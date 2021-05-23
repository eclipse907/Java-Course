package hr.fer.zemris.java.custom.scripting.elems;

/**
 * This class represents an integer constant expression
 * found in documents parsed by a parser. This class
 * inherits the Element class.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class ElementConstantInteger extends Element {
	
	private int value;
	
	/**
	 * This constructor creates a new integer constant
	 * expression with the given value.
	 * 
	 * @param value the value of the integer constant
	 *              expression.
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String asText() {
		return Integer.toString(value);
	}
	
	/**
	 * Returns the value of the integer constant
	 * expression.
	 * 
	 * @return the value of the integer constant
	 *         expression.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Checks if the given object is equal to this integer 
	 * constant expression.
	 * 
	 * @return true if the given object is equal, false
	 *         otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ElementConstantInteger)) {
			return false;
		}
		ElementConstantInteger otherInteger = (ElementConstantInteger)obj;
		return value == otherInteger.getValue();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return asText().hashCode();
	}
	
}
