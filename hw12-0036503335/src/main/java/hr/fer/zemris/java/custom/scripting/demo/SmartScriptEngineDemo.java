package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * This class represents a demo for the SmartScriptEngine class in
 * the hr.fer.zemris.java.custom.scripting.exec package.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class SmartScriptEngineDemo {

	/**
	 * The method from which the program starts.
	 * 
	 * @param args command line arguments. Not used in this
	 *             program.
	 */
	public static void main(String[] args) {
		try {
			String documentBody = new String(Files.readAllBytes(Paths.get("src\\main\\resources\\examples\\osnovni.smscr")), StandardCharsets.UTF_8);
			Map<String,String> parameters = new HashMap<>();
			Map<String,String> persistentParameters = new HashMap<>();
			List<RCCookie> cookies = new ArrayList<>();
			new SmartScriptEngine(
			new SmartScriptParser(documentBody).getDocumentNode(),
			new RequestContext(System.out, parameters, persistentParameters, cookies, "")
			).execute();
			System.out.println();
			
			documentBody = new String(Files.readAllBytes(Paths.get("src\\main\\resources\\examples\\zbrajanje.smscr")), StandardCharsets.UTF_8);
			parameters = new HashMap<>();
			persistentParameters = new HashMap<>();
			cookies = new ArrayList<>();
			parameters.put("a", "4");
			parameters.put("b", "2");
			new SmartScriptEngine(
			new SmartScriptParser(documentBody).getDocumentNode(),
			new RequestContext(System.out, parameters, persistentParameters, cookies, "")
			).execute();
			System.out.println();
			
			documentBody = new String(Files.readAllBytes(Paths.get("src\\main\\resources\\examples\\brojPoziva.smscr")), StandardCharsets.UTF_8);
			parameters = new HashMap<>();
			persistentParameters = new HashMap<>();
			cookies = new ArrayList<>();
			persistentParameters.put("brojPoziva", "3");
			RequestContext rc = new RequestContext(System.out, parameters, persistentParameters, cookies, "");
			new SmartScriptEngine(
			new SmartScriptParser(documentBody).getDocumentNode(), rc
			).execute();
			System.out.println();
			System.out.println("Vrijednost u mapi: " + rc.getPersistentParameter("brojPoziva"));
			
			documentBody = new String(Files.readAllBytes(Paths.get("src\\main\\resources\\examples\\fibonacci.smscr")), StandardCharsets.UTF_8);
			parameters = new HashMap<>();
			persistentParameters = new HashMap<>();
			cookies = new ArrayList<>();
			new SmartScriptEngine(
			new SmartScriptParser(documentBody).getDocumentNode(),
			new RequestContext(System.out, parameters, persistentParameters, cookies, "")
			).execute();
			System.out.println();
			
			documentBody = new String(Files.readAllBytes(Paths.get("src\\main\\resources\\examples\\fibonaccih.smscr")), StandardCharsets.UTF_8);
			parameters = new HashMap<>();
			persistentParameters = new HashMap<>();
			cookies = new ArrayList<>();
			new SmartScriptEngine(
			new SmartScriptParser(documentBody).getDocumentNode(),
			new RequestContext(System.out, parameters, persistentParameters, cookies, "")
			).execute();

		} catch (IOException ex) {
			System.out.println("Error while reading file from disk.");
			System.exit(1);
		}
	}

}
