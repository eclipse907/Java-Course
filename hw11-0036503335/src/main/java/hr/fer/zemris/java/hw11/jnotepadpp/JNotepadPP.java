package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAbstractAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableDefaultEditorKitAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableJMenu;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableJToolBar;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

public class JNotepadPP extends JFrame {

	private static final long serialVersionUID = 1L;
	private MultipleDocumentModel model;
	private JFileChooser chooser;
	private JLabel length;
	private DocumentListener lengthListener;
	private JLabel caret;
	private CaretListener caretListener;
	private ILocalizationProvider lp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);;
	
	public JNotepadPP() {
		setLocation(100, 100);
		setSize(800, 800);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setTitle("JNotepad++");
		initGUI();
	}
	
	private void initGUI() {
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				checkIfDocumentsNotSavedBeforeClosing();
			}
			
		});
		getContentPane().setLayout(new BorderLayout());
		JPanel centralPanel = new JPanel(new BorderLayout());
		getContentPane().add(centralPanel, BorderLayout.CENTER);
		DefaultMultipleDocumentModel m = new DefaultMultipleDocumentModel();
		model = m;
		centralPanel.add(m, BorderLayout.CENTER);
		model.addMultipleDocumentListener(new MultipleDocumentListener() {
			
			@Override
			public void documentRemoved(SingleDocumentModel model) {
				if (JNotepadPP.this.model.getCurrentDocument() == null) {
					saveDocument.setEnabled(false);
					saveAsDocument.setEnabled(false);
					closeDocument.setEnabled(false);
					cut.setEnabled(false);
					copy.setEnabled(false);
					paste.setEnabled(false);
					statistics.setEnabled(false);
				}
			}
			
			@Override
			public void documentAdded(SingleDocumentModel model) {
				if (!saveDocument.isEnabled()) {
					saveDocument.setEnabled(true);
					saveAsDocument.setEnabled(true);
					closeDocument.setEnabled(true);
					cut.setEnabled(true);
					copy.setEnabled(true);
					paste.setEnabled(true);
					statistics.setEnabled(true);
				}
			}
			
			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				if (previousModel != null) {
					previousModel.getTextComponent().getDocument().removeDocumentListener(lengthListener);
					previousModel.getTextComponent().removeCaretListener(caretListener);
				}
				if (currentModel != null) {
					setTitle((currentModel.getFilePath() != null ? currentModel.getFilePath().toString() : "(unnamed)") + " - JNotepad++");
					currentModel.getTextComponent().getDocument().addDocumentListener(lengthListener);
					currentModel.getTextComponent().addCaretListener(caretListener);
					lengthListener.changedUpdate(null);
					caretListener.caretUpdate(null);
				} else {
					setTitle("JNotepad++");
					length.setText("length: ");
					caret.setText("Ln:   Col:   Sel: ");
				}
			}
			
		});
		centralPanel.add(createStatusBar(), BorderLayout.PAGE_END);
		chooser = new JFileChooser();
		createActions();
		createMenus();
		createToolbars();
		model.createNewDocument();
	}
	
	private void createActions() {
		createDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		createDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		openDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		saveDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveAsDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift S"));
		saveAsDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_Y);
		copy.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copy.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		paste.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		paste.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
		cut.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		cut.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		statistics.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		statistics.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		closeDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control END"));
		closeDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_END);
		exit.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
		exit.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_Q);
	}
	
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new LocalizableJMenu("fileMenuName", lp);
		menuBar.add(fileMenu);
		fileMenu.add(new JMenuItem(createDocument));
		fileMenu.add(new JMenuItem(openDocument));
		fileMenu.add(new JMenuItem(saveDocument));
		fileMenu.add(new JMenuItem(saveAsDocument));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(closeDocument));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(exit));
		setJMenuBar(menuBar);
		JMenu editMenu = new LocalizableJMenu("editMenuName", lp);
		menuBar.add(editMenu);
		editMenu.add(new JMenuItem(cut));
		editMenu.add(new JMenuItem(copy));
		editMenu.add(new JMenuItem(paste));
		JMenu toolsMenu = new LocalizableJMenu("toolsMenuName", lp);
		menuBar.add(toolsMenu);
		JMenu changeCaseMenu = new LocalizableJMenu("changeCaseMenuName", lp);
		toolsMenu.add(changeCaseMenu);
		changeCaseMenu.add(new JMenuItem(upperCase));
		changeCaseMenu.add(new JMenuItem(lowerCase));
		changeCaseMenu.add(new JMenuItem(invertCase));
		JMenu sortMenu = new LocalizableJMenu("sortMenuName", lp);
		toolsMenu.add(sortMenu);
		sortMenu.add(new JMenuItem(sortLineAscending));
		sortMenu.add(new JMenuItem(sortLineDescending));
		toolsMenu.add(new JMenuItem(unique));
		JMenu viewMenu = new LocalizableJMenu("viewMenuName", lp);
		menuBar.add(viewMenu);
		viewMenu.add(new JMenuItem(statistics));
		JMenu languageMenu = new LocalizableJMenu("languageMenuName", lp);
		menuBar.add(languageMenu);
		languageMenu.add(new JMenuItem(changeLanguageEN));
		languageMenu.add(new JMenuItem(changeLanguageHR));
		languageMenu.add(new JMenuItem(changeLanguageDE));
	}
	
	private void createToolbars() {
		JToolBar toolBar = new LocalizableJToolBar("toolBarName", lp);
		toolBar.setFloatable(true);
		toolBar.add(new JButton(createDocument));
		toolBar.add(new JButton(openDocument));
		toolBar.add(new JButton(saveDocument));
		toolBar.add(new JButton(saveAsDocument));
		toolBar.add(new JButton(closeDocument));
		toolBar.add(new JButton(cut));
		toolBar.add(new JButton(copy));
		toolBar.add(new JButton(paste));
		toolBar.add(new JButton(statistics));
		toolBar.add(new JButton(exit));
		getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}
	
	private JPanel createStatusBar() {
		JPanel statusBar = new JPanel(new GridLayout(1, 3));
		statusBar.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		length = new JLabel();
		length.setAlignmentX(LEFT_ALIGNMENT);
		statusBar.add(length);
		caret = new JLabel();
		caret.setAlignmentX(LEFT_ALIGNMENT);
		statusBar.add(caret);
		DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		JLabel time = new JLabel(format.format(Calendar.getInstance().getTime()));
		time.setAlignmentX(RIGHT_ALIGNMENT);
		statusBar.add(time);
		new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				time.setText(format.format(Calendar.getInstance().getTime()));
			}
			
		}).start();
		lengthListener = new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				changeLength();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				changeLength();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				changeLength();
			}
			
			private void changeLength() {
				JTextArea current = model.getCurrentDocument().getTextComponent();
				length.setText(lp.getString("lengthDisplay") + ":" + current.getText().length());
			}
			
		};
		caretListener = new CaretListener() {
			
			@Override
			public void caretUpdate(CaretEvent e) {
				JTextArea current = model.getCurrentDocument().getTextComponent();
				int offset = current.getCaretPosition();
				try {
					int line = current.getLineOfOffset(offset) + 1;
					int column = offset - current.getLineStartOffset(line - 1) + 1;
					String selectedText = current.getSelectedText();
					checkIfSomethingIsSelected(selectedText);
					int selection = selectedText != null ? selectedText.length() : 0;
					caret.setText(lp.getString("lineDisplay") + ":" + Integer.toString(line) + "   " + lp.getString("columnDisplay") + ":"
					              + Integer.toString(column) + "   " + lp.getString("selectionDisplay") + ":" + Integer.toString(selection)
					             );
				} catch (BadLocationException ex) {
					JOptionPane.showOptionDialog(JNotepadPP.this, "Error while reading current line or column.", "Error",
							                     JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null
							                    );
				}
			}
			
			private void checkIfSomethingIsSelected(String selectedText) {
				if (selectedText == null) {
					upperCase.setEnabled(false);
					lowerCase.setEnabled(false);
					invertCase.setEnabled(false);
					sortLineAscending.setEnabled(false);
					sortLineDescending.setEnabled(false);
					unique.setEnabled(false);
				} else {
					upperCase.setEnabled(true);
					lowerCase.setEnabled(true);
					invertCase.setEnabled(true);
					sortLineAscending.setEnabled(true);
					sortLineDescending.setEnabled(true);
					unique.setEnabled(true);
				}
			}
			
		};
		lp.addLocalizationLisener(() -> {
			lengthListener.changedUpdate(null);
			caretListener.caretUpdate(null);
		});
		return statusBar;
	}
	
	private Action createDocument = new LocalizableAbstractAction("createName", "createDescription", lp) {

		private static final long serialVersionUID = -2438667143430812097L;

		@Override
		public void actionPerformed(ActionEvent e) {
			model.createNewDocument();
		}
		
	};
	
	private Action openDocument = new LocalizableAbstractAction("openName", "openDescription", lp) {
		
		private static final long serialVersionUID = -2796634880921047137L;

		@Override
		public void actionPerformed(ActionEvent e) {
			chooser.setDialogTitle("Open file");
			if (chooser.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			Path filePath = chooser.getSelectedFile().toPath();
			if (!Files.isReadable(filePath)) {
				JOptionPane.showOptionDialog(JNotepadPP.this, "The file is not readable.", "Error", JOptionPane.DEFAULT_OPTION,
						                     JOptionPane.ERROR_MESSAGE, null, null, null
						                    );
			}
			try {
				model.loadDocument(filePath);
			} catch (Exception ex) {
				JOptionPane.showOptionDialog(JNotepadPP.this, "Error while reading file.", "Error", JOptionPane.DEFAULT_OPTION,
	                     JOptionPane.ERROR_MESSAGE, null, null, null
	                    );
			}
		}
		
	};
	
	private Action saveDocument = new LocalizableAbstractAction("saveName", "saveDescription", lp) {
		
		private static final long serialVersionUID = -3330931000709851504L;

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				saveDocument(model.getCurrentDocument());
			} catch (IllegalArgumentException ex) {
				JOptionPane.showOptionDialog(JNotepadPP.this, ex.getMessage(), "Error", JOptionPane.DEFAULT_OPTION,
	                     JOptionPane.ERROR_MESSAGE, null, null, null
	                    );
				model.getCurrentDocument().setModified(true);
			} catch (Exception ex) {
				JOptionPane.showOptionDialog(JNotepadPP.this, "Error while saving file.", "Error", JOptionPane.DEFAULT_OPTION,
	                     JOptionPane.ERROR_MESSAGE, null, null, null
	                    );
				model.getCurrentDocument().setModified(true);
			}			
		}
		
	};
	
	private Action saveAsDocument = new LocalizableAbstractAction("saveAsName", "saveAsDescription", lp) {
		
		private static final long serialVersionUID = 6366520240770468350L;

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				chooser.setDialogTitle("Save file");
				if (chooser.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
					return;
				}
				Path filePath = chooser.getSelectedFile().toPath();
				int response = JOptionPane.YES_OPTION;
				if (Files.exists(filePath)) {
					response = JOptionPane.showOptionDialog(JNotepadPP.this, "File already exists. Would you like to overwrite?",
							                                filePath.getFileName().toString(), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
							                                null, null, null
		                                                   );
				}
				if (response == JOptionPane.YES_OPTION) {
					model.saveDocument(model.getCurrentDocument(), filePath);
				}
			} catch (IllegalArgumentException ex) {
				JOptionPane.showOptionDialog(JNotepadPP.this, ex.getMessage(), "Error", JOptionPane.DEFAULT_OPTION,
	                     JOptionPane.ERROR_MESSAGE, null, null, null
	                    );
				model.getCurrentDocument().setModified(true);
			} catch (Exception ex) {
				JOptionPane.showOptionDialog(JNotepadPP.this, "Error while saving file.", "Error", JOptionPane.DEFAULT_OPTION,
	                     JOptionPane.ERROR_MESSAGE, null, null, null
	                    );
				model.getCurrentDocument().setModified(true);
			}
		}
		
	};
	
	private Action exit = new LocalizableAbstractAction("exitName", "exitDescription", lp) {
		
		private static final long serialVersionUID = -7990560770170460604L;

		@Override
		public void actionPerformed(ActionEvent e) {
			checkIfDocumentsNotSavedBeforeClosing();
		}
		
	};
	
	private Action statistics = new LocalizableAbstractAction("statisticsName", "statisticsDescription", lp) {
		
		private static final long serialVersionUID = 70178771341626582L;

		@Override
		public void actionPerformed(ActionEvent e) {
			String text = model.getCurrentDocument().getTextComponent().getText();
			int numOfChars = text.length();
			int numOfNonBlankChars = text.replaceAll("\\s+", "").length();
			int numOfLines = model.getCurrentDocument().getTextComponent().getLineCount();
			JOptionPane.showOptionDialog(JNotepadPP.this, "Your document has " + Integer.toString(numOfChars) + " characters, "
					                     + Integer.toString(numOfNonBlankChars) + " non-blank characters and " + Integer.toString(numOfLines) + " lines.",
					                     "Statistics", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
		}
		
	};
	
	private Action closeDocument = new LocalizableAbstractAction("closeName", "closeDescription", lp) {
		
		private static final long serialVersionUID = 7628783364274402725L;

		@Override
		public void actionPerformed(ActionEvent e) {
			SingleDocumentModel document = model.getCurrentDocument();
			if (document.isModified()) {
				int response = JOptionPane.showOptionDialog(JNotepadPP.this, "File not saved. Would you like to save it?",
                                                            document.getFilePath() != null ? document.getFilePath().getFileName().toString() : "(unnamed)",
                                                            JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null
                                                           );
				if (response == JOptionPane.YES_OPTION) {
					try {
						saveDocument(document);
					} catch (IllegalArgumentException ex) {
						JOptionPane.showOptionDialog(JNotepadPP.this, ex.getMessage(), "Error", JOptionPane.DEFAULT_OPTION,
			                     JOptionPane.ERROR_MESSAGE, null, null, null
			                    );
						document.setModified(true);
						return;
					} catch (Exception ex) {
						JOptionPane.showOptionDialog(JNotepadPP.this, "Error while saving file.", "Error", JOptionPane.DEFAULT_OPTION,
			                     JOptionPane.ERROR_MESSAGE, null, null, null
			                    );
						document.setModified(true);
						return;
					}
					model.closeDocument(document);
				} else if (response == JOptionPane.NO_OPTION) {
					model.closeDocument(document);
				} else {
					return;
				}
			} else {
				model.closeDocument(document);
			}
		}
		
	};
	
	private Action upperCase = new LocalizableAbstractAction("upperCaseName", "upperCaseDescription", lp) {
		
		private static final long serialVersionUID = -903553134670025043L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea textArea = model.getCurrentDocument().getTextComponent();
			textArea.replaceSelection(textArea.getSelectedText().toUpperCase(new Locale(lp.getCurrentLanguage())));
		}
		
	};
	
    private Action lowerCase = new LocalizableAbstractAction("lowerCaseName", "lowerCaseDescription", lp) {
    	
		private static final long serialVersionUID = 5087210751601845379L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea textArea = model.getCurrentDocument().getTextComponent();
			textArea.replaceSelection(textArea.getSelectedText().toLowerCase(new Locale(lp.getCurrentLanguage())));
		}
		
	};
	
    private Action invertCase = new LocalizableAbstractAction("invertCaseName", "invertCaseDescription", lp) {
    	
		private static final long serialVersionUID = -6104904564181857252L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea textArea = model.getCurrentDocument().getTextComponent();
			String selectedText = textArea.getSelectedText();
			StringBuilder invertedText = new StringBuilder();
			for (int i = 0; i < selectedText.length(); i++) {
				char c = selectedText.charAt(i);
				invertedText.append(Character.isUpperCase(c) ? Character.toLowerCase(c) : Character.toUpperCase(c));
			}
			textArea.replaceSelection(invertedText.toString());
		}
		
	};
	
	private Action sortLineAscending = new LocalizableAbstractAction("sortLineAscendingName", "sortLineAscendingDescription", lp) {
		
		private static final long serialVersionUID = -8056717338317050194L;

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				JTextComponent current = model.getCurrentDocument().getTextComponent();
				Document doc = current.getDocument();
				Element root = doc.getDefaultRootElement();
				int startLine = root.getElementIndex(current.getSelectionStart());
				int endLine = root.getElementIndex(current.getSelectionEnd());
				List<String> lines = new ArrayList<>();
				for (int i = startLine; i <= endLine; i++) {
					int startOffset = root.getElement(i).getStartOffset();
					int endOffset = root.getElement(i).getEndOffset();
					lines.add(doc.getText(startOffset, endOffset - startOffset));
				}
				Collections.sort(lines, Collator.getInstance(new Locale(lp.getCurrentLanguage())));
				int startOffset = root.getElement(startLine).getStartOffset();
				int endOffset = root.getElement(endLine).getEndOffset();
				doc.remove(startOffset, endOffset - startOffset - 1);
				for (String line : lines) {
					doc.insertString(startOffset, line, null);
					startOffset += line.length();
				}
				doc.remove(current.getCaretPosition() - 1, 1);
			} catch (BadLocationException e1) {
				JOptionPane.showOptionDialog(JNotepadPP.this, "Error while sorting lines.", "Error",
	                                         JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null
	                                        );
			}
		}
		
	};
	
    private Action sortLineDescending = new LocalizableAbstractAction("sortLineDescendingName", "sortLineDescendingDescription", lp) {
    	
		private static final long serialVersionUID = 2652971335169220150L;

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				JTextComponent current = model.getCurrentDocument().getTextComponent();
				Document doc = current.getDocument();
				Element root = doc.getDefaultRootElement();
				int startLine = root.getElementIndex(current.getSelectionStart());
				int endLine = root.getElementIndex(current.getSelectionEnd());
				List<String> lines = new ArrayList<>();
				for (int i = startLine; i <= endLine; i++) {
					int startOffset = root.getElement(i).getStartOffset();
					int endOffset = root.getElement(i).getEndOffset();
					lines.add(doc.getText(startOffset, endOffset - startOffset));
				}				
				Collections.sort(lines, Collections.reverseOrder(Collator.getInstance(new Locale(lp.getCurrentLanguage()))));
				int startOffset = root.getElement(startLine).getStartOffset();
				int endOffset = root.getElement(endLine).getEndOffset();
				doc.remove(startOffset, endOffset - startOffset - 1);
				for (String line : lines) {
					doc.insertString(startOffset, line, null);
					startOffset += line.length();
				}
				doc.remove(current.getCaretPosition() - 1, 1);
			} catch (BadLocationException e1) {
				JOptionPane.showOptionDialog(JNotepadPP.this, "Error while sorting lines.", "Error",
	                                         JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null
	                                        );
			}
		}
		
	};
	
	private Action unique = new LocalizableAbstractAction("uniqueName", "uniqueDescription", lp) {
		
		private static final long serialVersionUID = 3707665776888618534L;

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				JTextComponent current = model.getCurrentDocument().getTextComponent();
				Document doc = current.getDocument();
				Element root = doc.getDefaultRootElement();
				int startLine = root.getElementIndex(current.getSelectionStart());
				int endLine = root.getElementIndex(current.getSelectionEnd());
				Set<String> lines = new LinkedHashSet<>();
				for (int i = startLine; i <= endLine; i++) {
					int startOffset = root.getElement(i).getStartOffset();
					int endOffset = root.getElement(i).getEndOffset();
					lines.add(doc.getText(startOffset, endOffset - startOffset));
				}				
				int startOffset = root.getElement(startLine).getStartOffset();
				int endOffset = root.getElement(endLine).getEndOffset();
				doc.remove(startOffset, endOffset - startOffset - 1);
				for (String line : lines) {
					doc.insertString(startOffset, line, null);
					startOffset += line.length();
				}
				doc.remove(current.getCaretPosition() - 1, 1);
			} catch (BadLocationException e1) {
				JOptionPane.showOptionDialog(JNotepadPP.this, "Error while removing duplicate lines.", "Error",
	                                         JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null
	                                        );
			}
		}
	};
	
	private Action changeLanguageEN = new LocalizableAbstractAction("changeLanguageENName", "changeLanguageENDescription", lp) {
		
		private static final long serialVersionUID = -903553134670025043L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("en");
		}
		
	};
	
    private Action changeLanguageHR = new LocalizableAbstractAction("changeLanguageHRName", "changeLanguageHRDescription", lp) {
    	
		private static final long serialVersionUID = -1143864837084132886L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("hr");
		}
		
	};
	
    private Action changeLanguageDE = new LocalizableAbstractAction("changeLanguageDEName", "changeLanguageDEDescription", lp) {
    	
		private static final long serialVersionUID = 1650172338466639185L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("de");
		}
		
	};
	
	private Action copy = new LocalizableDefaultEditorKitAction("copyName", "copyDescription", lp, new DefaultEditorKit.CopyAction());
	private Action paste = new LocalizableDefaultEditorKitAction("pasteName", "pasteDescription", lp, new DefaultEditorKit.PasteAction());
	private Action cut = new LocalizableDefaultEditorKitAction("cutName", "cutDescription", lp, new DefaultEditorKit.CutAction());
	
	
	private void checkIfDocumentsNotSavedBeforeClosing() {
		for (SingleDocumentModel document : model) {
			if (document.isModified()) {
				int response = JOptionPane.showOptionDialog(JNotepadPP.this, "File not saved. Would you like to save it?",
						                                    document.getFilePath() != null ? document.getFilePath().getFileName().toString() : "(unnamed)",
                                                            JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null
                                                           );
				if (response == JOptionPane.YES_OPTION) {
					try {
						saveDocument(document);
					} catch (IllegalArgumentException ex) {
						JOptionPane.showOptionDialog(JNotepadPP.this, ex.getMessage(), "Error", JOptionPane.DEFAULT_OPTION,
			                     JOptionPane.ERROR_MESSAGE, null, null, null
			                    );
						document.setModified(true);
						return;
					} catch (Exception ex) {
						JOptionPane.showOptionDialog(JNotepadPP.this, "Error while saving file.", "Error", JOptionPane.DEFAULT_OPTION,
			                     JOptionPane.ERROR_MESSAGE, null, null, null
			                    );
						document.setModified(true);
						return;
					}
				} else if (response == JOptionPane.CANCEL_OPTION) {
					return;
				}
			}
		}
		dispose();
	}
	
	private void saveDocument(SingleDocumentModel document) throws IOException {
		if (document.getFilePath() != null) {
			model.saveDocument(document, null);
		} else {
			chooser.setDialogTitle("Save file");
			if (chooser.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			Path filePath = chooser.getSelectedFile().toPath();
			int response = JOptionPane.YES_OPTION;
			if (Files.exists(filePath)) {
				response = JOptionPane.showOptionDialog(JNotepadPP.this, "File already exists. Would you like to overwrite?",
                                                        filePath.getFileName().toString(), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                                                        null, null, null
                                                       );
			}
			if (response == JOptionPane.YES_OPTION) {
				model.saveDocument(document, filePath);
			}
		}
	}
	
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JNotepadPP().setVisible(true);
		});
	}

}
