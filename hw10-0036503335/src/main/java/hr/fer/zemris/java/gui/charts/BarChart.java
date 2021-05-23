package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * This class represents the data necessary to draw
 * a bar chart.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class BarChart {
	
	private List<XYValue> values;
	private String xDescription;
	private String yDescription;
	private int yMin;
	private int yMax;
	private int gapSize;

	/**
	 * Creates a new bar chart with the data given.
	 * 
	 * @param values the values of the bar chart.
	 * @param xDescription the label of the x axis of the bar chart.
	 * @param yDescription the label of the y axis of the bar chart.
	 * @param yMin the minimal y value of the bar chart.
	 * @param yMax the max y value of the bar chart.
	 * @param gapSize the size of the gap between labeled points of the
	 *                y axis.
	 * @throws IllegalArgumentException if the given data is wrong.
	 */
	public BarChart(List<XYValue> values, String xDescription, String yDescription, int yMin, int yMax, int gapSize) {
		this.values = values;
		this.xDescription = xDescription;
		this.yDescription = yDescription;
		if (yMin < 0) {
			throw new IllegalArgumentException("Minimal y must be a positive number.");
		}
		this.yMin = yMin;
		if (yMax < yMin) {
			throw new IllegalArgumentException("Maximal y must be greater than minimal y.");
		}
		this.yMax = yMax;
		this.gapSize = gapSize;
		for (XYValue value : values) {
			if (value.getY() < yMin) {
				throw new IllegalArgumentException("Given value is smaller than minimal y.");
			}
		}
	}

	/**
	 * @return the values
	 */
	public List<XYValue> getValues() {
		return values;
	}

	/**
	 * @return the xDescription
	 */
	public String getxDescription() {
		return xDescription;
	}

	/**
	 * @return the yDescription
	 */
	public String getyDescription() {
		return yDescription;
	}

	/**
	 * @return the yMin
	 */
	public int getyMin() {
		return yMin;
	}

	/**
	 * @return the yMax
	 */
	public int getyMax() {
		return yMax;
	}

	/**
	 * @return the gapSize
	 */
	public int getGapSize() {
		return gapSize;
	}
	
}
