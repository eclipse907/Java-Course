package hr.fer.zemris.java.votingapp.servlets;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import hr.fer.zemris.java.votingapp.dao.DAOException;
import hr.fer.zemris.java.votingapp.dao.DAOProvider;
import hr.fer.zemris.java.votingapp.model.PollOption;

@WebServlet("servlets/voting-xls")
public class VotingResultsXlsServlet extends HttpServlet {

	private static final long serialVersionUID = 5004261861089573752L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String pollID = req.getParameter("pollID");
			if (pollID == null) {
				throw new NullPointerException();
			}
			long pollId = Long.parseLong(pollID);
			HSSFWorkbook resultsWorkbook = getXlsOfPollVotingResults(pollId);
			resp.setContentType("application/octet-stream");
			resp.setHeader("Content-Disposition", "attachment; filename=\"voting results.xls\"");
			resultsWorkbook.write(resp.getOutputStream());
		} catch (NullPointerException | NumberFormatException | DAOException ex) {
			resp.setContentType("text/html");
			req.setAttribute("errorMessage", "There was an error while creating the xls file for the poll voting results.");
			req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(req, resp);
		}
	}
	
	private HSSFWorkbook getXlsOfPollVotingResults(long pollId) throws DAOException {
		List<PollOption> votingResults = DAOProvider.getDao().getPollOptions(pollId);
		votingResults.sort(Collections.reverseOrder((e1, e2) -> (int)(e1.getVotesCount() - e2.getVotesCount())));
		HSSFWorkbook resultsWorkbook = new HSSFWorkbook();
		HSSFSheet sheet = resultsWorkbook.createSheet("Poll voting results");
		HSSFRow rowhead = sheet.createRow(0);
		rowhead.createCell(0).setCellValue("Poll option name");
		rowhead.createCell(1).setCellValue("Number of votes");
		int nextRow = 1;
		for (PollOption result : votingResults) {
			HSSFRow row = sheet.createRow(nextRow);
			row.createCell(0).setCellValue(result.getOptionTitle());
			row.createCell(1).setCellValue(result.getVotesCount());
			nextRow++;
		}
		return resultsWorkbook;
	}
	
}
