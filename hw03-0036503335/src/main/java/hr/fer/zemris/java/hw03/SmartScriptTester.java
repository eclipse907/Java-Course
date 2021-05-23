package hr.fer.zemris.java.hw03;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.StandardCharsets; 
import java.nio.file.Paths; 

/**
 * This class is a test for the parser from hr.fer.zemris.java.custom.scripting.parser
 * package. It loads a text file whose file path is given as the first command line
 * argument and parses it.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class SmartScriptTester {
	
	/**
	 * The method from which the program starts.
	 * 
	 * @param args command line arguments.
	 * @throws IOException if there is an error in
	 *                     reading the text file.
	 */
	public static void main(String[] args) throws IOException {
		String docBody = new String(                  
				Files.readAllBytes(Paths.get(args[0])),                   
				StandardCharsets.UTF_8 
		); 
		SmartScriptParser parser = null;
		try {  
			parser = new SmartScriptParser(docBody); 
		} catch(SmartScriptParserException e) {  
			System.out.println("Unable to parse document!");  
			System.exit(-1); 
		} catch(Exception e) {  
			System.out.println("If this line ever executes, you have failed this class!");  
			System.exit(-1); 
		} 
		DocumentNode document = parser.getDocumentNode(); 
		String originalDocumentBody = document.toString(); 
		System.out.println(originalDocumentBody); // should write something like original                                          
		                                          // content of docBody
		SmartScriptParser parser2 = new SmartScriptParser(docBody);
		DocumentNode document2 = parser2.getDocumentNode();
		String originalDocumentBody2 = document2.toString();
		SmartScriptParser parser3 = new SmartScriptParser(originalDocumentBody2);
		DocumentNode document3 = parser3.getDocumentNode(); 
		// now document and document2 should be structurally identical trees 
		boolean same = document2.equals(document3);  // ==> "same" must be true
		System.out.println(same);
	}
	
}
