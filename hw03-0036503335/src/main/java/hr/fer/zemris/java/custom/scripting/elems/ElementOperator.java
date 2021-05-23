package hr.fer.zemris.java.custom.scripting.elems;

/**
 * This class represents an operator expression
 * found in documents parsed by a parser. This class
 * inherits the Element class.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class ElementOperator extends Element {

	private String symbol;
	
	/**
	 * This constructor creates a new operator
	 * expression from the given operator
	 * symbol.
	 * 
	 * @param symbol the operator symbol.
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String asText() {
		return symbol;
	}

	/**
	 * Returns the operator symbol.
	 * 
	 * @return the operator symbol.
	 */
	public String getSymbol() {
		return symbol;		
	}
	
	/**
	 * Checks if the given object is equal to this operator
	 * expression.
	 * 
	 * @return true if the given object is equal, false
	 *         otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ElementOperator)) {
			return false;
		}
		ElementOperator otherOperator = (ElementOperator)obj;
		return symbol.equals(otherOperator.getSymbol());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return symbol.hashCode();
	}
	
}
