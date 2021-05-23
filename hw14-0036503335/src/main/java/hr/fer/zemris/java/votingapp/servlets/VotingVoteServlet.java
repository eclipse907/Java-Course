package hr.fer.zemris.java.votingapp.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import hr.fer.zemris.java.votingapp.dao.DAOException;
import hr.fer.zemris.java.votingapp.dao.DAOProvider;
import hr.fer.zemris.java.votingapp.model.PollOption;

@WebServlet("servlets/voting-vote")
public class VotingVoteServlet extends HttpServlet {

	private static final long serialVersionUID = -5892248935508602985L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String pollOptionID = req.getParameter("pollOptionID");
			if (pollOptionID == null) {
				throw new NullPointerException();
			}
			long pollOptionId = Long.parseLong(pollOptionID);
			updateVotingResults(pollOptionId);
			resp.sendRedirect(req.getContextPath() + "/servlets/voting-results?pollID=" + getPollOption(pollOptionId).getPollId());
		} catch (NullPointerException | NumberFormatException | DAOException ex) {
			req.setAttribute("errorMessage", "There was an error while saving the vote for the given poll option.");
			req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(req, resp);
		}
	}
	
	private void updateVotingResults(long pollOptionId) throws DAOException {
		DAOProvider.getDao().vote(pollOptionId);
	}
	
	private PollOption getPollOption(long pollOptionId) throws DAOException {
		return DAOProvider.getDao().gePollOption(pollOptionId);
	}

}
