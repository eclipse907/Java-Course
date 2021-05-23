package hr.fer.zemris.java.gui.charts;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import javax.swing.JComponent;

/**
 * This class represents a graphical component that draws a bar chart
 * from the data given.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class BarChartComponent extends JComponent {

	private static final long serialVersionUID = -2290688286165650352L;
	private BarChart chart;
	private int distanceBetweenXLabelAndNumbers;
	private int distanceBetweenXNumbersAndChart;
	private int distanceBetweenYLabelAndNumbers;
	private int distanceBetweenYNumbersAndChart;
	private int distanceBetweenXLabelAndEnd;
	private int distanceBetweenYLabelAndEnd;
	private int distanceBetweenArrowAndAxis;
	private int arrowHeight;
	private int arrowWidth;
	private int distanceBetweenXAxisAndEnd;
	private int distanceBetweenYAxisAndEnd;

	/**
	 * Creates a new graphical component that draws a bar chart
	 * from the data given.
	 * 
	 * @param chart the data from which to draw a bar chart.
	 */
	public BarChartComponent(BarChart chart) {
		this.chart = chart;
		distanceBetweenXLabelAndNumbers = 30;
		distanceBetweenXNumbersAndChart = 15;
		distanceBetweenYLabelAndNumbers = 10;
		distanceBetweenYNumbersAndChart = 10;
		distanceBetweenArrowAndAxis = 10;
		arrowHeight = 10;
		arrowWidth = 10;
		distanceBetweenXLabelAndEnd = 5;
		distanceBetweenYLabelAndEnd = 20;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void paintComponent(Graphics g) {
		FontMetrics fm = g.getFontMetrics();
		distanceBetweenXAxisAndEnd = distanceBetweenXLabelAndEnd + 2 * fm.getHeight() + distanceBetweenXLabelAndNumbers + distanceBetweenXNumbersAndChart;
		distanceBetweenYAxisAndEnd = distanceBetweenYLabelAndEnd + fm.getHeight() + distanceBetweenYLabelAndNumbers +
				                     fm.stringWidth(Integer.toString(chart.getyMax())) + distanceBetweenYNumbersAndChart;
		int xAxisLength = getWidth() - distanceBetweenYAxisAndEnd;
		int yAxisLength = getHeight() - distanceBetweenXAxisAndEnd;
		drawXAxis(g, xAxisLength, yAxisLength);
		drawYAxis(g, yAxisLength);
		drawXAxisLabels(g, xAxisLength, yAxisLength);
		drawYAxisLabels(g, yAxisLength);
		drawXLabel(g, xAxisLength);
		drawYLabel(g, yAxisLength);
		drawBarChart(g, xAxisLength, yAxisLength);
	}
	
	/**
	 * Draws the x label of the bar chart.
	 * 
	 * @param g an object that can draw on this bar chart.
	 * @param xAxisLength the length of the x axis.
	 */
	private void drawXLabel(Graphics g, int xAxisLength) {
		FontMetrics fm = g.getFontMetrics();
		double x = distanceBetweenYAxisAndEnd + (xAxisLength - arrowHeight - distanceBetweenArrowAndAxis - fm.stringWidth(chart.getxDescription())) / 2.0;
		g.drawString(chart.getxDescription(), (int)x, getHeight() - distanceBetweenXLabelAndEnd);
	}
	
	/**
	 * Draws the y label of the bar chart.
	 * 
	 * @param g an object that can draw on this bar chart.
	 * @param yAxisLength the length of the y axis.
	 */
	private void drawYLabel(Graphics g, int yAxisLength) {
		Graphics2D graphics2d = (Graphics2D)g;
		AffineTransform defaultAt = graphics2d.getTransform();
		AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI / 2);	 
		graphics2d.setTransform(at);
		FontMetrics fm = g.getFontMetrics();
		double x = -(distanceBetweenYAxisAndEnd + (yAxisLength - arrowHeight - distanceBetweenArrowAndAxis + fm.stringWidth(chart.getyDescription())) / 2.0);
		graphics2d.drawString(chart.getyDescription(), (int)x, distanceBetweenYLabelAndEnd);
		graphics2d.setTransform(defaultAt);
	}
	
	/**
	 * Draws the x axis of this bar chart.
	 * 
	 * @param g an object that can draw on this bar chart.
	 * @param xAxisLength the length of the x axis.
	 * @param yAxisLength the length of the y axis.
	 */
	private void drawXAxis(Graphics g, int xAxisLength, int yAxisLength) {
		g.drawLine(distanceBetweenYAxisAndEnd - distanceBetweenYNumbersAndChart, yAxisLength, getWidth(), yAxisLength);
		g.drawLine(getWidth() - arrowHeight, (int)(yAxisLength - arrowWidth / 2.0), getWidth(), yAxisLength);
		g.drawLine(getWidth() - arrowHeight, (int)(yAxisLength + arrowWidth / 2.0), getWidth(), yAxisLength);
		double xGapWidth = (xAxisLength - arrowHeight - distanceBetweenArrowAndAxis) / (double)chart.getValues().size();
		double currentX = distanceBetweenYAxisAndEnd;
		for (int i = 0; i < chart.getValues().size(); i++) {
			currentX += xGapWidth;
			g.drawLine((int)currentX, yAxisLength + distanceBetweenXNumbersAndChart, (int)currentX, 0);
		}
	}
	
	/**
	 * Draws the y axis of this bar chart.
	 * @param g an object that can draw on this bar chart.
	 * @param yAxisLength the length of the y axis.
	 */
	private void drawYAxis(Graphics g, int yAxisLength) {
		g.drawLine(distanceBetweenYAxisAndEnd, yAxisLength + distanceBetweenXNumbersAndChart, distanceBetweenYAxisAndEnd, 0);
		g.drawLine((int)(distanceBetweenYAxisAndEnd - arrowWidth / 2.0), arrowHeight, distanceBetweenYAxisAndEnd, 0);
		g.drawLine((int)(distanceBetweenYAxisAndEnd + arrowWidth / 2.0), arrowHeight, distanceBetweenYAxisAndEnd, 0);
		int numOfGaps = (chart.getyMax() - chart.getyMin()) / chart.getGapSize();
		double yGapHeight = (yAxisLength - arrowHeight - distanceBetweenArrowAndAxis) / (double)numOfGaps;
		double currentY = yAxisLength;
		for (int i = 0; i < numOfGaps; i++) {
			currentY -= yGapHeight;
			g.drawLine(distanceBetweenYAxisAndEnd - distanceBetweenYNumbersAndChart, (int)currentY, getWidth(), (int)currentY);
		}
	}
	
	/**
	 * Draws the x axis labels of this bar chart.
	 * 
	 * @param g an object that can draw on this bar chart.
	 * @param xAxisLength the length of the x axis.
	 * @param yAxisLength the length of the y axis.
	 */
	private void drawXAxisLabels(Graphics g, int xAxisLength, int yAxisLength) {
		double xGapWidth = (xAxisLength - arrowHeight - distanceBetweenArrowAndAxis) / (double)chart.getValues().size();
		double currentX = distanceBetweenYAxisAndEnd;
		FontMetrics fm = g.getFontMetrics();
		for (XYValue value : chart.getValues()) {
			String xValue = Integer.toString(value.getX());
			g.drawString(xValue, (int)(currentX + xGapWidth / 2.0 - fm.stringWidth(xValue) / 2.0), yAxisLength + distanceBetweenXNumbersAndChart);
			currentX += xGapWidth;
		}
	}
	
	/**
	 * Draws the y axis labels of this bar chart.
	 * 
	 * @param g an object that can draw on this bar chart.
	 * @param yAxisLength the length of the y axis.
	 */
	private void drawYAxisLabels(Graphics g, int yAxisLength) {
		int numOfGaps = (chart.getyMax() - chart.getyMin()) / chart.getGapSize();
		double yGapHeight = (yAxisLength - arrowHeight - distanceBetweenArrowAndAxis) / (double)numOfGaps;
		double currentY = yAxisLength;
		int currentNumber = chart.getyMin();
		FontMetrics fm = g.getFontMetrics();
		for (int i = 0; i <= numOfGaps; i++) {
			String yValue = Integer.toString(currentNumber);
			g.drawString(yValue, distanceBetweenYAxisAndEnd - distanceBetweenYNumbersAndChart - fm.stringWidth(yValue),
					     (int)(currentY + fm.getHeight() / 3.0)
					    );
			currentY -= yGapHeight;
			currentNumber += chart.getGapSize();
		}
	}
	
	/**
	 * Draws the bar charts of this bar chart.
	 * 
	 * @param g an object that can draw on this bar chart.
	 * @param xAxisLength the length of the x axis.
	 * @param yAxisLength the length of the y axis.
	 */
	private void drawBarChart(Graphics g, int xAxisLength, int yAxisLength) {
		double barChartWidth = (xAxisLength - arrowHeight - distanceBetweenArrowAndAxis) / (double)chart.getValues().size();
		int numOfGaps = (chart.getyMax() - chart.getyMin()) / chart.getGapSize();
		double barChartGapHeight = (yAxisLength - arrowHeight - distanceBetweenArrowAndAxis) / (double)numOfGaps;
		double currentX = distanceBetweenYAxisAndEnd;
		for (XYValue value : chart.getValues()) {
			double numOfGapsNeeded = value.getY() / (double)chart.getGapSize();
			double barChartHeight = numOfGapsNeeded * barChartGapHeight;
			g.fillRect((int)currentX, (int)(yAxisLength - barChartHeight), (int)barChartWidth, (int)barChartHeight);
			currentX += barChartWidth;
		}
	}

}
