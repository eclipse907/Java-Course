package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a complex polynomial and uses the Complex class
 * from the hr.fer.zemris.math package to represent its coefficients.
 * It supports basic arithmetic operations typical for a complex polynomial.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class ComplexPolynomial {
	
	private List<Complex> coefficients;
	
	/**
	 * Creates a new complex polynomial with the given coefficients.
	 * The given coefficients are interpreted from left to right in
	 * the following order: z0, z1, ....., zn.
	 * 
	 * @param factors the coefficients of the complex polynomial. 
	 */
	public ComplexPolynomial(Complex ...factors) {
		this.coefficients = new ArrayList<>();
		for (Complex coefficient : factors) {
			this.coefficients.add(coefficient);
		}
	}
	
	/**
	 * Returns the order of this complex polynomial.
	 * 
	 * @return the order of this complex polynomial.
	 */
	public short order() {
		return (short)(coefficients.size() - 1);
	}
	
	/**
	 * Returns a new complex polynomial representing the result of
	 * multiplying this complex polynomial with the complex polynomial
	 * given.
	 *  
	 * @param p the complex polynomial with which to multiply this
	 *          complex polynomial.
	 * @return a new complex polynomial representing the result of
	 *         multiplying this complex polynomial with the complex polynomial
	 *         given.
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] result = new Complex[coefficients.size() + p.coefficients.size() - 1];
		for (int i = 0; i < p.coefficients.size(); i++) {
			for (int j = 0; j < coefficients.size(); j++) {
				if (result[j + i] == null) {
					result[j + i] = coefficients.get(j).multiply(p.coefficients.get(i));
				} else {
					result[j + i] = result[j + i].add(coefficients.get(j).multiply(p.coefficients.get(i)));
				}
			}
		}
		for (int i = 0; i < result.length; i++) {
			if (result[i] == null) {
				result[i] = Complex.ZERO;
			}
		}
		return new ComplexPolynomial(result);
	}
	
	/**
	 * Returns a new complex polynomial representing the result of deriving
	 * this complex polynomial.
	 * 
	 * @return a new complex polynomial representing the result of deriving
	 *         this complex polynomial.
	 */
	public ComplexPolynomial derive() {
		Complex[] result = new Complex[coefficients.size() - 1];
		for (int i = 0; i < result.length; i++) {
			result[i] = coefficients.get(i + 1).multiply(new Complex(i + 1, 0));
		}
		return new ComplexPolynomial(result);
	}
	
	/**
	 * Calculates the value of this complex polynomial in the complex
	 * point given.
	 * 
	 * @param z the point for which to calculate the value of the
	 *          complex polynomial.
	 * @return a new complex number representing the value of the
	 *         complex polynomial in the complex point given.
	 */
	public Complex apply(Complex z) {
		Complex sum = coefficients.get(0);
		for (int i = 1; i < coefficients.size(); i++) {
			sum = sum.add(z.power(i).multiply(coefficients.get(i)));
		}
		return sum;
	}
	
	/**
	 * Returns a string representation of this complex polynomial.
	 * 
	 * @return a string representation of this complex polynomial.
	 */
	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		for (int i = coefficients.size() - 1; i > 0; i--) {
			string.append(coefficients.get(i).toString() + "*z^" + Integer.toString(i) + "+");
		}
		string.append(coefficients.get(0).toString());
		return string.toString();
	}

}
