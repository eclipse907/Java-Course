package hr.fer.zemris.java.webserver;

/**
 * This interface represents an object that can analyze the web server path
 * given and process it.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
@FunctionalInterface
public interface IDispatcher {
	
	/**
	 * This method analyzes the web server path and processes
	 * the request.
	 * 
	 * @param urlPath the web server path to analyze.
	 * @throws Exception if there is an error while processing
	 *                   the request.
	 */
	void dispatchRequest(String urlPath) throws Exception;
	
}
