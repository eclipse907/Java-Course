package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a template for a representation of a
 * structured document and uses a List to store its direct 
 * children.
 *  
 * @author Tin Reiter
 * @version 1.0
 */
public abstract class Node {

	private List<Node> childrenCollection;
	
	/**
	 * Adds the given child node to an internally managed 
	 * collection of children. The collection is created
	 * when the method is called for the first time.
	 * 
	 * @param child the child node to add.
	 */
	public void addChildNode(Node child) {
		if (childrenCollection == null) {
			childrenCollection = new ArrayList<>();
		}
		childrenCollection.add(child);
	}
	
	/**
	 * Returns the number of direct children of this node.
	 * 
	 * @return the number of direct children.
	 */
	public int numberOfChildren() {
		return childrenCollection.size();
	}
	
	/**
	 * Returns the selected child node.
	 * 
	 * @param index the index of the child node.
	 * @return the selected child node.
	 * @throws IndexOutOfBoundsException if the index is not
	 *                                   between 0 and number
	 *                                   of direct children - 1.
	 */
	public Node getChild(int index) {
		return childrenCollection.get(index);
	}
	
	/**
	 * The method invoked when a Node visitor wants to visit
	 * a node.
	 * 
	 * @param visitor the visitor of the Node.
	 */
	public abstract void accept(INodeVisitor visitor);
	
}
