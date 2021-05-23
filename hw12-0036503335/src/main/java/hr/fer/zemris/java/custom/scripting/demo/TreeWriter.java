package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * This class represents a program that reads a file given as the first
 * command line argument and parses it into a document tree and then displays
 * it's content on the standard output.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class TreeWriter {
	
	/**
	 * This class represents a Node visitor that visits all
	 * the nodes of a document and displays it's content on
	 * the standard output. It implements the INodeVisitor
	 * interface.
	 * 
	 * @author Tin Reiter
	 * @version 1.0
	 */
	private static class WriterVisitor implements INodeVisitor {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitTextNode(TextNode node) {
			System.out.printf(node.getText());
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitForLoopNode(ForLoopNode node) {
			StringBuilder toString = new StringBuilder();
			toString.append("{$ FOR ");
			toString.append(node.getVariable() + " ");
			toString.append(node.getStartExpression() + " ");
			toString.append(node.getEndExpression());
			if (node.getStepExpression() != null) {
				toString.append(" " + node.getStepExpression() + " ");
			}
			toString.append("$}");
			System.out.printf(toString.toString());
			for (int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}
			System.out.printf("{$END$}");
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitEchoNode(EchoNode node) {
			System.out.printf(node.toString());
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}
		}
		
	}

	/**
	 * The method from which the program starts.
	 * 
	 * @param args command line arguments.
	 * @throws IllegalArgumentException if wrong number of command line arguments
	 *                                  is given or the given file is not a smart
	 *                                  script.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			throw new IllegalArgumentException("Wrong number of command line arguments given.");
		}
		if (!args[0].endsWith(".smscr")) {
			throw new IllegalArgumentException("The given file must be a smart script.");
		}
		try {
			String docBody = new String(Files.readAllBytes(Paths.get(args[0])), StandardCharsets.UTF_8);
			SmartScriptParser p = new SmartScriptParser(docBody);
			WriterVisitor visitor = new WriterVisitor();
			p.getDocumentNode().accept(visitor);
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
			System.exit(1);
		}
	}
	
}
