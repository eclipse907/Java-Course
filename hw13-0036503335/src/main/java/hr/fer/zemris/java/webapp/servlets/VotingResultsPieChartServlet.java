package hr.fer.zemris.java.webapp.servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.util.Rotation;
import org.jfree.data.general.DefaultPieDataset;

@WebServlet("/voting-graphics")
public class VotingResultsPieChartServlet extends HttpServlet {

	private static final long serialVersionUID = -5994252772797062409L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String bandFile = req.getServletContext().getRealPath("/WEB-INF/voting-definition.txt");
		String resultFile = req.getServletContext().getRealPath("/WEB-INF/voting-results.txt");
		try {
			resp.setContentType("image/png");
	        ChartUtils.writeChartAsPNG(resp.getOutputStream(), getPieChartOfVotingResults(bandFile, resultFile), 400, 400);
		} catch (IOException | NumberFormatException ex) {
			resp.setContentType("text/html");
			req.setAttribute("errorMessage", "There was an error while creating the voting results pie chart.");
			req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(req, resp);
		}
	}
	
	private JFreeChart getPieChartOfVotingResults(String bandFile, String resultFile) throws IOException, NumberFormatException {
		List<String[]> results = VotingResultsServlet.getVotingResults(resultFile, VotingServlet.getBands(bandFile));
		DefaultPieDataset dataset = new DefaultPieDataset();
		for(String[] result : results) {
			Long numOfVotes = Long.parseLong(result[2]);
			if (numOfVotes.longValue() > 0) {
				dataset.setValue(result[1], numOfVotes);
			}
		}
        JFreeChart chart = ChartFactory.createPieChart3D(
                "Favorite band voting results",
                dataset,
                true,
                true,
                false
            );
        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(90);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        return chart;
	}
	
}
