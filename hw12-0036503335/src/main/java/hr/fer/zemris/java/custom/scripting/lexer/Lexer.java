package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * This class represents a lexer that does lexical analysis
 * on the given text and returns tokens by invoking the
 * next token method. This lexer supports different modes
 * of analysis and is used by the parser from 
 * hr.fer.zemris.java.custom.scripting.parser package.
 *  
 * @author Tin Reiter
 * @version 1.0
 */
public class Lexer {
	
	private char[] text;
	private Token lastToken;
	private int nextIndex;
	private LexerMode mode;
	
	/**
	 * This constructor creates a new lexer that does
	 * lexical analysis on the given text. The lexer is
	 * initially set in text mode.
	 * 
	 * @param text the text on which to perform lexical
	 *             analysis.
	 * @throws NullPointerException if the given text is
	 *                              null. 
	 */
	public Lexer(String text) {
		if (text == null) {
			throw new NullPointerException();
		}
		this.text = text.toCharArray();
		this.mode = LexerMode.TEXT;
	}
	
	/**
	 * Returns the next token in the lexical analysis.
	 * 
	 * @return the next token in the lexical analysis.
	 * @throws LexerException if the given text is invalid
	 *                        or the lexer has analyzed the
	 *                        entire given text.
	 */
	public Token nextToken() {
		if (lastToken != null && lastToken.getType() == TokenType.EOF) {
			throw new LexerException("End of file reached.");
		}
		if (nextIndex >= text.length) {
			lastToken = new Token(TokenType.EOF, null);
			return lastToken;
		}		
		if (mode == LexerMode.TEXT) {
			textMode();
			return lastToken;
		} else if (mode == LexerMode.TAG_NAME) {
			tagNameMode();
			return lastToken;
		} else {
			tagBodyMode();
			return lastToken;
		}
	}
	
	/**
	 * Returns the last token in the lexical
	 * analysis.
	 * 
	 * @return the last token in the lexical
	 *         analysis.
	 */
	public Token getLastToken() {
		return lastToken;
	}
	
	/**
	 * Sets the mode in which the lexer will
	 * perform lexical analysis.
	 * 
	 * @param mode the mode in which the lexer will
	 *             perform lexical analysis. Must
	 *             be one of the modes from the
	 *             LexerMode enumeration.
	 * @throws NullPointerException if the given mode
	 *                              is null. 
	 */
	public void setMode(LexerMode mode) {
		if (mode == null) {
			throw new NullPointerException();
		}
		this.mode = mode;
	}
	
	/**
	 * This method returns the next token in lexical analysis when the lexer is in
	 * text mode.
	 * 
	 * @throws LexerException if the given text is invalid.
	 */
	private void textMode() {
		if (text[nextIndex] == '{' && nextIndex + 1 < text.length && text[nextIndex + 1] == '$') {
			lastToken = new Token(TokenType.TAG_START, "{$");
			nextIndex += 2;
			return;
		}
		if (text[nextIndex] == '$' && nextIndex + 1 < text.length && text[nextIndex + 1] == '}') {
			lastToken = new Token(TokenType.TAG_END, "$}");
			nextIndex += 2;
			return;
		}
		StringBuilder textToken = new StringBuilder();
		while (nextIndex < text.length) {
			if (text[nextIndex] == '{' && nextIndex + 1 < text.length && text[nextIndex + 1] == '$') {
				break;
			}
			if (text[nextIndex] == '\\') {
				if (nextIndex + 1 < text.length && (text[nextIndex + 1] == '\\' || text[nextIndex + 1] == '{')) {
					nextIndex++;
				} else {
					throw new LexerException("Text is invalid.");
				}
			}
			textToken.append(text[nextIndex]);
			nextIndex++;
		}
		lastToken = new Token(TokenType.TEXT, textToken.toString());
	}
	
	/**
	 * This method returns the next token in lexical analysis when the lexer is in
	 * tag_name mode.
	 * 
	 * @throws LexerException if the given text is invalid.
	 */
	private void tagNameMode() {
		removeBlanks();
		if (Character.isLetter(text[nextIndex])) {
			StringBuilder nameToken = new StringBuilder();
			nameToken.append(text[nextIndex]);
			nextIndex++;
			while (nextIndex < text.length) {
				if (
						text[nextIndex] == '\r'     || 
				        text[nextIndex] == '\n'     ||
				        text[nextIndex] == '\t'     || 
				        text[nextIndex] == ' '      ||
				        (text[nextIndex] == '$'     &&
				        nextIndex + 1 < text.length &&
				        text[nextIndex + 1] == '}')) {
					break;
				}
				if (Character.isLetter(text[nextIndex])) {
					nameToken.append(text[nextIndex]);
				} else if (Character.isDigit(text[nextIndex])) {
					nameToken.append(text[nextIndex]);
				} else if (text[nextIndex] == '_') {
					nameToken.append(text[nextIndex]);
				} else {
					throw new LexerException("The given text is invalid");
				}
				nextIndex++;
			}
			lastToken = new Token(TokenType.TAG_NAME, nameToken.toString());
			return;
		}
		if (text[nextIndex] == '=') {
			lastToken = new Token(TokenType.TAG_NAME, "=");
			nextIndex++;
			return;
		}
		throw new LexerException("The given text is invalid.");
	}
	
