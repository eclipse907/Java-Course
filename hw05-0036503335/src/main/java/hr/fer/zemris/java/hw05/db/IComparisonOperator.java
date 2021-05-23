package hr.fer.zemris.java.hw05.db;

/**
 * This interface represents a comparison operator used
 * in database queries.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public interface IComparisonOperator {
	
	/**
	 * Checks if the given arguments satisfy the comparison
	 * operator.
	 * 
	 * @param value1 the first value.
	 * @param value2 the second value.
	 * @return true if the given arguments satisfy the comparison
	 *         operator, false otherwise.
	 */
	public boolean satisfied(String value1, String value2);

}
