package hr.fer.zemris.java.custom.scripting.elems;

/**
 * This class represents a decimal number constant
 * expression found in documents parsed by a parser.
 * This class inherits the Element class.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class ElementConstantDouble extends Element {
	
	private double value;
	
	/**
	 * This constructor creates a new decimal number 
	 * constant expression with the given value.
	 * 
	 * @param value the value of the decimal number 
	 *              constant expression.
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String asText() {
		return Double.toString(value);
	}

	/**
	 * Returns the value of the decimal number 
	 * constant expression.
	 * 
	 * @return the value of the decimal number 
	 *         constant expression.
	 */
	public double getValue() {
		return value;
	}
	
	/**
	 * Checks if the given object is equal to this decimal 
	 * number constant expression.
	 * 
	 * @return true if the given object is equal, false
	 *         otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ElementConstantDouble)) {
			return false;
		}
		ElementConstantDouble otherDouble = (ElementConstantDouble)obj;
		return value == otherDouble.getValue();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return asText().hashCode();
	}
}
