package hr.fer.zemris.java.votingapp.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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

@WebServlet("servlets/voting-results")
public class VotingResultsServlet extends HttpServlet {

	private static final long serialVersionUID = 7808149973193076212L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String pollID = req.getParameter("pollID");
			if (pollID == null) {
				throw new NullPointerException();
			}
			long pollId = Long.parseLong(pollID);
			req.setAttribute("poll", gePoll(pollId));
			List<PollOption> votingResults = getPollVotingResults(pollId);
			req.setAttribute("pollVotingResults", votingResults);
			req.setAttribute("mostVotedPollOptions", getMostVotedPollOptions(votingResults));
			req.getRequestDispatcher("/WEB-INF/pages/votingRes.jsp").forward(req, resp);
		} catch (NullPointerException | NumberFormatException | DAOException ex) {
			req.setAttribute("errorMessage", "There was an error while loading the voting results for the given poll.");
			req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(req, resp);
		}
	}
	
	private List<PollOption> getPollVotingResults(long pollId) throws DAOException {
		List<PollOption> votingResults = DAOProvider.getDao().getPollOptions(pollId);
		votingResults.sort(Collections.reverseOrder((e1, e2) -> (int)(e1.getVotesCount() - e2.getVotesCount())));
		return votingResults;
	}
	
	private Poll gePoll(long pollId) throws DAOException {
		return DAOProvider.getDao().getPoll(pollId);
	}
	
	private List<PollOption> getMostVotedPollOptions(List<PollOption> votingResults) {
		List<PollOption> mostVotedPollOptions = new ArrayList<>();
		long maxVotes = votingResults.get(0).getVotesCount();
		if (maxVotes == 0) {
			return mostVotedPollOptions;
		}
		for (PollOption result : votingResults) {
			if (result.getVotesCount() >= maxVotes) {
				mostVotedPollOptions.add(result);
			} else {
				break;
			}
		}
		return mostVotedPollOptions;
	}
	
}
