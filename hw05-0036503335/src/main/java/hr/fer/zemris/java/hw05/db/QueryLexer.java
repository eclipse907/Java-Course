package hr.fer.zemris.java.hw05.db;

/**
 * This class represents a lexer that does lexical analysis
 * on the given database query.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class QueryLexer {

	private int currentIndex;
	private char[] query;
	private Token lastToken;
	
	/**
	 * Creates a new lexer that does lexical
	 * analysis on the given query.
	 * 
	 * @param query the query to analyze.
	 * @throws NullPointerException if the given query
	 *                              is null.
	 * @throws LexerException if the given query is wrong
	 *                        or if the end of the query is
	 *                        reached.
	 */
	public QueryLexer(String query) {
		if (query == null) {
			throw new NullPointerException();
		}
		this.query = query.toCharArray();
	}
	
	/**
	 * Returns the next token in the lexical analysis.
	 * 
	 * @return the next token in the lexical analysis.
	 * @throws LexerException if the given query is wrong
	 *                        or if the end of the query is
	 *                        reached.
	 */
	public Token nextToken() {
		if (lastToken != null && lastToken.getType() == TokenType.EOF) {
			throw new LexerException("End of query reached.");
		}
		removeBlanks();
		if (currentIndex >= query.length) {
			lastToken = new Token(TokenType.EOF, null);
			return lastToken;
		}
		StringBuilder token = new StringBuilder();
		if (Character.isLetter(query[currentIndex])) {
			while(currentIndex < query.length && Character.isLetter(query[currentIndex])) {
				token.append(query[currentIndex]);
				currentIndex++;
			}
			String value = token.toString();
			if (value.equalsIgnoreCase("and")) {
				lastToken = new Token(TokenType.LOGICAL_OPERATOR, value);
			} else if (value.equals("LIKE")) {
				lastToken = new Token(TokenType.OPERATOR, value);
			} else {
				lastToken = new Token(TokenType.ATRIBUT, value);
			}
			return lastToken;
		} else if (query[currentIndex] == '"') {
			currentIndex++;
			while (currentIndex < query.length && query[currentIndex] != '"') {
				token.append(query[currentIndex]);
				currentIndex++;
			}
			if (currentIndex >= query.length || query[currentIndex] != '"') {
				throw new LexerException("String literal not closed.");
			}
			currentIndex++;
			lastToken = new Token(TokenType.STRING_LITERAL, token.toString());
			return lastToken;
		} else if (query[currentIndex] == '<') {
			token.append(query[currentIndex]);
			currentIndex++;
			if (currentIndex < query.length && query[currentIndex] == '=') {
				token.append(query[currentIndex]);
				currentIndex++;
			}
			lastToken = new Token(TokenType.OPERATOR, token.toString());
			return lastToken;
		} else if (query[currentIndex] == '>') {
			token.append(query[currentIndex]);
			currentIndex++;
			if (currentIndex < query.length && query[currentIndex] == '=') {
				token.append(query[currentIndex]);
				currentIndex++;
			}
			lastToken = new Token(TokenType.OPERATOR, token.toString());
			return lastToken;
		} else if (query[currentIndex] == '=') {
			token.append(query[currentIndex]);
			currentIndex++;
			lastToken = new Token(TokenType.OPERATOR, token.toString());
			return lastToken;
		} else if (query[currentIndex] == '!' && currentIndex + 1 < query.length && query[currentIndex + 1] == '=') {
			token.append(query[currentIndex]);
			currentIndex++;
			token.append(query[currentIndex]);
			currentIndex++;
			lastToken = new Token(TokenType.OPERATOR, token.toString());
			return lastToken;
		} else {
			throw new LexerException("Wrong query entered.");
		}
	}
	
	/**
	 * Returns the last token produced in the lexical
	 * analysis.
	 * 
	 * @return the last token produced in the lexical
	 *         analysis.
	 */
	public Token getLastToken() {
		return lastToken;
	}
	
	/**
	 * Advances the currentIndex forward past the blanks until
	 * a symbol is reached.
	 */
	private void removeBlanks() {
		while (
				currentIndex < query.length &&
				(query[currentIndex] == '\r' || 
				 query[currentIndex] == '\n' ||
				 query[currentIndex] == '\t' || 
				 query[currentIndex] == ' ')) {
			currentIndex++;
		}
	}
	
}
