package hr.fer.zemris.java.custom.scripting.elems;

/**
 * This class represents a string expression found
 * in documents parsed by a parser. This class
 * inherits the Element class.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class ElementString extends Element {

	private String value;
	
	/**
	 * This constructor creates a new string
	 * expression with the given value.
	 * 
	 * @param value the value of the string.
	 */
	public ElementString(String value) {
		this.value = value;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String asText() {
		return value;
	}

	/**
	 * Returns the value of the string.
	 * 
	 * @return the value of the string.
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "\"" + asText() + "\"";
	}
	
	/**
	 * Checks if the given object is equal to this string
	 * expression.
	 * 
	 * @return true if the given object is equal, false
	 *         otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ElementString)) {
			return false;
		}
		ElementString otherString = (ElementString)obj;
		return value.equals(otherString.getValue());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return value.hashCode();
	}
	
}
