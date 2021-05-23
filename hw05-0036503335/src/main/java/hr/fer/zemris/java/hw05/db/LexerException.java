package hr.fer.zemris.java.hw05.db;

/**
 * This class represents an exception that occurs when the
 * lexer can't find a token for the given input. This is an 
 * unchecked exception and inherits the RuntimeException class. 
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class LexerException extends RuntimeException {

	private static final long serialVersionUID = -288701431546364739L;

	public LexerException() {
	}
	
	public LexerException(String message) {
		super(message);
	}
	
	public LexerException(Throwable cause) {
		super(cause);
	}
	
	public LexerException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
