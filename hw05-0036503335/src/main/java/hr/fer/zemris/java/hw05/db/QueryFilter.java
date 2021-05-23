package hr.fer.zemris.java.hw05.db;

import java.util.List;

/**
 * This class represents a conditional expression evaluator that
 * checks if the given record satisfies the query expressions.
 * This class implements the IFilter interface.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class QueryFilter implements IFilter {
	
	private List<ConditionalExpression> expressions;
	
	/**
	 * Creates a new query filter with the given expressions.
	 * 
	 * @param expressions the expressions to check.
	 */
	public QueryFilter(List<ConditionalExpression> expressions) {
		this.expressions = expressions;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean accepts(StudentRecord record) {
		boolean isAcceptable = true;
		for (ConditionalExpression expression : expressions) {
			if (!expression.getOperator().satisfied(expression.getValueGetter().get(record), expression.getLiteral())) {
				isAcceptable = false;
				return isAcceptable;
			}
		}
		return isAcceptable;
	}

}
