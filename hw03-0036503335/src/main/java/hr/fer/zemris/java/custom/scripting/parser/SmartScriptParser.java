package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.Lexer;
import hr.fer.zemris.java.custom.scripting.lexer.LexerException;
import hr.fer.zemris.java.custom.scripting.lexer.LexerMode;
import hr.fer.zemris.java.custom.scripting.lexer.Token;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

/**
 * This class represents a parser that uses a lexer from the
 * hr.fer.zemris.java.custom.scripting.lexer package to parse
 * the given text.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class SmartScriptParser {
	
	private Lexer lexer;
	private ObjectStack stack;
	private DocumentNode document;
	
	/**
	 * This constructor creates a new parser and
	 * parses the given text.
	 * 
	 * @param text the text to parse.
	 * @throws SmartScriptParserException if the given text is
	 *                                    invalid.
	 * @throws NullPointerException if the given text is null.
	 */
	public SmartScriptParser(String text) {
		if (text == null) {
			throw new NullPointerException();
		}
		this.lexer = new Lexer(text);
		this.stack = new ObjectStack();
		this.document = new DocumentNode();
		parseDocument();
	}
	
	/**
	 * Returns a DocumentNode representation of the
	 * parsed text.
	 * 
	 * @return a DocumentNode representation of the
	 *         parsed text.
	 */
	public DocumentNode getDocumentNode() {
		return document;
	}
	
	/**
	 * Parses the given text.
	 * 
	 * @throws SmartScriptParserException if the given text is
	 *                                    invalid. 
	 */
	private void parseDocument() {
		stack.push(document);
		try {
			Token nextToken = lexer.nextToken();
			while (lexer.getLastToken().getType() != TokenType.EOF) {
				if (nextToken.getType() == TokenType.TEXT) {
					Node lastNode = (Node)stack.peek();
					lastNode.addChildNode(new TextNode((String)nextToken.getValue()));
				}
				else if (nextToken.getType() == TokenType.TAG_START) {
					lexer.setMode(LexerMode.TAG_NAME);
					nextToken = lexer.nextToken();
					lexer.setMode(LexerMode.TAG_BODY);
					String name = (String)nextToken.getValue();
					if (name.equalsIgnoreCase("FOR")) {
						Node lastNode = (Node)stack.peek();
						Node newNode = createForNode();
						lastNode.addChildNode(newNode);
						stack.push(newNode);
					} else if (name.equals("=")){
						Node lastNode = (Node)stack.peek();
						lastNode.addChildNode(createEchoNode());
					} else if (name.equalsIgnoreCase("END")) {
						if (stack.isEmpty()) {
							throw new SmartScriptParserException("The given text is invalid.");
						}
						stack.pop();
					} else {
						throw new SmartScriptParserException("The given text is invalid.");
					}
					lexer.setMode(LexerMode.TEXT);
				}
				nextToken = lexer.nextToken();
			}
			if (stack.isEmpty()) {
				throw new SmartScriptParserException("The given text is invalid.");
			}
		} catch (LexerException ex) {
			throw new SmartScriptParserException(ex.getMessage());
		}
	}
	
	/**
	 * Creates a new for loop node from a for tag.
	 * 
	 * @return a new for loop node.
	 */
	private Node createForNode() {
		Token nextToken = lexer.nextToken();
		if (nextToken.getType() != TokenType.VARIABLE) {
			throw new SmartScriptParserException("The given text is invalid.");
		}
		ElementVariable variable = new ElementVariable((String)nextToken.getValue());
		Element[] params = new Element[3];
		int i;
		for (i = 0; i < 3; i++) {
			nextToken = lexer.nextToken();
			if (nextToken.getType() == TokenType.TAG_END) {
				break;
			}
			else if (nextToken.getType() == TokenType.VARIABLE) {
				params[i] = new ElementVariable((String)nextToken.getValue());
			}
			else if (nextToken.getType() == TokenType.INTEGER) {
				params[i] = new ElementConstantInteger((int)nextToken.getValue());
			}
			else if (nextToken.getType() == TokenType.DOUBLE) {
				params[i] = new ElementConstantDouble((double)nextToken.getValue());
			}
			else if (nextToken.getType() == TokenType.STRING) {
				params[i] = new ElementString((String)nextToken.getValue());
			} else {
				throw new SmartScriptParserException("The given text is invalid.");
			}
		}
		if ((i == 3 && lexer.nextToken().getType() == TokenType.TAG_END) || (i == 2 && lexer.getLastToken().getType() == TokenType.TAG_END )) {
			Element startExpression = params[0];
			Element endExpression = params[1];
			Element stepExpression = params[2];
			return new ForLoopNode(variable, startExpression, endExpression, stepExpression);
		} else {
			throw new SmartScriptParserException("The given text is invalid.");
		}
	}
	
	/**
	 * Creates a new echo node from an echo tag.
	 * 
	 * @return a new echo node.
	 */
	private Node createEchoNode() {
		ArrayIndexedCollection elements = new ArrayIndexedCollection();
		for (Token nextToken = lexer.nextToken(); nextToken.getType()!= TokenType.TAG_END; nextToken = lexer.nextToken()) {
			if (nextToken.getType() == TokenType.STRING) {
				elements.add(new ElementString((String)nextToken.getValue()));
			}
			if (nextToken.getType() == TokenType.INTEGER) {
				elements.add(new ElementConstantInteger((int)nextToken.getValue()));
			}
			if (nextToken.getType() == TokenType.DOUBLE) {
				elements.add(new ElementConstantDouble((double)nextToken.getValue()));
			}
			if (nextToken.getType() == TokenType.VARIABLE) {
				elements.add(new ElementVariable((String)nextToken.getValue()));
			}
			if (nextToken.getType() == TokenType.OPERATOR) {
				elements.add(new ElementOperator((String)nextToken.getValue()));
			}
			if (nextToken.getType() == TokenType.FUNCTION) {
				elements.add(new ElementFunction((String)nextToken.getValue()));
			}
		}
		Object[] objects = elements.toArray();
		Element[] elems = new Element[elements.size()];
		for (int i = 0; i < elems.length; i++) {
			elems[i] = (Element) objects[i];
		}
		return new EchoNode(elems);
	}
	
}
