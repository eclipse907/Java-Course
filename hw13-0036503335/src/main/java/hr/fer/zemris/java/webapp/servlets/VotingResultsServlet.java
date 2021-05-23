package hr.fer.zemris.java.webapp.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/voting-results")
public class VotingResultsServlet extends HttpServlet {

	private static final long serialVersionUID = 7808149973193076212L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String bandFile = req.getServletContext().getRealPath("/WEB-INF/voting-definition.txt");
		String resultFile = req.getServletContext().getRealPath("/WEB-INF/voting-results.txt");
		try {
			Map<Integer, String[]> bands = VotingServlet.getBands(bandFile);
			List<String[]> results = getVotingResults(resultFile, bands);
			req.setAttribute("bands", bands);
			req.setAttribute("results", results);
			req.setAttribute("winnersSongs", getWinnersSongs(results, bands));
			req.getRequestDispatcher("/WEB-INF/pages/votingRes.jsp").forward(req, resp);
		} catch (NumberFormatException | IOException ex) {
			req.setAttribute("errorMessage", "There was an error while loading the voting results.");
			req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(req, resp);
		}
	}
	
	static List<String[]> getVotingResults(String resultFile, Map<Integer, String[]> bands) throws IOException, NumberFormatException {
		Path votingResultsFile = Paths.get(resultFile);
		if (!Files.exists(votingResultsFile)) {
			Files.createFile(votingResultsFile);
		}
		Map<Integer, Long> resultFileLines = Files.readAllLines(votingResultsFile).stream()
				.map(line -> line.split("\\t"))
				.collect(Collectors.toMap(line -> Integer.valueOf(line[0]), line -> Long.valueOf(line[1])));
		List<String[]> results = new ArrayList<>();
		for (Integer id : bands.keySet()) {
			String[] toSave = new String[3];
			toSave[0] = id.toString();
			toSave[1] = bands.get(id)[0];
			toSave[2] = resultFileLines.containsKey(id) ? resultFileLines.get(id).toString() : "0";
			results.add(toSave);
		}
		Collections.sort(results, Collections.reverseOrder((e1, e2) -> {
				Long value1 = Long.valueOf(e1[2]);
				Long value2 = Long.valueOf(e2[2]);
				return value1.compareTo(value2);
		}));
		return results;
	}
	
	private List<String[]> getWinnersSongs(List<String[]> results, Map<Integer, String[]> bands) {
		Long maxVotes = Long.valueOf(results.get(0)[2]);
		if (maxVotes == 0) {
			return null;
		}
		List<String[]> winnersSongs = new ArrayList<>();
		for (String[] result : results) {
			if (Long.valueOf(result[2]).compareTo(maxVotes) == 0) {
				String[] toSave = new String[2];
				toSave[0] = result[1];
				toSave[1] = bands.get(Integer.valueOf(result[0]))[1];
				winnersSongs.add(toSave);
			}
		}
		return winnersSongs;
	}

}
