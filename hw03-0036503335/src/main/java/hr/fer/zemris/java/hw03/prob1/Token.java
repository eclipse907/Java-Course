package hr.fer.zemris.java.hw03.prob1;

/**
 * This class represents a token in lexical analysis.
 * The token can be one of the types written in the
 * TokenType enumeration.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class Token {
	
	private TokenType type;
	private Object value;
	
	/**
	 * This constructor creates a new Token with the
	 * given type and value.
	 * 
	 * @param type type of token. It must be one of the
	 *             types written in the TokenType enumeration.    
	 * @param value the value of the token.
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Returns the value of the token.
	 * 
	 * @return the value of the token.
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * Returns the token type. It is one of the 
	 * types written in the TokenType enumeration.
	 *  
	 * @return the token type.
	 */
	public TokenType getType() {
		return type;
	}
	
}
