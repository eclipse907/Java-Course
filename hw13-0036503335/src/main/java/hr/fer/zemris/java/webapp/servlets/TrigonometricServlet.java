package hr.fer.zemris.java.webapp.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/trigonometric")
public class TrigonometricServlet extends HttpServlet {

	private static final long serialVersionUID = 8906444203830950433L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String a = req.getParameter("a");
			String b = req.getParameter("b");
			int startAngle, endAngle;
			if (a == null) {
				startAngle = 0;
			} else {
				startAngle = Integer.parseInt(a);
			}
			if (b == null) {
				endAngle = 360;
			} else {
				endAngle = Integer.parseInt(b);
			}
			if (startAngle > endAngle) {
				int temp = startAngle;
				startAngle = endAngle;
				endAngle = temp;
			}
			if (endAngle > startAngle + 720) {
				endAngle = startAngle + 720;
			}
			Map<Integer, Double> sinTrigonometricValues = new HashMap<>();
			Map<Integer, Double> cosTrigonometricValues = new HashMap<>();
			for (int i = startAngle; i <= endAngle; i++) {
				sinTrigonometricValues.put(i, Math.sin(Math.toRadians(i)));
				cosTrigonometricValues.put(i, Math.cos(Math.toRadians(i)));
			}
			req.setAttribute("sinValues", sinTrigonometricValues);
			req.setAttribute("cosValues", cosTrigonometricValues);
			req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
		} catch (NumberFormatException ex) {
			req.setAttribute("errorMessage", "One or more parameters are not parsable to integer.");
			req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(req, resp);
		}
	}

}
