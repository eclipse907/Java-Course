package hr.fer.zemris.java.hw05.db;

/**
 * This class contains comparison classes that represent comparisons that
 * can be written in database queries.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class ComparisonOperators {
	
	public static final IComparisonOperator LESS = (value1, value2) -> value1.compareTo(value2) < 0 ? true : false;
	public static final IComparisonOperator LESS_OR_EQUALS = (value1, value2) -> value1.compareTo(value2) <= 0 ? true : false;
	public static final IComparisonOperator GREATER = (value1, value2) -> value1.compareTo(value2) > 0 ? true : false;
	public static final IComparisonOperator GREATER_OR_EQUALS = (value1, value2) -> value1.compareTo(value2) >= 0 ? true : false;
	public static final IComparisonOperator EQUALS = (value1, value2) -> value1.compareTo(value2) == 0 ? true : false;
	public static final IComparisonOperator NOT_EQUALS = (value1, value2) -> value1.compareTo(value2) != 0 ? true : false;
	public static final IComparisonOperator LIKE = (value1, value2) -> {
		
		int wildcardIndex = value2.indexOf('*');
		if (wildcardIndex == -1) {
			return value1.equals(value2);
		} else {
			int afterWildcardStringLength = value2.length() - 1 - wildcardIndex;
			return value1.substring(0, wildcardIndex)
				   .equals(value2.substring(0, wildcardIndex)) &&
				   wildcardIndex <= value1.length() - afterWildcardStringLength &&
				   value1.substring(value1.length() - afterWildcardStringLength, value1.length())
				   .equals(value2.substring(wildcardIndex + 1, value2.length()));
		}
		
	};
	
}
