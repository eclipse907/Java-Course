package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * This interface represents a MultipleDocument listener that
 * observers a MultipleDocument object.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public interface MultipleDocumentListener {

	/**
	 * The method called when the current document of MultipleDocument object
	 * is changed.
	 * 
	 * @param previousModel the previous current document.
	 * @param currentModel the current document.
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);
	void documentAdded(SingleDocumentModel model);
	void documentRemoved(SingleDocumentModel model);
	
}
