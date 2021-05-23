package hr.fer.zemris.java.custom.scripting.elems;

/**
 * This class represents a variable expression found
 * in documents parsed by a parser. This class inherits
 * the Element class.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class ElementVariable extends Element{
	
	private String name;
	
	/**
	 * This constructor creates a new variable
	 * expression with the given variable name.
	 * 
	 * @param name the name of the variable.
	 */
	public ElementVariable(String name) {
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
	 * Returns the name of the variable.
	 * 
	 * @return the name of the variable.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Checks if the given object is equal to this variable
	 * expression.
	 * 
	 * @return true if the given object is equal, false
	 *         otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ElementVariable)) {
			return false;
		}
		ElementVariable otherVariable = (ElementVariable)obj;
		return name.equals(otherVariable.getName());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
}
