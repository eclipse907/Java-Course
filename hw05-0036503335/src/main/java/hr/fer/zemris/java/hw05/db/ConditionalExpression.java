package hr.fer.zemris.java.hw05.db;

/**
 * This class represents a conditional expression found in
 * database queries.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class ConditionalExpression {
	
	private IFieldValueGetter valueGetter;
	private String literal;
	private IComparisonOperator operator;
	
	/**
	 * Creates a new conditional expression with the given operator and
	 * arguments.
	 * 
	 * @param valueGetter the attribute getter of the query.
	 * @param literal the string constant of the query.
	 * @param operator the comparison operator of the query.
	 */
	public ConditionalExpression(IFieldValueGetter valueGetter, String literal, IComparisonOperator operator) {
		this.valueGetter = valueGetter;
		this.literal = literal;
		this.operator = operator;
	}

	/**
	 * Returns the attribute getter of the query.
	 * 
	 * @return the attribute getter of the query.
	 */
	public IFieldValueGetter getValueGetter() {
		return valueGetter;
	}

	/**
	 * Returns the string constant of the query.
	 * 
	 * @return the string constant of the query.
	 */
	public String getLiteral() {
		return literal;
	}

	/**
	 * Returns the comparison operator of the query.
	 * 
	 * @return the comparison operator of the query.
	 */
	public IComparisonOperator getOperator() {
		return operator;
	}

}
