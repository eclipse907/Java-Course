package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * This class represents a demo for the RequestContext class
 * from the hr.fer.zemris.java.webserver package.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class DemoRequestContext {
	
	/**
	 * The method from which the program starts.
	 * 
	 * @param args command line arguments. Not used
	 *             in this program.
	 * @throws IOException if there is an error while
	 *                     writing to the text file.
	 */
	public static void main(String[] args) throws IOException {
		demo1("primjer1.txt", "ISO-8859-2");
		demo1("primjer2.txt", "UTF-8");
		demo2("primjer3.txt", "UTF-8");
	}
	
	/**
	 * The first demo.
	 * 
	 * @param filePath the path of the text file in which to save
	 *                 the output.
	 * @param encoding the charset encoding to use.
	 * @throws IOException if there is an error while
	 *                     writing to the text file.
	 */
	private static void demo1(String filePath, String encoding) throws IOException {
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		RequestContext rc = new RequestContext(os, new HashMap<String, String>(),
		new HashMap<String, String>(),
		new ArrayList<RequestContext.RCCookie>(), "");
		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		// Only at this point will header be created and written...
		rc.write("Čevapčići i Šiščevapčići.");
		os.close();
	}
	
	/**
	 * The second demo.
	 * 
	 * @param filePath the path of the text file in which to save
	 *                 the output.
	 * @param encoding the charset encoding to use.
	 * @throws IOException if there is an error while
	 *                     writing to the text file.
	 */
	private static void demo2(String filePath, String encoding) throws IOException {
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		RequestContext rc = new RequestContext(os, new HashMap<String, String>(),
		new HashMap<String, String>(),
		new ArrayList<RequestContext.RCCookie>(), "");
		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		rc.addRCCookie(new RCCookie("korisnik", "perica", 3600, "127.0.0.1",
		"/", null));
		rc.addRCCookie(new RCCookie("zgrada", "B4", null, null, "/", null));
		// Only at this point will header be created and written...
		rc.write("Čevapčići i Šiščevapčići.");
		os.close();
	}
	
}
