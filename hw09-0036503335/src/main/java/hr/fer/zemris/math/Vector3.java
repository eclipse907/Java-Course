package hr.fer.zemris.math;

/**
 * This class represents a vector in a 3D space.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class Vector3 {

	private double x;
	private double y;
	private double z;
	
	/**
	 * Creates a new 3D vector with the given values.
	 * 
	 * @param x the value of the x component of the vector.
	 * @param y the value of the y component of the vector.
	 * @param z the value of the z component of the vector.
	 */
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Returns the norm (length) of this vector.
	 * 
	 * @return the norm (length) of this vector.
	 */
	public double norm() {
		return Math.sqrt(x * x + y * y + z * z);
	}
	
	/**
	 * Returns a new vector representing the result of
	 * normalizing this vector.
	 * 
	 * @return a new vector representing the result of
	 *         normalizing this vector.
	 */
	public Vector3 normalized() {
		double norm = norm();
		return new Vector3(x / norm, y / norm, z / norm);
	}
	
	/**
	 * Returns a new vector representing the result of adding
	 * the vector given to this vector.
	 * 
	 * @param other the vector to add to this vector.
	 * @return a new vector representing the result of adding
	 *         the vector given to this vector.
	 */
	public Vector3 add(Vector3 other) {
		return new Vector3(x + other.getX(), y + other.getY(), z + other.getZ());
	}
	
	/**
	 * Returns a new vector representing the result of subtracting the vector given
	 * from this vector.
	 * 
	 * @param other the vector to subtract from this vector.
	 * @return a new vector representing the result of subtracting the vector given
	 *         from this vector.
	 */
	public Vector3 sub(Vector3 other) {
		return new Vector3(x - other.getX(), y - other.getY(), z - other.getZ());
	}
	
	/**
	 * Returns the dot product of this vector and the vector given.
	 * 
	 * @param other the vector to dot multiply with this vector.
	 * @return the dot product of this vector and the vector given.
	 */
	public double dot(Vector3 other) {
		return x * other.getX() + y * other.getY() + z * other.getZ();
	}
	
	/**
	 * Returns the cross product of this vector and the vector given.
	 * 
	 * @param other the vector to cross multiply with this vector.
	 * @return the cross product of this vector and the vector given.
	 */
	public Vector3 cross(Vector3 other) {
		return new Vector3(
				           y * other.getZ() - z * other.getY(),
				           z * other.getX() - x * other.getZ(),
				           x * other.getY() - y * other.getX()
				          );
	}
	
	/**
	 * Returns a new vector representing the result of scaling
	 * this vector with the value given.
	 * 
	 * @param s the scale factor.
	 * @return a new vector representing the result of scaling
	 *         this vector with the value given.
	 */
	public Vector3 scale(double s) {
		return new Vector3(s * x, s * y, s * z);
	}
	
	/**
	 * Returns the value of the cos of the angle between this
	 * vector and the vector given.
	 * 
	 * @param other the other vector.
	 * @return the value of the cos of the angle between this
	 *         vector and the vector given.
	 */
	public double cosAngle(Vector3 other) {
		return dot(other) / (norm() * other.norm());
	}
	
	/**
	 * Returns the value of the x component of this vector.
	 * 
	 * @return the value of the x component of this vector.
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Returns the value of the y component of this vector.
	 * 
	 * @return the value of the y component of this vector.
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Returns the value of the z component of this vector.
	 * 
	 * @return the value of the z component of this vector.
	 */
	public double getZ() {
		return z;
	}
	
	/**
	 * Returns an array containing the values of the components
	 * of this vector. The values are stored in the array in the
	 * following order: x, y, z.
	 * 
	 * @return an array containing the values of the components
	 *         of this vector.
	 */
	public double[] toArray() {
		double[] elements = {x, y, z};
		return elements;
	}
	
	/**
	 * Returns a string representation of this vector.
	 * 
	 * @return a string representation of this vector.
	 */
	@Override
	public String toString() {
		return "(" + Double.toString(x) + ", " + Double.toString(y) + ", " + Double.toString(z) + ")";
	}
	
}
