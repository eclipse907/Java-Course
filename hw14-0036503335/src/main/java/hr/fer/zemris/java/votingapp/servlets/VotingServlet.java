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
import hr.fer.zemris.java.votingapp.model.PollOption;

@WebServlet("servlets/voting")
public class VotingServlet extends HttpServlet {

	private static final long serialVersionUID = -791129135167110974L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String pollID = req.getParameter("pollID");
			if (pollID == null) {
				throw new NullPointerException();
			}
			long pollId = Long.parseLong(pollID);
			req.setAttribute("poll", gePoll(pollId));
			req.setAttribute("pollOptions", getPollOptions(pollId));
			req.getRequestDispatcher("/WEB-INF/pages/votingIndex.jsp").forward(req, resp);
		} catch (NullPointerException | NumberFormatException | DAOException ex) {
			req.setAttribute("errorMessage", "There was an error while loading the polling options for the given poll id.");
			req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(req, resp);
		}
	}
	
	private List<PollOption> getPollOptions(long pollId) throws DAOException {
		List<PollOption> pollOptions = DAOProvider.getDao().getPollOptions(pollId);
		pollOptions.sort((e1, e2) -> (int)(e1.getId() - e2.getId()));
		return pollOptions;
	}
	
	private Poll gePoll(long pollId) throws DAOException {
		return DAOProvider.getDao().getPoll(pollId);
	}

}
