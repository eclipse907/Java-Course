package hr.fer.zemris.java.custom.scripting.parser;

/**
 * This class represents an exception that is thrown when a
 * parser tries to parse a text that is invalid. This is an
 * unchecked expression. It inherits the RuntimeException
 * class.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class SmartScriptParserException extends RuntimeException {
	
	private static final long serialVersionUID = -7146405271722626823L;

	public SmartScriptParserException() {
	}
	
	public SmartScriptParserException(String message) {
		super(message);
	}
	
	public SmartScriptParserException(Throwable cause) {
		super(cause);
	}
	
	public SmartScriptParserException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
