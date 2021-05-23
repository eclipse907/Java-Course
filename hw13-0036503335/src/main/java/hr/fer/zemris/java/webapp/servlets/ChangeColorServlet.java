package hr.fer.zemris.java.webapp.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/setcolor")
public class ChangeColorServlet extends HttpServlet {

	private static final long serialVersionUID = -7562900147536004914L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String color = req.getParameter("color");
		if (color == null) {
			color = "FFFFFF";
		}
		HttpSession session = req.getSession();
		session.setAttribute("pickedBgCol", color);
		resp.sendRedirect(req.getContextPath() + "/index.jsp");
	}

}
