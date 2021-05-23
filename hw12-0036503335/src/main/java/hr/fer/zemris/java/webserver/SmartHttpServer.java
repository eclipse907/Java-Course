package hr.fer.zemris.java.webserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * This class represents a Http server that loads it's configuration from
 * the file given.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class SmartHttpServer {

	private String address;
	private String domainName;
	private int port;
	private int workerThreads;
	private int sessionTimeout;
	private Map<String,String> mimeTypes = new HashMap<>();
	private Map<String, SessionMapEntry> sessions = new ConcurrentHashMap<>();
	private Random sessionRandom = new Random();
	private ServerThread serverThread;
	private ExecutorService threadPool;
	private Path documentRoot;
	private Map<String,IWebWorker> workersMap;
	
	/**
	 * Creates a new Http server and configures it using the settings from the
	 * configuration file given.
	 * 
	 * @param configFileName the name of the configuration file.
	 */
	public SmartHttpServer(String configFileName) {
		try {
			Properties serverConfig = new Properties();
			serverConfig.load(Files.newBufferedReader(Paths.get("config\\" + configFileName)));
			address = serverConfig.getProperty("server.address");
			domainName = serverConfig.getProperty("server.domainName");
			port = Integer.parseInt(serverConfig.getProperty("server.port"));
			workerThreads = Integer.parseInt(serverConfig.getProperty("server.workerThreads"));
			sessionTimeout = Integer.parseInt(serverConfig.getProperty("session.timeout"));
			documentRoot = Paths.get(serverConfig.getProperty("server.documentRoot"));
			Properties mimeConfig = new Properties();
			mimeConfig.load(Files.newBufferedReader(Paths.get(serverConfig.getProperty("server.mimeConfig"))));
			for (String mime : mimeConfig.stringPropertyNames()) {
				mimeTypes.put(mime, mimeConfig.getProperty(mime));
			}
			List<String> workerConfigLines = Files.readAllLines(Paths.get(serverConfig.getProperty("server.workers")));
			workersMap = new HashMap<>();
			for (String line : workerConfigLines) {
				if (line.trim().startsWith("#") || line.trim().isEmpty()) {
					continue;
				}
				String[] splitedLine = line.split("=");
				if (workersMap.containsKey(splitedLine[0].trim())) {
					throw new IllegalArgumentException("There is already a mapping for the path given.");
				} else {
					workersMap.put(splitedLine[0].trim(), getWorkerFromFQCN(splitedLine[1].trim()));
				}
			}
		} catch (IOException ex) {
			System.out.println("Error while reading configuration file.");
			System.exit(1);
		} catch (Exception ex) {
			System.out.println("Error while loading workers from workers.properties file.");
			System.exit(1);
		}
	}
	
	/**
	 * Starts this Http server.
	 */
	protected synchronized void start() {
		if (serverThread == null) {
			serverThread = new ServerThread();
			threadPool = Executors.newFixedThreadPool(workerThreads);
			serverThread.start();
		}
	}
	
	/**
	 * Stops this Http server.
	 */
	protected synchronized void stop() {
		serverThread.interrupt();
		threadPool.shutdown();
	}
	
	/**
	 * Returns an object that implements the IWebWorker interface from the Fully Qualified
	 * Class Name given.
	 * 
	 * @param fqcn the Fully Qualified Class Name of the class to load.
	 * @return the loaded class.
	 * @throws Exception if there is an error while loading the class.
	 */
	private IWebWorker getWorkerFromFQCN(String fqcn) throws Exception {
		Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
		@SuppressWarnings("deprecation")
		Object newObject = referenceToClass.newInstance();
		return (IWebWorker)newObject;
	}
	
	/**
	 * This class represents a thread that listens for Http request and submits them
	 * to the server thread pool for processing.
	 * 
	 * @author Tin Reiter
	 * @version 1.0
	 */
	protected class ServerThread extends Thread {
		
		@Override
		public void run() {
			try (ServerSocket serverSocket = new ServerSocket()) {
				serverSocket.bind(new InetSocketAddress(InetAddress.getByName(address), port));
				Thread cleaner = new Thread(() -> {
					try {
						Thread.sleep(300000);
					} catch (InterruptedException ex) {						
					}
					for (String sid : sessions.keySet()) {
						SessionMapEntry entry = sessions.get(sid);
						if (entry.validUntil < System.currentTimeMillis()) {
							sessions.remove(sid);
						}
					}
				});
				cleaner.setDaemon(true);
				cleaner.start();
				while (true) {
					Socket client = serverSocket.accept();
					ClientWorker cw = new ClientWorker(client);
					threadPool.submit(cw);
				}
			} catch (IOException ex) {
				System.out.println("Error while creating socket.");
			}
		}
		
	}
	
	/**
	 * This class represents a worker that processes Http
	 * requests.
	 * 
	 * @author Tin Reiter
	 * @version 1.0
	 */
	private class ClientWorker implements Runnable, IDispatcher {
		
		private Socket csocket;
		private InputStream istream;
		private OutputStream ostream;
		private String version;
		private String method;
		private String host;
		private Map<String,String> params = new HashMap<>();
		private Map<String,String> tempParams = new HashMap<>();
		private Map<String,String> permPrams = new HashMap<>();
		private List<RCCookie> outputCookies = new ArrayList<>();
		private String SID;
		private RequestContext context;
		
		/**
		 * Creates a new ClientWorker.
		 * 
		 * @param csocket the socket with which to send
		 *                the Http response.
		 */
		public ClientWorker(Socket csocket) {
			this.csocket = csocket;
		}
		
		@Override
		public void run() {
			try {
				istream = new BufferedInputStream(csocket.getInputStream());
				ostream = new BufferedOutputStream(csocket.getOutputStream());
				List<String> request = extractHeaderLines(new String(readRequest(), StandardCharsets.US_ASCII));
				if (request.size() < 1) {
					sendError(400, "Bad Request");					
					return;
				}
				String[] firstLine = request.get(0).split(" ");
				version = firstLine[2].trim();
				method = firstLine[0].trim();
				if (!firstLine[0].equals(method) || (!firstLine[2].equals(version) && !firstLine[2].equals(version))) {
					sendError(400, "Bad Request");
					return;
				}
				checkSession(request);
				String[] requestedPath = firstLine[1].split("\\?");
				if (requestedPath.length == 2) {
					String paramString = requestedPath[1].trim();
					parseParameters(paramString);
				}
				internalDispatchRequest(requestedPath[0].trim(), true);
			} catch (IOException ex) {
				System.out.println("Error while creating stream.");
			} catch (Exception ex) {
			} finally {
				try {
					ostream.flush();
					csocket.close();
				} catch (IOException ex) {
					System.out.println("Error while closing resources.");
				}
			}
		}
		
		/**
		 * Reads the header of the Http request.
		 * 
		 * @return a byte array containing the header of the
		 *         http request.
		 * @throws IOException if there is an error while reading
		 *                     the request.
		 */
		private byte[] readRequest() throws IOException {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
	        l: while(true) {
				int b = istream.read();
				if(b==-1) return null;
				if(b!=13) {
					bos.write(b);
				}
				switch(state) {
				case 0: 
					if(b==13) { state=1; } else if(b==10) state=4;
					break;
				case 1: 
					if(b==10) { state=2; } else state=0;
					break;
				case 2: 
					if(b==13) { state=3; } else state=0;
					break;
				case 3: 
					if(b==10) { break l; } else state=0;
					break;
				case 4: 
					if(b==10) { break l; } else state=0;
					break;
				}
			}
			return bos.toByteArray();
		}
		
		/**
		 * Extracts the lines from the header given and stores them in a
		 * list.
		 * 
		 * @param header the header of the http request.
		 * @return a list containing the header lines.
		 */
		private List<String> extractHeaderLines(String header) {
			List<String> headerLines = new ArrayList<>();
			String currentLine = null;
			for (String line : header.split("\n")) {
				if (line.isEmpty()) {
					break;
				}
				char begining = line.charAt(0);
				if (begining == 9 || begining == 32) {
					currentLine += line;
				} else {
					if (currentLine != null) {
						headerLines.add(currentLine);
					}
					currentLine = line;
				}
			}
			if (!currentLine.isEmpty()) {
				headerLines.add(currentLine);
			}
			return headerLines;
		}
		
		/**
		 * Parses the request parameters from the String given and
		 * stores them in the parameters map.
		 * 
		 * @param paramString the string containing the parameters.
		 */
		private void parseParameters(String paramString) {
			for (String param : paramString.split("&")) {
				String[] splitedParam = param.trim().split("=", 2);
				params.put(splitedParam[0].trim(), splitedParam[1].trim());
			}
		}
		
		/**
		 * Sends an error message to the web server client.
		 * 
		 * @param statusCode the status code to send.
		 * @param statusText the status text to send.
		 * @throws IOException if there is an error while sending
		 *                     the message.
		 */
		private void sendError(int statusCode, String statusText) throws IOException {
			ostream.write((version + " " + statusCode + " " + statusText + "\r\n" +
					  "Server: " + host +
					  "Content-Type: text/plain; charset=UTF-8\r\n" +
					  "Content-Length: 0\r\n" +
					  "Connection: close\r\n" +
					  "\r\n").getBytes(StandardCharsets.US_ASCII)
				     );
			ostream.flush();
		}
		
		/**
		 * Analyzes the web server path and process the request.
		 * 
		 * @param urlPath the path to analyze.
		 * @param directCall is this a direct call.
		 * @throws Exception if there is an error while processing the
		 *                   request.
		 */
		private void internalDispatchRequest(String urlPath, boolean directCall) throws Exception {
			if (directCall && (urlPath.startsWith("/private/") || urlPath.equals("/private"))) {
				sendError(404, "Not Found");
				return;
			}
			if (urlPath.matches("^/ext/\\w+$")) {
				String fqcn = "hr.fer.zemris.java.webserver.workers." + urlPath.split("^/ext/", 2)[1];
				if (context == null) {
					context = new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this, SID);
				}
				getWorkerFromFQCN(fqcn).processRequest(context);
				return;
			}
			if (workersMap.containsKey(urlPath)) {
				if (context == null) {
					context = new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this, SID);
				}
				workersMap.get(urlPath).processRequest(context);
				return;
			}
			Path requestedFile = documentRoot.resolve(urlPath.substring(1));
			if (!requestedFile.toAbsolutePath().startsWith(documentRoot.toAbsolutePath())) {
				sendError(403, "Forbidden");
				return;
			}
			String extension;
			if (!Files.exists(requestedFile) || !Files.isRegularFile(requestedFile) || !Files.isReadable(requestedFile)) {
				sendError(404, "Not Found");
				return;
			} else {
				extension = requestedFile.getFileName().toString().split("\\.", 2)[1].trim();
			}
			if (extension.equals("smscr")) {
				if (context == null) {
					context = new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this, SID);
				}
				String documentBody = new String(Files.readAllBytes(requestedFile), StandardCharsets.UTF_8);
				new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(), context).execute();
			} else {
				String mimeType;
				if (mimeTypes.containsKey(extension)) {
					mimeType = mimeTypes.get(extension);
				} else {
					mimeType = "application/octet-stream";
				}
				if (context == null) {
					context = new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this, SID);
				}
				context.setContentLength(Files.size(requestedFile));
				context.setMimeType(mimeType);
				context.setStatusCode(200);
				context.write(Files.readAllBytes(requestedFile));
			}
		}

		/*
		 * {@inheritDoc}
		 */
		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}
		
		/**
		 * Checks if there is a cookie entry for this session.
		 * 
		 * @param headers the header of the http request.
		 */
		private void checkSession(List<String> headers) {
			String sidCandidate = null;
			for (String headerLine : headers) {
				if (!headerLine.trim().startsWith("Cookie:")) {
					continue;
				} else {
					for (String cookie : headerLine.split(" ", 2)[1].trim().split(";")) {
						if (cookie.trim().startsWith("sid")) {
							sidCandidate = cookie.trim().split("=", 2)[1].trim();
							sidCandidate = sidCandidate.substring(1, sidCandidate.length() - 1).trim();
							break;
						}
					}
					break;
				}
			}
			synchronized (SmartHttpServer.this) {
				for (String line : headers) {
					if (line.startsWith("Host:")) {
						String hostName = line.split(" ", 2)[1].trim();
						if (hostName.contains(":")) {
							host = hostName.split(":", 2)[0].trim();
						} else {
							host = hostName;
						}
						break;
					}
				}
				if (host == null) {
					host = domainName;
				}
				if (sidCandidate != null && sessions.containsKey(sidCandidate) && sessions.get(sidCandidate).host.equals(host) &&
					sessions.get(sidCandidate).validUntil > System.currentTimeMillis()
				   ) {
					SessionMapEntry session = sessions.get(sidCandidate);
					session.validUntil = System.currentTimeMillis() + sessionTimeout * 1000;
					permPrams = session.map;
					SID = session.sid;
					return;
				}
				StringBuilder sidBuilder = new StringBuilder();
				for (int i = 0; i < 20; i++) {
					sidBuilder.append((char)('A' + sessionRandom.nextInt(26)));
				}
				String sid = sidBuilder.toString();
				SessionMapEntry session = new SessionMapEntry();
				session.sid = sid;
				session.host = host;
				session.validUntil = System.currentTimeMillis() + sessionTimeout * 1000;
				session.map = new ConcurrentHashMap<>();
				sessions.put(sid, session);
				outputCookies.add(new RCCookie("sid", sid, null, host, "/", "HttpOnly"));
				SID = sid;
				permPrams = session.map;
			}
		}
		
	}
	
	/**
	 * This class represent information about a 
	 * session.
	 * 
	 * @author Tin Reiter
	 * @version 1.0
	 */
	private static class SessionMapEntry {
		String sid;
		String host;
		long validUntil;
		Map<String,String> map;
	}
	
	/**
	 * The method from which the server starts.
	 * 
	 * @param args command line arguments.
	 */
	public static void main(String[] args) {
		SmartHttpServer server = new SmartHttpServer("server.properties");
		server.start();
	}

}
