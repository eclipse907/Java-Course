package hr.fer.zemris.java.webapp.servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

@WebServlet("/voting-xls")
public class VotingResultsXlsServlet extends HttpServlet {

	private static final long serialVersionUID = 5004261861089573752L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String bandFile = req.getServletContext().getRealPath("/WEB-INF/voting-definition.txt");
		String resultFile = req.getServletContext().getRealPath("/WEB-INF/voting-results.txt");
		try {
			HSSFWorkbook resultsWorkbook = getXlsOfVotingResults(bandFile, resultFile);
			resp.setContentType("application/octet-stream");
			resp.setHeader("Content-Disposition", "attachment; filename=\"voting results.xls\"");
			resultsWorkbook.write(resp.getOutputStream());
		} catch (IOException | NumberFormatException ex) {
			resp.setContentType("text/html");
			req.setAttribute("errorMessage", "There was an error while creating the xls file for voting results.");
			req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(req, resp);
		}
	}
	
	private HSSFWorkbook getXlsOfVotingResults(String bandFile, String resultFile) throws IOException, NumberFormatException {
		List<String[]> results = VotingResultsServlet.getVotingResults(resultFile, VotingServlet.getBands(bandFile));
		HSSFWorkbook resultsWorkbook = new HSSFWorkbook();
		HSSFSheet sheet = resultsWorkbook.createSheet("Voting results");
		HSSFRow rowhead = sheet.createRow(0);
		rowhead.createCell(0).setCellValue("Band name");
		rowhead.createCell(1).setCellValue("Number of votes");
		int nextRow = 1;
		for (String[] result : results) {
			HSSFRow row = sheet.createRow(nextRow);
			row.createCell(0).setCellValue(result[1]);
			row.createCell(1).setCellValue(Long.parseLong(result[2]));
			nextRow++;
		}
		return resultsWorkbook;
	}
	
}
