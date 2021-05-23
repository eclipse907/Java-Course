package hr.fer.zemris.java.gui.layouts;

/**
 * This class represents an exception that is thrown when a user
 * tries to add a wrong component or position to the CalcLayout
 * manager.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class CalcLayoutException extends RuntimeException {

	private static final long serialVersionUID = -3503721957041493250L;

	public CalcLayoutException() {
	}
	
	public CalcLayoutException(String message) {
		super(message);
	}
	
	public CalcLayoutException(Throwable cause) {
		super(cause);
	}
	
	public CalcLayoutException(String message, Throwable cause) {
		super(message, cause);
	}

}
