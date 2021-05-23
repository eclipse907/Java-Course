package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This class represents an object that can process a
 * request to sum two numbers.
 * 
 * @author Tin Reiter
 *
 */
public class SumWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) {
		int a, b;
		try {
			a = Integer.parseInt(context.getParameter("a"));
		} catch (NumberFormatException ex) {
			a = 1;
		}
		try {
			b = Integer.parseInt(context.getParameter("b"));
		} catch (NumberFormatException ex) {
			b = 2;
		}
		context.setTemporaryParameter("zbroj", Integer.toString(a + b));
		context.setTemporaryParameter("varA", Integer.toString(a));
		context.setTemporaryParameter("varB", Integer.toString(b));
		if ((a + b) % 2 == 0) {
			context.setTemporaryParameter("imgName", "evenNum.jpg");
		} else {
			context.setTemporaryParameter("imgName", "oddNum.jpg");
		}
		try {
			context.getDispatcher().dispatchRequest("/private/pages/calc.smscr");
		} catch (Exception ex) {
			System.out.println("Error occurred while processing the request using the dispatcher given");
		}
	}

}
