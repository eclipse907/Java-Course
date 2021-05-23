package hr.fer.zemris.java.hw06.shell;

/**
 * This class represents an exception that is thrown when the
 * shell encounters an error while reading from or writing to
 * the standard input/output.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class ShellIOException extends RuntimeException {

	private static final long serialVersionUID = 3844731367581627444L;
	
	public ShellIOException() {
	}
	
	public ShellIOException(String message) {
		super(message);
	}
	
	public ShellIOException(Throwable cause) {
		super(cause);
	}
	
	public ShellIOException(String message, Throwable cause) {
		super(message, cause);
	}

}
