package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class DefaultSingleDocumentModel implements SingleDocumentModel {
	
	private JTextArea textArea;
	private Path filePath;
	private boolean isModified;
	private Set<SingleDocumentListener> observers;
	
	public DefaultSingleDocumentModel(Path filePath, String textContent) {
		textArea = new JTextArea(textContent);
		textArea.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				setModified(true);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				setModified(true);
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				setModified(true);
			}
		});
		this.filePath = filePath;
		observers = new HashSet<>(); 
	}

	@Override
	public JTextArea getTextComponent() {
		return textArea;
	}

	@Override
	public Path getFilePath() {
		return filePath;
	}

	@Override
	public void setFilePath(Path path) {
		if (path == null) {
			throw new NullPointerException("The given file path is null.");
		}
		filePath = path;
		notifyObserversPathChanged();
	}

	@Override
	public boolean isModified() {
		return isModified;
	}

	@Override
	public void setModified(boolean modified) {
		isModified = modified;
		notifyObserversTextChanged();
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		observers.add(l);
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		observers.remove(l);
	}
	
	private void notifyObserversPathChanged() {
		for (SingleDocumentListener l : observers) {
			l.documentFilePathUpdated(this);
		}
	}
	
	private void notifyObserversTextChanged() {
		for (SingleDocumentListener l : observers) {
			l.documentModifyStatusUpdated(this);
		}
	}

}
