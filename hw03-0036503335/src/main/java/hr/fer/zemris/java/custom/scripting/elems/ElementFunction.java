package hr.fer.zemris.java.custom.scripting.elems;

/**
 * This class represents a function expression found
 * in documents parsed by a parser. This class inherits
 * the Element class.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class ElementFunction extends Element {

	private String name;
	
	/**
	 * This constructor creates a new function
	 * expression with the given function name.
	 * 
	 * @param name the name of the function.
	 */
	public ElementFunction(String name) {
		this.name = name;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String asText() {
		return name;
	}

	/**
	 * Returns the name of the function.
	 * 
	 * @return the name of the function.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "@" + asText();
	}
	
	/**
	 * Checks if the given object is equal to this function
	 * expression.
	 * 
	 * @return true if the given object is equal, false
	 *         otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ElementFunction)) {
			return false;
		}
		ElementFunction otherFunction = (ElementFunction)obj;
		return name.equals(otherFunction.getName());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
}
