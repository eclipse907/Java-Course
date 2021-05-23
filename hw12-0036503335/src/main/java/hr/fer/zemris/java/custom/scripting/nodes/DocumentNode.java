package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * This class represents a document which contains different
 * parts (nodes). This class inherits the Node class.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class DocumentNode extends Node {

	/**
	 * This method returns the entire document in
	 * text form.
	 * 
	 * @return the entire document in text form.
	 */
	@Override
	public String toString() {
		StringBuilder document = new StringBuilder();
		for (int i = 0; i < numberOfChildren(); i++) {
			document.append(getChild(i).toString());
		}
		return document.toString();
	}
	
	/**
	 * Checks if the given object is equal to this document.
	 * 
	 * @return true if the given object is equal, false
	 *         otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof DocumentNode)) {
			return false;
		}
		DocumentNode otherDocument = (DocumentNode)obj;
		if (this.numberOfChildren() != otherDocument.numberOfChildren()) {
			return false;
		}
		for (int i = 0; i < numberOfChildren(); i++) {
			if (!this.getChild(i).equals(otherDocument.getChild(i))) {
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
		for (int i = 0; i < numberOfChildren(); i++) {
			hashCode += getChild(i).hashCode();
		}
		return hashCode;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitDocumentNode(this);
	}
	
}
