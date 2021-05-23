package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This class represents a smart script engine that executes
 * the smart script parsed by the SmartScriptParser from the
 * hr.fer.zemris.java.custom.scripting.parser package and
 * writes the result using the RequestContext object given. 
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class SmartScriptEngine {

	private DocumentNode documentNode;
	private RequestContext requestContext;
	private ObjectMultistack multistack;
	
	/**
	 * A Node visitor that visits all the document nodes of the smart
	 * script given and executes them. It writes the results using the
	 * RequestContext object given.
	 */
	private INodeVisitor visitor = new INodeVisitor() {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (IOException ex) {
				System.out.println("Error while writing to output stream.");
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String forVariable = node.getVariable().getName();
			multistack.push(forVariable, new ValueWrapper(node.getStartExpression().asText()));
			String forStepValue = node.getStepExpression() != null ? node.getStepExpression().asText() : "1";
			String forEndValue = node.getEndExpression().asText();
			while (multistack.peek(forVariable).numCompare(forEndValue) <= 0) {
				for (int i = 0; i < node.numberOfChildren(); i++) {
					node.getChild(i).accept(this);
				}
				multistack.peek(forVariable).add(forStepValue);
			}
			multistack.pop(forVariable);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitEchoNode(EchoNode node) {
			for (Element el : node.getElements()) {
				if (el instanceof ElementConstantDouble) {
					multistack.push("EchoNode", new ValueWrapper(((ElementConstantDouble)el).getValue()));
				} else if (el instanceof ElementConstantInteger) {
					multistack.push("EchoNode", new ValueWrapper(((ElementConstantInteger)el).getValue()));
				} else if (el instanceof ElementString) {
					multistack.push("EchoNode", new ValueWrapper(el.asText()));
				} else if (el instanceof ElementVariable) {
					multistack.push("EchoNode", new ValueWrapper(multistack.peek(el.asText()).getValue()));
				} else if (el instanceof ElementOperator) {
					doArithmeticOperation((ElementOperator)el);
				} else {
					calculateFunction((ElementFunction)el);
				}
			}
			if (!multistack.isEmpty("EchoNode")) {
				List<ValueWrapper> remainingValues = new ArrayList<>();
				while (!multistack.isEmpty("EchoNode")) {
					remainingValues.add(0, multistack.pop("EchoNode"));
				}
				for (ValueWrapper value : remainingValues) {
					try {
						requestContext.write(value.getValue().toString());
					} catch (IOException ex) {
						System.out.println("Error while writing to output stream.");
					}
				}
			}
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
		
		/**
		 * Computes the result of the operator given.
		 * 
		 * @param operator the operator to compute.
		 * @throws RuntimeException if the given operator
		 *                          is not supported.
		 */
		private void doArithmeticOperation(ElementOperator operator) {
			Object second = multistack.pop("EchoNode").getValue();
			if (operator.getSymbol().equals("+")) {
				multistack.peek("EchoNode").add(second);
			} else if (operator.getSymbol().equals("-")) {
				multistack.peek("EchoNode").subtract(second);
			} else if (operator.getSymbol().equals("*")) {
				multistack.peek("EchoNode").multiply(second);
			} else if (operator.getSymbol().equals("/")) {
				multistack.peek("EchoNode").divide(second);
			} else {
				throw new RuntimeException("The given operator in the smart script is not supported.");
			}
		}
		
		/**
		 * Calculates the function given.
		 * 
		 * @param function the function to calculate.
		 * @throws RuntimeException if the given function is
		 *                          not supported.
		 * @throws IllegalArgumentException if the given function arguments are
		 *                                  invalid.
		 */
		private void calculateFunction(ElementFunction function) {
			try {
				if (function.getName().equals("sin")) {
					Double result = Math.sin(Math.toRadians(Double.parseDouble(multistack.peek("EchoNode").getValue().toString())));
					multistack.peek("EchoNode").setValue(result);
				} else if (function.getName().equals("decfmt")) {
					DecimalFormat format = new DecimalFormat(multistack.pop("EchoNode").getValue().toString());
					String formatedValue = format.format(Double.parseDouble(multistack.peek("EchoNode").getValue().toString()));
					multistack.peek("EchoNode").setValue(formatedValue);
				} else if (function.getName().equals("dup")) {
					multistack.push("EchoNode", new ValueWrapper(multistack.peek("EchoNode").getValue()));
				} else if (function.getName().equals("swap")) {
					ValueWrapper first = multistack.pop("EchoNode");
					ValueWrapper second = multistack.pop("EchoNode");
					multistack.push("EchoNode", first);
					multistack.push("EchoNode", second);
				} else if (function.getName().equals("setMimeType")) {
					requestContext.setMimeType(multistack.pop("EchoNode").getValue().toString());
				} else if (function.getName().equals("paramGet")) {
					ValueWrapper defValue = multistack.pop("EchoNode");
					ValueWrapper name = multistack.pop("EchoNode");
					String value = requestContext.getParameter(name.getValue().toString());
					multistack.push("EchoNode", new ValueWrapper(value != null ? value : defValue.getValue()));
				} else if (function.getName().equals("pparamGet")) {
					ValueWrapper defValue = multistack.pop("EchoNode");
					ValueWrapper name = multistack.pop("EchoNode");
					String value = requestContext.getPersistentParameter(name.getValue().toString());
					multistack.push("EchoNode", new ValueWrapper(value != null ? value : defValue.getValue()));
				} else if (function.getName().equals("pparamSet")) {
					ValueWrapper name = multistack.pop("EchoNode");
					ValueWrapper value = multistack.pop("EchoNode");
					requestContext.setPersistentParameter(name.getValue().toString(), value.getValue().toString());
				} else if (function.getName().equals("pparamDel")) {
					ValueWrapper name = multistack.pop("EchoNode");
					requestContext.removePersistentParameter(name.getValue().toString());
				} else if (function.getName().equals("tparamGet")) {
					ValueWrapper defValue = multistack.pop("EchoNode");
					ValueWrapper name = multistack.pop("EchoNode");
					String value = requestContext.getTemporaryParameter(name.getValue().toString());
					multistack.push("EchoNode", new ValueWrapper(value != null ? value : defValue.getValue()));
				} else if (function.getName().equals("tparamSet")) {
					ValueWrapper name = multistack.pop("EchoNode");
					ValueWrapper value = multistack.pop("EchoNode");
					requestContext.setTemporaryParameter(name.getValue().toString(), value.getValue().toString());
				} else if (function.getName().equals("tparamDel")) {
					ValueWrapper name = multistack.pop("EchoNode");
					requestContext.removeTemporaryParameter(name.getValue().toString());
				} else {
					throw new RuntimeException("The given function in the smart script is not supported.");
				}
			} catch (NumberFormatException ex) {
				throw new IllegalArgumentException("The given function argument in the smart script is invalid.");
			}
		}
		
	};
	
	/**
	 * Creates a new SmartScriptEngine that executes the smart script represented by
	 * the document node given and writes the result using the RequestContext object
	 * given.
	 *  
	 * @param documentNode the document node of the smart script to execute.
	 * @param requestContext the RequestContext object to use to write the
	 *                       results.
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		this.documentNode = documentNode;
		this.requestContext = requestContext;
		multistack = new ObjectMultistack();
	}
	
	/**
	 * Executes the smart script given to this
	 * SmartScriptEngine.
	 */
	public void execute() {
	    documentNode.accept(visitor);
	}
	
}
