package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;
import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This class represents an object that can process an echo
 * request.
 * 
 * @author Tin Reiter
 *
 */
public class EchoParams implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) {
		try {
			StringBuilder table = new StringBuilder();
			table.append("<html><body>");
			table.append("<h2>Given parameters</h2>");
			table.append("<table style=\"width:100%\">");
			for (String param : context.getParameterNames()) {
				table.append("<tr><td>" + context.getParameter(param) + "</td></tr>");
			}
			table.append("</table>");
			table.append("</body></html>");
			context.setMimeType("text/html");
			context.write(table.toString());
		} catch(IOException ex) {
			System.out.println("Error while writing to context's output stream.");
		}
	}

}
