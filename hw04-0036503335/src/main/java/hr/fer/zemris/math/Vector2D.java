package hr.fer.zemris.math;

/**
 * This class represents a vector in a 2D space. It
 * supports basic operations typical for a vector.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class Vector2D {
	
	private double x;
	private double y;
	
	/**
	 * Creates a new 2D vector with the given
	 * values.
	 * 
	 * @param x the x axis value of the vector.
	 * @param y the y axis value of the vector.
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Returns the x axis value of the vector.
	 * 
	 * @return the x axis value of the vector.
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Returns the y axis value of the vector.
	 * 
	 * @return the y axis value of the vector.
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Translates this vector for the given
	 * value.
	 * 
	 * @param offset the value of the translation.
	 */
	public void translate(Vector2D offset) {
		x = x + offset.getX();
		y = y + offset.getY();
	}
	
	/**
	 * Returns a new 2D vector which represents this vector
	 * translated for the given value.
	 * 
	 * @param offset the value of the translation.
	 * @return a new 2D vector which represents this vector
	 *         translated for the given value.
	 */
	public Vector2D translated(Vector2D offset) {
		return new Vector2D(x + offset.getX(), y + offset.getY());
	}
	
	/**
	 * Rotates this vector for the given value.
	 * 
	 * @param angle the rotation angle. Must be in
	 *              radians.
	 */
	public void rotate(double angle) {
		double rX = Math.cos(angle) * x - Math.sin(angle) * y;
		double rY = Math.sin(angle) * x + Math.cos(angle) * y;
		x = rX;
		y = rY;
	}
	
	/**
	 * Returns a new 2D vector which represents this vector
	 * rotated for the given value.
	 * 
	 * @param angle the rotation angle. Must be in
	 *              radians.
	 * @return a new 2D vector which represents this vector
	 *         rotated for the given value.
	 */
	public Vector2D rotated(double angle) {
		return new Vector2D(
				Math.cos(angle) * x - Math.sin(angle) * y,
				Math.sin(angle) * x + Math.cos(angle) * y
				);
	}
	
	/**
	 * Scales this vector with the given value.
	 * 
	 * @param scaler the scale factor.
	 */
	public void scale(double scaler) {
		x = x * scaler;
		y = y * scaler;
	}
	
	/**
	 * Returns a new 2D vector which represents this vector
	 * scaled with the given value.
	 * 
	 * @param scaler the scale factor.
	 * @return a new 2D vector which represents this vector
	 *         scaled with the given value.
	 */
	public Vector2D scaled(double scaler) {
		return new Vector2D(x * scaler, y * scaler);
	}
	
	/**
	 * Returns a new 2D vector which is a copy of this
	 * vector.
	 * 
	 * @return a new 2D vector which is a copy of this
	 *         vector.
	 */
	public Vector2D copy() {
		return new Vector2D(x, y);
	}
	
}
