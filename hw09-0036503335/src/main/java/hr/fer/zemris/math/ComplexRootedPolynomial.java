package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a complex rooted polynomial and uses the Complex class
 * from the hr.fer.zemris.math package to represent its roots.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class ComplexRootedPolynomial {
	
	private Complex constant;
	private List<Complex> roots;
	
	/**
	 * Creates a new complex rooted polynomial with the given values.
	 * The given roots are interpreted from left to right in the 
	 * following order: z1, z2, ....., zn.
	 *  
	 * @param constant the constant of the complex rooted polynomial.
	 * @param roots the roots of the complex rooted polynomial.
	 */
	public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
		this.constant = constant;
		this.roots = new ArrayList<>();
		if (roots != null) {
			for (Complex root : roots) {
				this.roots.add(root);
			}
		}
	}
	
	/**
	 * Calculates the value of this complex rooted polynomial in the 
	 * complex point given.
	 * 
	 * @param z the point for which to calculate the value of the
	 *          complex rooted polynomial.
	 * @return a new complex number representing the value of the
	 *         complex rooted polynomial in the complex point given.
	 */
	public Complex apply(Complex z) {
		Complex value = constant;
		for (Complex root : roots) {
			value = value.multiply(z.sub(root));
		}
		return value;
	}
	
	/**
	 * Converts this complex rooted polynomial to a complex polynomial
	 * from the hr.fer.zemris.math package.
	 * 
	 * @return a new complex polynomial which represents this complex
	 *         rooted polynomial.  
	 */
	public ComplexPolynomial toComplexPolynom() {
		Complex[] coefficients = new Complex[roots.size() + 1];
		if (roots.size() == 0) {
			coefficients[0] = constant;
			return new ComplexPolynomial(coefficients);
		}
		coefficients[0] = roots.get(0);
		coefficients[1] = Complex.ONE;
		for (int i = 1; i < roots.size(); i++) {
			coefficients[i + 1] = Complex.ONE;
			int j = 0;
			while (i - j > 0) {
				coefficients[i - j] = coefficients[i - j - 1].add(coefficients[i - j].multiply(roots.get(i)));
				j++;
			}
			coefficients[0] = coefficients[0].multiply(roots.get(i));
		}
		for (int i = 0; i < coefficients.length; i++) {
			coefficients[i] = coefficients[i].multiply(constant);
		}
		return new ComplexPolynomial(coefficients);
	}
	
	/**
	 * Returns a string representation of this complex rooted polynomial.
	 * 
	 * @return a string representation of this complex rooted polynomial.
	 */
	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append(constant.toString());
		for (Complex root : roots) {
			string.append("*(z-" + root.toString() + ")");
		}
		return string.toString();
	}
	
	/**
	 * Finds the index of the closest root from the given complex number
	 * that is within threshold. First root has index 0, second index 1, 
	 * etc.
	 * 
	 * @param z the complex number for which to find the index.
	 * @param treshold the minimal distance from the given complex
	 *                 number.
	 * @return the index of the closest root if found, -1 otherwise.
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		int index = -1;
		double minDistance = treshold;
		for (int i = 0; i < roots.size(); i++) {
			double diff = z.sub(roots.get(i)).module();
			if (diff < minDistance) {
				index = i;
				minDistance = diff;
			}
		}
		return index;
	}
}
