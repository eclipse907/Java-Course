package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * This class is a template for a representation of a
 * structured document and uses an ArrayIndexedCollection
 * from the hr.fer.zemris.java.custom.collections package
 * to store its direct children.
 *  
 * @author Tin Reiter
 * @version 1.0
 */
public class Node {

	private ArrayIndexedCollection childrenCollection;
	
	/**
	 * Adds the given child node to an internally managed 
	 * collection of children. The collection is created
	 * when the method is called for the first time.
	 * 
	 * @param child the child node to add.
	 */
	public void addChildNode(Node child) {
		if (childrenCollection == null) {
			childrenCollection = new ArrayIndexedCollection();
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
		return (Node)childrenCollection.get(index);
	}
	
}
