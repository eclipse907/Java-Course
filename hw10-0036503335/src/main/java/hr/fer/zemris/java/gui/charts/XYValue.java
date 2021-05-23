package hr.fer.zemris.java.gui.charts;

/**
 * This class represents data in a 
 * bar chart.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class XYValue {

	private int x;
	private int y;
	
	/**
	 * Creates new bar chart data with the values given.
	 * 
	 * @param x the x axis value of the data.
	 * @param y the y axis value of the data.
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns the x axis value of the data.
	 * 
	 * @return the x axis value of the data.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Returns the y axis value of the data.
	 * 
	 * @return the y axis value of the data.
	 */
	public int getY() {
		return y;
	}
	
}
