package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a parser that parses the given
 * database query using a lexer.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class QueryParser {
	
	private QueryLexer lexer;
	private List<ConditionalExpression> conditionalExpressions;
	
	/**
	 * Creates a new parser that parses the given query.
	 * 
	 * @param query the query to parse.
	 * @throws NullPointerException if the given query is
	 *                              null.
	 * @throws IllegalArgumentException if the given query is
	 *                                  wrong.
	 */
	public QueryParser(String query) {
		if (query == null) {
			throw new NullPointerException();
		}
		this.lexer = new QueryLexer(query);
		this.conditionalExpressions = new ArrayList<>();
		parseQuery();
	}
	
	/**
	 * This method parses the given query.
	 * 
	 * @throws IllegalArgumentException if the given query is
	 *                                  wrong.
	 */
	private void parseQuery() {
		try {
			Token nextToken = lexer.nextToken();
			Token lastToken = null;
			IFieldValueGetter lastValueGetter = null;
			String lastLiteral = null;
			IComparisonOperator lastOperator = null;
			while (nextToken.getType() != TokenType.EOF) {
				if (nextToken.getType() == TokenType.ATRIBUT) {
					if (lastToken != null && lastToken.getType() != TokenType.LOGICAL_OPERATOR) {
						throw new IllegalArgumentException("Wrong query given.");
					}
					if (nextToken.getValue().equals("jmbag")) {
						lastValueGetter = FieldValueGetters.JMBAG;
					} else if (nextToken.getValue().equals("lastName")) {
						lastValueGetter = FieldValueGetters.LAST_NAME;
					} else if (nextToken.getValue().equals("firstName")) {
						lastValueGetter = FieldValueGetters.FIRST_NAME;
					} else {
						throw new IllegalArgumentException("Wrong atribute given.");
					}
				} else if (nextToken.getType() == TokenType.OPERATOR) {
					if (lastToken == null || lastToken.getType() != TokenType.ATRIBUT) {
						throw new IllegalArgumentException("Wrong query given"); 
					}
					if (nextToken.getValue().equals("<")) {
						lastOperator = ComparisonOperators.LESS;
					} else if (nextToken.getValue().equals("<=")) {
						lastOperator = ComparisonOperators.LESS_OR_EQUALS;
					} else if (nextToken.getValue().equals(">")) {
						lastOperator = ComparisonOperators.GREATER;
					} else if (nextToken.getValue().equals(">=")) {
						lastOperator = ComparisonOperators.GREATER_OR_EQUALS;
					} else if (nextToken.getValue().equals("=")) {
						lastOperator = ComparisonOperators.EQUALS;
					} else if (nextToken.getValue().equals("!=")) {
						lastOperator = ComparisonOperators.NOT_EQUALS;
					} else if (nextToken.getValue().equals("LIKE")) {
						lastOperator = ComparisonOperators.LIKE;
					} else {
						throw new IllegalArgumentException("Wrong operator given.");
					}
				} else if (nextToken.getType() == TokenType.STRING_LITERAL) {
					if (lastToken == null || lastToken.getType() != TokenType.OPERATOR) {
						throw new IllegalArgumentException("Wrong query given."); 
					}
					if (lastToken.getType() == TokenType.OPERATOR && lastToken.getValue().equals("LIKE")) {
						long numOfWildcards = nextToken.getValue().chars().filter(symbol -> symbol == '*').count();
						if (numOfWildcards > 1) {
							throw new IllegalArgumentException("Wrong number of wildcards entered.");
						}
					}
					lastLiteral = nextToken.getValue();
				} else if (nextToken.getType() == TokenType.LOGICAL_OPERATOR) {
					if (lastToken == null || lastToken.getType() != TokenType.STRING_LITERAL) {
						throw new IllegalArgumentException("Wrong query given.");
					}
					conditionalExpressions.add(new ConditionalExpression(lastValueGetter, lastLiteral, lastOperator));
				} else {
					throw new IllegalArgumentException("Unknown token given.");
				}
				lastToken = nextToken;
				nextToken = lexer.nextToken();
			}
			conditionalExpressions.add(new ConditionalExpression(lastValueGetter, lastLiteral, lastOperator));
		} catch (LexerException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}
	}
	
	/**
	 * Checks if the given query is a direct
	 * query.
	 * 
	 * @return true if the given query is a direct
	 *         query, false otherwise.
	 */
	public boolean isDirectQuery() {
		if (conditionalExpressions.size() != 1) {
			return false;
		}
		ConditionalExpression expression = conditionalExpressions.get(0);
		return expression.getValueGetter().equals(FieldValueGetters.JMBAG) &&
			   expression.getOperator().equals(ComparisonOperators.EQUALS);
	}
	
	/**
	 * Returns the jmbag of the parsed query if the query given was a
	 * direct query, throws an exception otherwise.
	 * 
	 * @return the jmbag of the parsed query.
	 * @throws IllegalStateException if the given query is not
	 *                               a direct query.
	 */
	public String getQueriedJMBAG() {
		if (isDirectQuery()) {
			return conditionalExpressions.get(0).getLiteral();
		} else {
			throw new IllegalStateException("The query was not direct.");
		}
	}
	
	/**
	 * Returns the conditional expressions found in this parsed query.
	 * 
	 * @return a list containing the conditional expressions 
	 *         found in this parsed query. 
	 */
	public List<ConditionalExpression> getQuery() {
		return conditionalExpressions;
	}
	
}