	/**
	 * This method returns the next token in lexical analysis when the lexer is in
	 * tag_body mode.
	 * 
	 * @throws LexerException if the given text is invalid.
	 */
	private void tagBodyMode() {
		removeBlanks();
		if (Character.isLetter(text[nextIndex])) {
			StringBuilder nameToken = new StringBuilder();
			nameToken.append(text[nextIndex]);
			nextIndex++;
			while (nextIndex < text.length) {
				if (
						text[nextIndex] == '\r'     || 
				        text[nextIndex] == '\n'     ||
				        text[nextIndex] == '\t'     || 
				        text[nextIndex] == ' '      ||
				        (text[nextIndex] == '$'     &&
				        nextIndex + 1 < text.length &&
				        text[nextIndex + 1] == '}')) {
					break;
				}
				if (Character.isLetter(text[nextIndex])) {
					nameToken.append(text[nextIndex]);
				} else if (Character.isDigit(text[nextIndex])) {
					nameToken.append(text[nextIndex]);
				} else if (text[nextIndex] == '_') {
					nameToken.append(text[nextIndex]);
				} else {
					throw new LexerException("The given text is invalid");
				}
				nextIndex++;
			}
			lastToken = new Token(TokenType.VARIABLE, nameToken.toString());
			return;
		}
		if (
				Character.isDigit(text[nextIndex]) || 
				(text[nextIndex] == '-'            && 
				nextIndex + 1 < text.length        &&
				Character.isDigit(text[nextIndex + 1]))) {
			StringBuilder numberToken = new StringBuilder();
			boolean isDecimal = false;
			numberToken.append(text[nextIndex]);
			nextIndex++;
			while (nextIndex < text.length) {
				if (Character.isDigit(text[nextIndex])) {
					numberToken.append(text[nextIndex]);
				} else if (text[nextIndex] == '.') {
					if ( nextIndex + 1 < text.length && Character.isDigit(text[nextIndex + 1])) {
						numberToken.append(text[nextIndex]);
						isDecimal = true;
					}
				} else {
					break;
				}
				nextIndex++;
			}
			if (isDecimal) {
				lastToken = new Token(TokenType.DOUBLE, Double.valueOf(numberToken.toString()));
				return;
			} else {
				lastToken = new Token(TokenType.INTEGER, Integer.valueOf(numberToken.toString()));
				return;
			}
		}
		if (text[nextIndex] == '@' && nextIndex + 1 < text.length && Character.isLetter(text[nextIndex + 1])) {
			StringBuilder functionToken = new StringBuilder();
			nextIndex++;
			while (nextIndex < text.length) {
				if (
						text[nextIndex] == '\r' || 
				        text[nextIndex] == '\n' ||
				        text[nextIndex] == '\t' || 
				        text[nextIndex] == ' ') {
					break;
				}
				if (Character.isLetter(text[nextIndex])) {
					functionToken.append(text[nextIndex]);
				} else if (Character.isDigit(text[nextIndex])) {
					functionToken.append(text[nextIndex]);
				} else if (text[nextIndex] == '_') {
					functionToken.append(text[nextIndex]);
				} else {
					throw new LexerException("The given text is invalid");
				}
				nextIndex++;
			}
			lastToken = new Token(TokenType.FUNCTION, functionToken.toString());
			return;
		}
		if (
				text[nextIndex] == '+' ||
				text[nextIndex] == '-' ||
				text[nextIndex] == '*' ||
				text[nextIndex] == '/' ||
				text[nextIndex] == '^') {
			lastToken = new Token(TokenType.OPERATOR, Character.toString(text[nextIndex]));
			nextIndex++;
			return;
		}
		if (text[nextIndex] == '"') {
			StringBuilder stringToken = new StringBuilder();
			nextIndex++;
			while (nextIndex < text.length) {
				if (text[nextIndex] == '"') {
					nextIndex++;
					break;
				} else if (text[nextIndex] == '\\') {
					if (nextIndex + 1 < text.length  &&
						(text[nextIndex + 1] == '\\' ||
						text[nextIndex + 1] == '"')
					   ) {
						nextIndex++;
						stringToken.append(text[nextIndex]);
					} else if (nextIndex + 1 < text.length && text[nextIndex + 1] == 'n') {
						nextIndex++;
						stringToken.append('\n');
					} else if (nextIndex + 1 < text.length && text[nextIndex + 1] == 'r') {
						nextIndex++;
						stringToken.append('\r');
					} else if (nextIndex + 1 < text.length && text[nextIndex + 1] == 't') {
						nextIndex++;
						stringToken.append('\t');
					} else {
						throw new LexerException("The given text is invalid");
					}
				} else {
					stringToken.append(text[nextIndex]);
				}
				nextIndex++;
			}
			lastToken = new Token(TokenType.STRING, stringToken.toString());
			return;
		}
		if (text[nextIndex] == '{' && nextIndex + 1 < text.length && text[nextIndex + 1] == '$') {
			lastToken = new Token(TokenType.TAG_START, "{$");
			nextIndex += 2;
			return;
		}
		if (text[nextIndex] == '$' && nextIndex + 1 < text.length && text[nextIndex + 1] == '}') {
			lastToken = new Token(TokenType.TAG_END, "$}");
			nextIndex += 2;
			return;
		}
		throw new LexerException("The given text is invalid");
	}
	
	/**
	 * Advances the nextIndex forward past the blanks until
	 * a symbol is reached.
	 */
	private void removeBlanks() {
		while (
				nextIndex < text.length  &&
				(text[nextIndex] == '\r' || 
				text[nextIndex] == '\n'  ||
				text[nextIndex] == '\t'  || 
				text[nextIndex] == ' ')) {
			nextIndex++;
		}
	}
}
