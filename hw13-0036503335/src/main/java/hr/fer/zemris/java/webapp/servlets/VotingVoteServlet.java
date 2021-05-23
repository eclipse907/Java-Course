package hr.fer.zemris.java.webapp.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/voting-vote")
public class VotingVoteServlet extends HttpServlet {

	private static final long serialVersionUID = -5892248935508602985L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/voting-results.txt");
		try {
			int id = Integer.parseInt(req.getParameter("id"));
			updateVotingResults(fileName, id);
			resp.sendRedirect(req.getContextPath() + "/voting-results");
		} catch (IOException | NumberFormatException ex) {
			req.setAttribute("errorMessage", "There was an error while saving the vote.");
			req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(req, resp);
		}
	}
	
	private void updateVotingResults(String fileName, int id) throws IOException {
		Path votingResultsFile = Paths.get(fileName);
		if (!Files.exists(votingResultsFile)) {
			Files.createFile(votingResultsFile);
		}
		List<String> lines = Files.readAllLines(votingResultsFile);
		boolean bandExists = false;
		for (int i = 0; i < lines.size(); i++) {
			String[] line = lines.get(i).split("\\t");
			if (id == Integer.parseInt(line[0])) {
				line[1] = Long.toString(Long.parseLong(line[1]) + 1);
				lines.set(i, String.join("\t", line));
				bandExists = true;
				break;
			}
		}
		if (!bandExists) {
			lines.add(String.join("\t", Integer.toString(id), Long.toString(1)));
		}
		Files.write(votingResultsFile, lines);
	}

}
