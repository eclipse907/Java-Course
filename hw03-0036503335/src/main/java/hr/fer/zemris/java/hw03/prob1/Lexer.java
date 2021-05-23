package hr.fer.zemris.java.hw03.prob1;

/**
 * This class represents a lexer that does lexical analysis
 * on the given text and returns tokens by invoking the
 * next token method. This lexer supports different modes
 * of analysis.
 *  
 * @author Tin Reiter
 * @version 1.0
 */
public class Lexer {
	
	private char[] data;
	private Token token;
	private int currentIndex;
	private LexerState currentState;
 
	/**
	 * This constructor creates a new lexer that does
	 * lexical analysis on the given text. The lexer is
	 * initially set in basic mode.
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
		this.data = text.toCharArray();
		this.currentState = LexerState.BASIC;
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
		if (token != null && token.getType() == TokenType.EOF) {
			throw new LexerException("The entire text has been analysed.");
		}
		removeBlanks();
		if (currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null); 
			return token; 
		}
		if (currentState == LexerState.BASIC) {
			basicState();
		} else {
			extendedState();
		}
		return token;
	} 

	/**
	 * Returns the last token in the lexical
	 * analysis.
	 * 
	 * @return the last token in the lexical
	 *         analysis.
	 */
	public Token getToken() {
		return token;
	}
	
	/**
	 * Sets the mode in which the lexer will
	 * perform lexical analysis.
	 * 
	 * @param state the mode in which the lexer will
	 *              perform lexical analysis. Must
	 *              be one of the modes from the
	 *              LexerState enumeration.
	 * @throws NullPointerException if the given mode
	 *                              is null. 
	 */
	public void setState(LexerState state) {
		if (state == null) {
			throw new NullPointerException();
		}
		currentState = state;
	}
	
	/**
	 * This method returns the next token in lexical analysis when the lexer is in
	 * basic mode.
	 * 
	 * @throws LexerException if the given text is invalid.
	 */
	private void basicState() {
		if (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {
			StringBuilder wordToken = new StringBuilder();
			while (currentIndex < data.length) {
				if (Character.isLetter(data[currentIndex])) {
					wordToken.append(data[currentIndex]);
				} else if (data[currentIndex] == '\\') {
					if (currentIndex + 1 < data.length && (Character.isDigit(data[currentIndex + 1]) || data[currentIndex + 1] == '\\')) {
						currentIndex++;
						wordToken.append(data[currentIndex]);
					} else {
						throw new LexerException("The given text is invalid.");
					}
					
				} else {
					break;
				}
				currentIndex++;
			}
			token = new Token(TokenType.WORD, wordToken.toString());
			return; 
		}
		if (Character.isDigit(data[currentIndex])) {
			StringBuilder numberToken = new StringBuilder();
			while (currentIndex < data.length) {
				if (Character.isDigit(data[currentIndex])) {
					numberToken.append(data[currentIndex]);
				} else {
					break;
				}
				currentIndex++;
			}
			try {
				token = new Token(TokenType.NUMBER, Long.valueOf(numberToken.toString())); 
			} catch (NumberFormatException ex) {
				throw new LexerException("The given text is invalid.");
			}
			return;
		}
		token = new Token(TokenType.SYMBOL, data[currentIndex]);
		currentIndex++;
		return;
	}
	
	/**
	 * This method returns the next token in lexical analysis when the lexer is in
	 * extended mode.
	 * 
	 * @throws LexerException if the given text is invalid.
	 */
	private void extendedState() {
		if (data[currentIndex] == '#') {
			token = new Token(TokenType.SYMBOL, data[currentIndex]);
			currentIndex++;
			return;
		}
		StringBuilder wordToken = new StringBuilder();
		while (currentIndex < data.length) {
			if (
					data[currentIndex] == '\r' || 
					data[currentIndex] == '\n' ||
					data[currentIndex] == '\t' || 
					data[currentIndex] == ' '  ||
					data[currentIndex] == '#'
			) {
				break;
			}
			wordToken.append(data[currentIndex]);
			currentIndex++;
		}
		token = new Token(TokenType.WORD, wordToken.toString());
	}
	
	/**
	 * Advances the currentIndex forward past the blanks until
	 * a symbol is reached.
	 */
	private void removeBlanks() {
		while (
				currentIndex < data.length &&
				(data[currentIndex] == '\r' || 
				data[currentIndex] == '\n' ||
				data[currentIndex] == '\t' || 
				data[currentIndex] == ' ')) {
			currentIndex++;
		}
	}

}
