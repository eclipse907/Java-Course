package hr.fer.zemris.java.webapp.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/voting")
public class VotingServlet extends HttpServlet {

	private static final long serialVersionUID = -791129135167110974L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/voting-definition.txt");
		try {			
			req.setAttribute("bands", getBands(fileName));
			req.getRequestDispatcher("/WEB-INF/pages/votingIndex.jsp").forward(req, resp);
		} catch (IOException | NumberFormatException ex) {
			req.setAttribute("errorMessage", "There was an error while loading the bands.");
			req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(req, resp);
		}
	}
	
	static Map<Integer, String[]> getBands(String fileName) throws IOException {
		try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
			Map<Integer, String[]> bands = new TreeMap<>();
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				String[] data = line.split("\\t");
				bands.put(Integer.valueOf(data[0]), Arrays.copyOfRange(data, 1, 3));
			}
			return bands;
		}
	}

}
