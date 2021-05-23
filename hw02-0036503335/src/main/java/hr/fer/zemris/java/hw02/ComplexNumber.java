package hr.fer.zemris.java.hw02;

/**
 * This class represents a complex number with a real
 * and imaginary part. The class supports basic complex
 * number creation and arithmetics.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class ComplexNumber {
	
	private double real;
	private double imaginary;
	
	/**
	 * This constructor creates a complex number with
	 * the given values.
	 * 
	 * @param real the real value of the number.
	 * @param imaginary the imaginary value of the number.
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}
	
	/**
	 * This method creates a new complex number with the
	 * given real value and the imaginary value set to
	 * 0.
	 * 
	 * @param real the real value of the number.
	 * @return a new complex number.
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}
	
	/**
	 * This method creates a new complex number with the
	 * given imaginary value and the real value set to
	 * 0.
	 * 
	 * @param imaginary the imaginary value of the number.
	 * @return a new complex number.
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}
	
	/**
	 * This method creates a new complex number from the given
	 * magnitude and angle.
	 * 
	 * @param magnitude the magnitude of the complex number.
	 * @param angle the angle of the complex number.
	 * @return a new complex number.
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
	}
	
	/**
	 * This method parses a complex number from the given string. The string must contain first
	 * a signed or unsigned real part and then a signed imaginary part which has an
	 * "i" at the end of its value. The string can also contain just one of the former parts.
	 * If the string only contains an imaginary part the value doesn't have to be signed.
	 * The method also accepts imaginary values written in the notation "i" or "-i".
	 * 
	 * @param s the string from which to parse.
	 * @return a new complex number.
	 * @throws NumberFormatException if the string is not in the proper
	 *                               format.
	 */
	public static ComplexNumber parse(String s) {
		StringBuilder realPart = new StringBuilder();
		StringBuilder imaginaryPart = new StringBuilder();
		if (s.matches("^[+-]?\\d+(\\.\\d+)?$")) {
			realPart.append(s);
			imaginaryPart.append("0.0");
		} else if (s.matches("^[+-]?(\\d+(\\.\\d+)?)?i{1}$")) {
			realPart.append("0.0");
			imaginaryPart.append(s);
		} else if (s.matches("^[+-]?\\d+(\\.\\d+)?[+-]{1}(\\d+(\\.\\d+)?)?i{1}$")) {
			char[] input = s.toCharArray();
			realPart.append(input[0]);
			int index;
			for (index = 1; input[index] != '+' && input[index] != '-'; index++) {
				realPart.append(input[index]);
			}
			for (; index < input.length; index++) {
				imaginaryPart.append(input[index]);
			}
		} else {
			throw new NumberFormatException("Entered string is not a valid complex number."); 
		}
		String real = realPart.toString();
		String imaginary = imaginaryPart.toString();
		if (imaginary.equals("+i") || imaginary.equals("i")) {
			imaginary = "1.0";
		} else if (imaginary.equals("-i")) {
			imaginary = "-1.0";
		} else {
			imaginary = imaginary.replace("i", "");
		}
		return new ComplexNumber(Double.parseDouble(real), Double.parseDouble(imaginary));
	}
	
	/**
	 * Returns the real value of the number.
	 * 
	 * @return the real value of the number.
	 */
	public double getReal() {
		return real;
	}
	
	/**
	 * Returns the imaginary value of the number.
	 * 
	 * @return the imaginary value of the number.
	 */
	public double getImaginary() {
		return imaginary;
	}
	
	/**
	 * Returns the magnitude of the complex number.
	 * 
	 * @return the magnitude of the complex number.
	 */
	public double  getMagnitude() {
		return Math.sqrt(Math.pow(real, 2) + Math.pow(imaginary, 2));
	}
	
	/**
	 * Returns the angle of the complex number.
	 * 
	 * @return the angle of the complex number.
	 */
	public double getAngle() {
		double angle;
		if (real > 0 && imaginary > 0) {
			angle = Math.atan(imaginary / real);
		} else if (real < 0) {
			angle = Math.atan(imaginary / real) + Math.PI;
		} else {
			angle = Math.atan(imaginary / real) + 2 * Math.PI;
		}
		return angle;
	}
	
	/**
	 * Returns a new complex number representing the sum of this
	 * number and the complex number given.
	 * 
	 * @param c the complex number to add to this number.
	 * @return a new complex number representing the sum of this
	 *         number and the complex number given.
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(real + c.real, imaginary + c.imaginary);
	}
	

	/**
	 * Returns a new complex number representing the result of
	 * subtracting this number with the complex number given.
	 * 
	 * @param c the subtrahend.
	 * @return a new complex number representing the result of
	 *         subtracting this number with the complex number given.
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(real - c.real, imaginary - c.imaginary);
	}
	
	/**
	 * Returns a new complex number representing the result of multiplying
	 * this number with the complex number given.
	 * 
	 * @param c the multiplier.
	 * @return a new complex number representing the result of multiplying
	 *         this number with the complex number given.
	 */
	public ComplexNumber mul(ComplexNumber c) {
		return fromMagnitudeAndAngle(getMagnitude() * c.getMagnitude(), getAngle() + c.getAngle());
	}
	
	/**
	 * Returns a new complex number representing the result of dividing
	 * this number with the complex number given.
	 * 
	 * @param c the divisor.
	 * @return a new complex number representing the result of dividing
	 *         this number with the complex number given.
	 */
	public ComplexNumber div(ComplexNumber c) {
		return fromMagnitudeAndAngle(getMagnitude() / c.getMagnitude(), getAngle() - c.getAngle());
	}
	
    /**
     * Returns a new complex number representing the result of this
     * number to the power of the given value.
     * 
     * @param n the power of the complex number.
     * @return a new complex number representing the result of this
     *         number to the power of the given value.
     */
	public ComplexNumber power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException();
		}
		return fromMagnitudeAndAngle(Math.pow(getMagnitude(), n), n * getAngle());
	}
	
	 /**
     * Returns a new complex number representing the result of the root
     * of the given value from this complex number.
     * 
     * @param n the value of the root.
     * @return a new complex number representing the result of the root
     *         of the given value from this complex number.
     */
	public ComplexNumber[] root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}
		double magnitude = Math.pow(getMagnitude(), 1 / n);
		ComplexNumber [] numbers = new ComplexNumber[n];
		for (int i = 0; i < n; i++) {
			numbers[i] = fromMagnitudeAndAngle(magnitude, (getAngle() + 2 * i * Math.PI) / n);
		}
		return numbers;
	}
	
	/**
	 * Returns a string representation of the complex number.
	 * 
	 * @return the string representation of the complex number.
	 */
	@Override
	public String toString() {
		if (imaginary < 0) {
			return Double.toString(real) + Double.toString(imaginary) + "i";
		} else {
			return Double.toString(real) + "+" + Double.toString(imaginary) + "i";
		}
	}
}
