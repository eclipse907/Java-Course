package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This class represents an object that can process a change
 * color request.
 * 
 * @author Tin Reiter
 *
 */
public class BgColorWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) {
		try {
			if (context.getParameter("bgcolor") == null) {
				context.getDispatcher().dispatchRequest("/private/pages/colorNotChanged.smscr");
				return;
			}
			String color = context.getParameter("bgcolor");
			for (Character c : color.toCharArray()) {
				if (Character.digit(c, 16) == -1) {
					context.getDispatcher().dispatchRequest("/private/pages/colorNotChanged.smscr");
					return;
				}
			}
			context.setPersistentParameter("bgcolor", color);
			context.getDispatcher().dispatchRequest("/private/pages/colorChanged.smscr");
		} catch (Exception ex) {
			System.out.println("An error occurred while processing the request.");
		}
	}

}
