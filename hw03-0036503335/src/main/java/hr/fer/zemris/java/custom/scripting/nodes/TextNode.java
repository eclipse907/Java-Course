package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * This class represents a piece of textual data. This
 * class inherits the Node class.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class TextNode extends Node {
	
	private String text;
	
	/**
	 * This constructor creates a new
	 * text node with the given text.
	 * 
	 * @param text the text of the text
	 *             node.
	 */
	public TextNode(String text) {
		this.text = text;
	}

	/**
	 * Returns the text of the text
	 * node.
	 * 
	 * @return the text of the text
	 *         node.
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * Returns a string representation of
	 * the text node.
	 * 
	 * @return a string representation of
	 *         the text node. 
	 */
	@Override
	public String toString() {
		return getText();
	}
	
	/**
	 * Checks if the given object is equal to this text.
	 * 
	 * @return true if the given object is equal, false
	 *         otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof TextNode)) {
			return false;
		}
		TextNode otherText = (TextNode)obj;
		return text.equals(otherText.getText());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return text.hashCode();
	}
	
}
