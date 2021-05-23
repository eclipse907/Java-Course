package hr.fer.zemris.java.webserver;

/**
 * This interface represents an object that can process
 * a web request.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
@FunctionalInterface
public interface IWebWorker {

	/**
	 * This method process the web request and writes it using
	 * the RequestContext object given.
	 * 
	 * @param context the RequestContext object used to write the
	 *                result.
	 * @throws Exception if there is an error while processing the
	 *                   request.
	 */
	public void processRequest(RequestContext context) throws Exception;
	
}
