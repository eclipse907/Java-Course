package hr.fer.zemris.java.models;

import java.awt.Color;
import java.awt.Point;

public class Oval extends Shape {

	private Point center;
	private int rx;
	private int ry;
	private Color lineColor;
	private Color fillColor;
	
	public Oval(Point center, int rx, int ry, Color lineColor, Color fillColor) {
		super(ShapeType.OVAL);
		this.center = center;
		this.rx = rx;
		this.ry = ry;
		this.lineColor = lineColor;
		this.fillColor = fillColor;
	}

	/**
	 * @return the center
	 */
	public Point getCenter() {
		return center;
	}

	/**
	 * @return the rx
	 */
	public int getRx() {
		return rx;
	}

	/**
	 * @return the ry
	 */
	public int getRy() {
		return ry;
	}

	/**
	 * @return the lineColor
	 */
	public Color getLineColor() {
		return lineColor;
	}

	/**
	 * @return the fillColor
	 */
	public Color getFillColor() {
		return fillColor;
	}
	
}
