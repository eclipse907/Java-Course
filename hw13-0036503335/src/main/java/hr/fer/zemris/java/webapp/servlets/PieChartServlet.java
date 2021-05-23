package hr.fer.zemris.java.webapp.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.util.Rotation;
import org.jfree.data.general.DefaultPieDataset;

@WebServlet("/reportImage")
public class PieChartServlet extends HttpServlet {

	private static final long serialVersionUID = -8610993778259783188L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("image/png");
        ChartUtils.writeChartAsPNG(resp.getOutputStream(), getChart(), 500, 270);
	}
	
	private JFreeChart getChart() {
		DefaultPieDataset result = new DefaultPieDataset();
        result.setValue("Linux", 20);
        result.setValue("Mac", 25);
        result.setValue("Windows", 55);
        JFreeChart chart = ChartFactory.createPieChart3D(
                "OS usage survey results",
                result,
                true,
                true,
                false
            );
        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        return chart;
	}

}
