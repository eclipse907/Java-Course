package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * This enumeration lists the types of tokens
 * that lexical analysis can create.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public enum TokenType {
	
	TEXT,
	TAG_NAME,
	TAG_START,
	TAG_END,
	STRING,
	INTEGER,
	DOUBLE,
	OPERATOR,
	FUNCTION,
	VARIABLE,
	EOF;
	
}
