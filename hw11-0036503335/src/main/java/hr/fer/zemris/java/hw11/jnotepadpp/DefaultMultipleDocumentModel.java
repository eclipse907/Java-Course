package hr.fer.zemris.java.hw11.jnotepadpp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	private static final long serialVersionUID = -2515137507092721994L;
	private List<SingleDocumentModel> documents;
	private SingleDocumentModel currentDocument;
	private Set<MultipleDocumentListener> observers;
	private ImageIcon unmodifiedIcon;
	private ImageIcon modifiedIcon;
	
	public DefaultMultipleDocumentModel() {
		documents = new ArrayList<>();
		observers = new HashSet<>();
		unmodifiedIcon = loadIcon("icons/unmodified-icon.png");
		modifiedIcon = loadIcon("icons/modified-icon.png");
		addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				SingleDocumentModel oldDocument = currentDocument;
				currentDocument = documents.size() > 0 ? documents.get(getSelectedIndex()) : null;
				notifyCurrentDocumentChanged(oldDocument);
			}
			
		});
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return documents.iterator();
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel document = new DefaultSingleDocumentModel(null, null);
		documents.add(document);
		addTab("(unnamed)", unmodifiedIcon, new JScrollPane(document.getTextComponent()), "(unnamed)");
		document.addSingleDocumentListener(new SingleDocumentListener() {
			
			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				int i = documents.indexOf(model);
				if (model.isModified()) {
					setIconAt(i, modifiedIcon);
				} else {
					setIconAt(i, unmodifiedIcon);
				}
			}
			
			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				Path path = model.getFilePath();
				int i = documents.indexOf(model); 
				setTitleAt(i, path != null ? path.getFileName().toString() : "(unnamed)");
				setToolTipTextAt(i, path != null ? path.toString() : "(unnamed)");
			}
			
		});
		setSelectedIndex(documents.indexOf(document));
		notifyDocumentAdded(document);
		return document;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentDocument;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) throws IOException {
		if (path == null) {
			throw new NullPointerException("Given path is null.");
		}
		for (SingleDocumentModel document : documents) {
			if (document.getFilePath() != null && document.getFilePath().equals(path)) {
				setSelectedIndex(documents.indexOf(document));
				return document;
			}
		}
		StringBuilder text = new StringBuilder();
		try (BufferedReader br = Files.newBufferedReader(path)) {
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				text.append(line + "\n");
			}
		}
		SingleDocumentModel document = new DefaultSingleDocumentModel(path, text.toString().replaceAll("[\n]$", ""));
		documents.add(document);
		addTab(path.getFileName().toString(), unmodifiedIcon, new JScrollPane(document.getTextComponent()), path.toString());
		document.addSingleDocumentListener(new SingleDocumentListener() {
			
			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				int i = documents.indexOf(model);
				if (model.isModified()) {
					setIconAt(i, modifiedIcon);
				} else {
					setIconAt(i, unmodifiedIcon);
				}
			}
			
			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				Path path = model.getFilePath();
				int i = documents.indexOf(model); 
				setTitleAt(i, path != null ? path.getFileName().toString() : "(unnamed)");
				setToolTipTextAt(i, path != null ? path.toString() : "(unnamed)");
			}
			
		});
		setSelectedIndex(documents.indexOf(document));
		notifyDocumentAdded(document);
		return document;		
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) throws IOException {
		if (newPath != null) {
			for (SingleDocumentModel document : documents) {
				if (document.getFilePath() != null && document.getFilePath().equals(newPath)) {
					throw new IllegalArgumentException("Path already associated with opened document.");
				}
			}
		} else {
			newPath = model.getFilePath();
		}
		try (BufferedWriter bw = Files.newBufferedWriter(newPath)) {
			bw.write(model.getTextComponent().getText());
		}
		if (!newPath.equals(model.getFilePath())) {
			model.setFilePath(newPath);
		}
		model.setModified(false);
		if (model.equals(currentDocument)) {
			notifyCurrentDocumentChanged(null);
		}
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		int index = documents.indexOf(model); 
		documents.remove(index);
		remove(index);
		if (model.equals(currentDocument)) {
			setSelectedIndex(documents.size() > 0 ? 0 : -1);
		}
		notifyDocumentRemoved(model);
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		observers.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		observers.remove(l);
	}
	
	private void notifyCurrentDocumentChanged(SingleDocumentModel previousModel) {
		for (MultipleDocumentListener l : observers) {
			l.currentDocumentChanged(previousModel, currentDocument);
		}
	}
	
	private void notifyDocumentAdded(SingleDocumentModel newDocument) {
		for (MultipleDocumentListener l : observers) {
			l.documentAdded(newDocument);
		}
	}
	
	private void notifyDocumentRemoved(SingleDocumentModel removedDocument) {
		for (MultipleDocumentListener l : observers) {
			l.documentRemoved(removedDocument);
		}
	}

	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return documents.get(index);
	}
	
	private ImageIcon loadIcon(String path) {
		byte[] bytes = null;
		try (InputStream is = this.getClass().getResourceAsStream(path)) {
			if (is == null) {
				throw new NullPointerException();
			}
			bytes = is.readAllBytes();
		} catch (IOException | NullPointerException ex) {
			System.out.println("Error while loading icons.");
			System.exit(1);
		}		
		return new ImageIcon(bytes);
	}

}
