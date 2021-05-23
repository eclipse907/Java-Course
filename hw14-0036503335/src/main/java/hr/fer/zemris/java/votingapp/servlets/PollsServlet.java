package hr.fer.zemris.java.votingapp.servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import hr.fer.zemris.java.votingapp.dao.DAOException;
import hr.fer.zemris.java.votingapp.dao.DAOProvider;
import hr.fer.zemris.java.votingapp.model.Poll;

@WebServlet("/servlets/index.html")
public class PollsServlet extends HttpServlet {

	private static final long serialVersionUID = 7755215288362306949L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			req.setAttribute("polls", getPolls());
			req.getRequestDispatcher("/WEB-INF/pages/polls.jsp").forward(req, resp);
		} catch (DAOException ex) {
			req.setAttribute("errorMessage", "There was an error while loading the polls.");
			req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(req, resp);
		}
	}
	
	private List<Poll> getPolls() {
		return DAOProvider.getDao().getPolls();
	}

}
