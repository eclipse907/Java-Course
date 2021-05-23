package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * This class represents a command which generates some kind of
 * a textual output dynamically. This class inherits the Node
 * class.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class EchoNode extends Node {
	
	private Element[] elements;
	
	/**
	 * This constructor creates a new echo node
	 * with the given expressions.
	 * 
	 * @param elements the expressions of the
	 *                 echo node.
	 */
	public EchoNode(Element[] elements) {
		this.elements = elements;
	}

	/**
	 * Returns an array which contains the expressions
	 * of the echo node.
	 * 
	 * @return the expressions of the echo node.
	 */
	public Element[] getElements() {
		return elements;
	}

	/**
	 * Returns a string representation of the echo
	 * node.
	 * 
	 * @return a string representation of the echo
	 *         node.
	 */
	@Override
	public String toString() {
		StringBuilder toString = new StringBuilder();
		toString.append("{$= ");
		for (int i = 0; i < elements.length; i++) {
			toString.append(elements[i].toString() + " ");
		}
		toString.append("$}");
		return toString.toString();
	}
	
	/**
	 * Checks if the given object is equal to this echo node.
	 * 
	 * @return true if the given object is equal, false
	 *         otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof EchoNode)) {
			return false;
		}
		EchoNode otherEcho = (EchoNode)obj;
		if (elements.length != otherEcho.getElements().length) {
			return false;
		}
		for (int i = 0; i < elements.length; i++) {
			if (!(elements[i].equals(otherEcho.getElements()[i]))) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		int hashCode = 0;
		for (int i = 0; i < elements.length; i++) {
			hashCode += elements[i].hashCode();
		}
		return hashCode;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitEchoNode(this);
	}
	
}
