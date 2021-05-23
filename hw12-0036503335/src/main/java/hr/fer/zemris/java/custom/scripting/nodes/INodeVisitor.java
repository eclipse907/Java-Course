package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * This interface represents a visitor that visits
 * Nodes.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public interface INodeVisitor {

	/**
	 * Method invoked when a visitor visits a text
	 * node.
	 * 
	 * @param node the text node to visit.
	 */
	public void visitTextNode(TextNode node);
	
	/**
	 * Method invoked when a visitor visits a for
	 * loop node.
	 * 
	 * @param node the for loop node to visit.
	 */
	public void visitForLoopNode(ForLoopNode node);
	
	/**
	 * Method invoked when a visitor visits an echo
	 * node.
	 * 
	 * @param node the echo node to visit.
	 */
	public void visitEchoNode(EchoNode node);
	
	/**
	 * Method invoked when a visitor visits a document
	 * node.
	 * 
	 * @param node the document node to visit.
	 */
	public void visitDocumentNode(DocumentNode node);
	
}
