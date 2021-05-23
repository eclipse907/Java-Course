package hr.fer.zemris.java.votingapp.servlets;

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
import hr.fer.zemris.java.votingapp.dao.DAOException;
import hr.fer.zemris.java.votingapp.dao.DAOProvider;
import hr.fer.zemris.java.votingapp.model.PollOption;

@WebServlet("servlets/voting-graphics")
public class VotingResultsPieChartServlet extends HttpServlet {

	private static final long serialVersionUID = -5994252772797062409L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String pollID = req.getParameter("pollID");
			if (pollID == null) {
				throw new NullPointerException();
			}
			long pollId = Long.parseLong(pollID);
			resp.setContentType("image/png");
	        ChartUtils.writeChartAsPNG(resp.getOutputStream(), getPieChartOfPollVotingResults(pollId), 400, 400);
		} catch (NullPointerException | NumberFormatException | DAOException ex) {
			resp.setContentType("text/html");
			req.setAttribute("errorMessage", "There was an error while creating the poll voting results pie chart.");
			req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(req, resp);
		}
	}
	
	private JFreeChart getPieChartOfPollVotingResults(long pollId) throws DAOException {
		List<PollOption> results = DAOProvider.getDao().getPollOptions(pollId);
		DefaultPieDataset dataset = new DefaultPieDataset();
		for(PollOption result : results) {
			if (result.getVotesCount() > 0) {
				dataset.setValue(result.getOptionTitle(), result.getVotesCount());
			}
		}
        JFreeChart chart = ChartFactory.createPieChart3D(
                "Poll voting results",
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
