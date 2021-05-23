package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a complex number with a real and
 * imaginary part. It supports basic arithmetic operations
 * typical for complex numbers and provides several static
 * complex numbers.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class Complex {

	private double re;
	private double im;
	
	public static final Complex ZERO = new Complex(0,0);
	public static final Complex ONE = new Complex(1,0);
	public static final Complex ONE_NEG = new Complex(-1,0);
	public static final Complex IM = new Complex(0,1);
	public static final Complex IM_NEG = new Complex(0,-1);
	
	/**
	 * Creates a new complex number with the real and
	 * imaginary part set to zero.
	 */
	public Complex() {
		this.re = 0;
		this.im = 0;
	}
	
	/**
	 * Creates a new complex number with the given values.
	 * 
	 * @param re the real value of the complex number
	 * @param im the imaginary value of the complex number.
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}
	
	/**
	 * Returns the module of this complex number.
	 * 
	 * @return the module of this complex number.
	 */
	public double module() {
		return Math.sqrt(re * re + im * im);
	}
	
	/**
	 * Returns a new complex number representing the result of multiplying
	 * this complex number with the given complex number.
	 * 
	 * @param c the complex number to multiply with this number.
	 * @return a new complex number representing the result of multiplying
	 *         this complex number with the given complex number.
	 */
	public Complex multiply(Complex c) {
		return new Complex(re * c.re - im * c.im, re * c.im + im * c.re);
	}
	
	/**
	 * Returns a new complex number representing the result of dividing this
	 * complex number with the complex number given.
	 * 
	 * @param c the complex number with which to divide this number.
	 * @return a new complex number representing the result of dividing this
	 *         complex number with the complex number given.
	 */
	public Complex divide(Complex c) {
		Complex numerator = multiply(new Complex(c.re, -c.im));
		double denominator = c.re * c.re + c.im * c.im;
		return new Complex(numerator.re / denominator, numerator.im / denominator);
	}
	
	/**
	 * Returns a new complex number representing the result of adding the complex
	 * number given to this complex number.
	 * 
	 * @param c the complex number to add to this number.
	 * @return a new complex number representing the result of adding the complex
	 *         number given to this complex number.
	 */
	public Complex add(Complex c) {
		return new Complex(re + c.re, im + c.im);
	}
	
	/**
	 * Returns a new complex number representing the result of subtracting the complex
	 * number given from this complex number.
	 * 
	 * @param c the complex number to subtract from this number.
	 * @return a new complex number representing the result of subtracting the complex
	 *         number given from this complex number.
	 */
	public Complex sub(Complex c) {
		return new Complex(re - c.re, im - c.im);
	}
	
	/**
	 * Returns a new complex number representing the negation of this
	 * complex number.
	 * 
	 * @return a new complex number representing the negation of this
	 *         complex number.
	 */
	public Complex negate() {
		return new Complex(-re, -im);
	}
	
	/**
	 * Returns a new complex number representing the result of raising
	 * this complex number to the power given.
	 * 
	 * @param n the power to raise this number to.
	 * @return a new complex number representing the result of raising
	 *         this complex number to the power given.
	 */
	public Complex power(int n) {
		double r = Math.pow(module(), n);
		double angle = angle() * n;
		return new Complex(r * Math.cos(angle), r * Math.sin(angle));
	}
	
	/**
	 * Calculates all the n roots of this complex number for the number
	 * given.
	 * 
	 * @param n the root to calculate.
	 * @return a list containing all the n roots of this complex number. 
	 */
	public List<Complex> root(int n) {
		List<Complex> roots = new ArrayList<>();
		double r = Math.pow(module(), 1.0 / (double)n);
		double angle = angle();
		for (int i = 0; i < n; i++) {
			roots.add(new Complex(r * Math.cos((angle + 2 * Math.PI * i) / n), r * Math.sin((angle + 2 * Math.PI * i) / n)));
		}
		return roots;
	}
	
	/**
	 * Returns a string representation of this complex number.
	 * 
	 * @return a string representation of this complex number.
	 */
	@Override
	public String toString() {
		if (im >= 0) {
			return "(" + Double.toString(re) + "+i" + Double.toString(im) + ")";
		} else {
			return "(" + Double.toString(re) + "-i" + Double.toString(Math.abs(im)) + ")";
		}
	}
	
	/**
	 * Returns the angle of this complex number.
	 * 
	 * @return the angle of this complex number.
	 */
	private double angle() {
		double angle;
		if (re > 0 && im > 0) {
			angle = Math.atan(im / re);
		} else if (re < 0) {
			angle = Math.atan(im / re) + Math.PI;
		} else {
			angle = Math.atan(im / re) + 2 * Math.PI;
		}
		return angle;
	}

}
