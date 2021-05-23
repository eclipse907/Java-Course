package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This class represents an object that can display
 * the home page of this web server.
 * 
 * @author Tin Reiter
 *
 */
public class Home implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) {
		if (context.getPersistentParameter("bgcolor") != null) {
			context.setTemporaryParameter("background", context.getPersistentParameter("bgcolor"));
		} else {
			context.setTemporaryParameter("background", "7F7F7F");
		}
		try {
			context.getDispatcher().dispatchRequest("/private/pages/home.smscr");
		} catch (Exception ex) {
			System.out.println("Error occurred while processing the request using the dispatcher given");
		}
	}

}
