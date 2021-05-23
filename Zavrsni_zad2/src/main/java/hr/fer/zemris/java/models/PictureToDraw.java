package hr.fer.zemris.java.models;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class PictureToDraw {

	private Point size;
	private Color backroundColor;
	private List<Shape> shapes;
	
	public PictureToDraw(Point size, Color backroundColor) {
		this.size = size;
		this.backroundColor = backroundColor;
		shapes = new ArrayList<>();
	}

	/**
	 * @return the size
	 */
	public Point getSize() {
		return size;
	}

	/**
	 * @return the backroundColor
	 */
	public Color getBackroundColor() {
		return backroundColor;
	}

	/**
	 * @return the shapes
	 */
	public List<Shape> getShapes() {
		return shapes;
	}
	
	public void addShape(Shape shape) {
		shapes.add(shape);
	}
	
}
