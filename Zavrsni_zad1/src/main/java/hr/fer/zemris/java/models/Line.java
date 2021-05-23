package hr.fer.zemris.java.models;

import java.awt.Color;
import java.awt.Point;

public class Line extends Shape {

	private Point start;
	private Point end;
	private Color lineColor;
	
	public Line(Point start, Point end, Color lineColor) {
		super(ShapeType.LINE);
		this.start = start;
		this.end = end;
		this.lineColor = lineColor;
	}

	/**
	 * @return the start
	 */
	public Point getStart() {
		return start;
	}

	/**
	 * @return the end
	 */
	public Point getEnd() {
		return end;
	}

	/**
	 * @return the lineColor
	 */
	public Color getLineColor() {
		return lineColor;
	}
	
}
