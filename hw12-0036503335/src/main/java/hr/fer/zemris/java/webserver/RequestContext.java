package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class represents an object that stores the context
 * needed to write bytes to the OutputStream given.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class RequestContext {
	
	private OutputStream outputStream;
	private Charset charset;
	private String encoding;
	private int statusCode;
	private String statusText;
	private String mimeType;
	private Long contentLength;
	private Map<String, String> parameters;
	private Map<String, String> temporaryParameters = new HashMap<>();
	private Map<String, String> persistentParameters;
	private List<RCCookie> outputCookies;
	private boolean headerGenerated;
	private IDispatcher dispatcher;
	private String sid;

	/**
	 * This class represents cookies used by the RequestContext
	 * class.
	 * 
	 * @author Tin Reiter
	 * @version 1.0
	 */
	public static class RCCookie {
		
		private String name;
		private String value;
		private String domain;
		private String path;
		private Integer maxAge;
		private String type;
		
		public RCCookie(String name, String value, Integer maxAge, String domain, String path, String type) {
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
			this.type = type;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @return the value
		 */
		public String getValue() {
			return value;
		}

		/**
		 * @return the domain
		 */
		public String getDomain() {
			return domain;
		}

		/**
		 * @return the path
		 */
		public String getPath() {
			return path;
		}

		/**
		 * @return the maxAge
		 */
		public Integer getMaxAge() {
			return maxAge;
		}

		/**
		 * @return the type
		 */
		public String getType() {
			return type;
		}
		
	}

	/**
	 * Creates a new RequestContext with the values given.
	 * 
	 * @param outputStream the stream to write to.
	 * @param parameters the parameters.
	 * @param persistentParameters the persistent parameters.
	 * @param outputCookies the output cookies.
	 * @param sid the session id.
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters, Map<String, String> persistentParameters,
			              List<RCCookie> outputCookies, String sid
			             ) {
		this.outputStream = outputStream;
		this.parameters = parameters;
		this.persistentParameters = persistentParameters;
		this.outputCookies = outputCookies;
		encoding = "UTF-8";
		statusCode = 200;
		statusText = "OK";
		mimeType = "text/html";
		contentLength = null;
		this.sid = sid;
	}
	
	/**
	 * Creates a new RequestContext with the values given.
	 * 
	 * @param outputStream the stream to write to.
	 * @param parameters the parameters.
	 * @param persistentParameters the persistent parameters.
	 * @param outputCookies the output cookies.
	 * @param temporaryParameters the temporary parameters.
	 * @param dispatcher the dispatcher.
	 * @param sid the session id.
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters, Map<String, String> persistentParameters,
			              List<RCCookie> outputCookies, Map<String,String> temporaryParameters, IDispatcher dispatcher, String sid
			             ) {
		this(outputStream, parameters, persistentParameters, outputCookies, sid);
		this.temporaryParameters = temporaryParameters;
		this.dispatcher = dispatcher;
	}

	/**
	 * @param encoding the encoding to set
	 */
	public void setEncoding(String encoding) {
		if (!headerGenerated) {
			this.encoding = encoding;
		} else {
			throw new RuntimeException("The header has already been generated.");
		}
	}

	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(int statusCode) {
		if (!headerGenerated) {
			this.statusCode = statusCode;
		} else {
			throw new RuntimeException("The header has already been generated.");
		}
	}

	/**
	 * @param statusText the statusText to set
	 */
	public void setStatusText(String statusText) {
		if (!headerGenerated) {
			this.statusText = statusText;
		} else {
			throw new RuntimeException("The header has already been generated.");
		}
	}

	/**
	 * @param mimeType the mimeType to set
	 */
	public void setMimeType(String mimeType) {
		if (!headerGenerated) {
			this.mimeType = mimeType;
		} else {
			throw new RuntimeException("The header has already been generated.");
		}
	}

	/**
	 * @param contentLength the contentLength to set
	 */
	public void setContentLength(Long contentLength) {
		if (!headerGenerated) {
			this.contentLength = contentLength;
		} else {
			throw new RuntimeException("The header has already been generated.");
		}
	}
	
	/**
	 * Adds the given cookie to this RequestContext.
	 * @param cookie
	 */
	public void addRCCookie(RCCookie cookie) {
		if (!headerGenerated) {
			outputCookies.add(cookie);
		} else {
			throw new RuntimeException("The header has already been generated.");
		}
	}
	
	/**
	 * Returns the value of the parameter with
	 * the name given.
	 * @param name
	 * @return
	 */
	public String getParameter(String name) {
		if (parameters.containsKey(name)) {
			return parameters.get(name);
		} else {
			return null;
		}
	}
	
	/**
	 * Returns a Set containing the names of all the parameters
	 * this RequestContext contains.
	 * 
	 * @return
	 */
	public Set<String> getParameterNames() {
		return Collections.unmodifiableSet(parameters.keySet());
	}
	
	/**
	 * Returns the value of the persistent parameter with the given
	 * name.
	 * 
	 * @param name
	 * @return
	 */
	public String getPersistentParameter(String name) {
		if (persistentParameters.containsKey(name)) {
			return persistentParameters.get(name);
		} else {
			return null;
		}
	}
	
	/**
	 * Returns a Set containing the names of all the persistent parameters
	 * this RequestContext contains.
	 * 
	 * @return
	 */
	public Set<String> getPersistentParameterNames() {
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}
	
	/**
	 * Adds the persistent parameter given to this RequestContext
	 * @param name
	 * @param value
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}
	
	/**
	 * Removes the persistent parameter with the given name
	 * from this RequestContext.
	 * @param name
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}
	
	/**
	 * Returns the value of the temporary parameter with the
	 * given name.
	 * 
	 * @param name
	 * @return
	 */
	public String getTemporaryParameter(String name) {
		if (temporaryParameters.containsKey(name)) {
			return temporaryParameters.get(name);
		} else {
			return null;
		}
	}
	
	/**
	 * Returns a Set containing the names of all the temporary parameters
	 * this RequestContext contains.
	 * 
	 * @return
	 */
	public Set<String> getTemporaryParameterNames() {
		return Collections.unmodifiableSet(temporaryParameters.keySet());
	}
	
	/**
	 * Returns the session id.
	 * @return
	 */
	public String getSessionID() {
		return sid;
	}
	
	/**
	 * Adds the temporary parameter given to this RequestContext
	 * @param name
	 * @param value
	 */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}
	
	/**
	 * Removes the temporary parameter given from this RequestContext
	 * @param name
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}
	
	/**
	 * Writes the bytes given to the output stream of this
	 * RequestContext.
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public RequestContext write(byte[] data) throws IOException {
		if (!headerGenerated) {
			outputStream.write(createHeader());
		}
		outputStream.write(data);
		return this;
	}
	
	/**
	 * Writes len number of bytes given to the output stream of this RequestContext from
	 * the offset given.
	 * @param data
	 * @param offset
	 * @param len
	 * @return
	 * @throws IOException
	 */
	public RequestContext write(byte[] data, int offset, int len) throws IOException {
		if (!headerGenerated) {
			outputStream.write(createHeader());
		}
		outputStream.write(data, offset, len);
		return this;
	}
	
	/**
	 * Converts the String given into bytes using the charset of this RequestContext
	 * and writes it to the output stream of this RequestContext.
	 * @param text
	 * @return
	 * @throws IOException
	 */
	public RequestContext write(String text) throws IOException {
		if (!headerGenerated) {
			outputStream.write(createHeader());
		}
		outputStream.write(text.getBytes(charset));
		return this;
	}
	
	/**
	 * Creates a Http header from the context of this RequestContext.
	 * 
	 * @return
	 */
	private byte[] createHeader() {
		StringBuilder header = new StringBuilder();
		charset = Charset.forName(encoding);
		header.append("HTTP/1.1 " + Integer.toString(statusCode) + " " + statusText + "\r\n");
		header.append("Content-Type: " + (mimeType.startsWith("text/") ? mimeType + "; charset=" + encoding : mimeType) + "\r\n");
		if (contentLength != null) {
			header.append("Content-Length: " + contentLength.toString() + "\r\n");
		}
		for (RCCookie cookie : outputCookies) {
			header.append("Set-Cookie: " + cookie.getName() + "=\"" + cookie.getValue() + "\"" +
		                  (cookie.getDomain() != null ? "; Domain=" + cookie.getDomain() : "") +
		                  (cookie.getPath() != null ? "; Path=" + cookie.getPath() : "") +
		                  (cookie.getMaxAge() != null ? "; Max-Age=" + cookie.getMaxAge().toString() : "") +
		                  (cookie.getType() != null ? "; " + cookie.getType() : "")  + "\r\n"
		                 );
		}
		header.append("\r\n");
		headerGenerated = true;
		return header.toString().getBytes(StandardCharsets.ISO_8859_1);
	}

	/**
	 * @return the dispatcher
	 */
	public IDispatcher getDispatcher() {
		return dispatcher;
	}
	
}
