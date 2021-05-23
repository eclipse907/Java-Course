package hr.fer.zemris.java.custom.scripting.elems;

/**
 * This class is a template for the representation of
 * expressions found in documents parsed by a
 * parser.
 *  
 * @author Tin Reiter
 * @version 1.0
 */
public class Element {
	
	/**
	 * Returns the string representation of this
	 * expression.
	 * 
	 * @return the string representation of this
	 *         expression.
	 */
	public String asText() {
		return "";
	}
	
	/**
	 * Returns the string representation of this
	 * expression by calling the asText() method 
	 * of this class. This method is overridden 
	 * for compatibility purposes. 
	 * 
	 * @return the string representation of this
	 *         expression.
	 */
	@Override
	public String toString() {
		return asText();
	}
	
}
