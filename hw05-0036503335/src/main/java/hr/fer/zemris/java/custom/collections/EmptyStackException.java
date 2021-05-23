package hr.fer.zemris.java.custom.collections;

/**
 * This class represents an exception that is thrown
 * when a user tries to pop or peek an empty stack.
 * This is an unchecked exception and inherits the
 * RuntimeException class.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class EmptyStackException extends RuntimeException {
	
	private static final long serialVersionUID = 5019575938076748881L;
	
	public EmptyStackException() {
	}
	
	public EmptyStackException(String message) {
		super(message);
	}
	
	public EmptyStackException(Throwable cause) {
		super(cause);
	}
	
	public EmptyStackException(String message, Throwable cause) {
		super(message, cause);
	}

}
