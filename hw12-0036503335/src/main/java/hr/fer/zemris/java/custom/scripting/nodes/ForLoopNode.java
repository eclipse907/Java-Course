package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * This class represents a single for-loop construct. This
 * class inherits the Node class.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class ForLoopNode extends Node {
	
	private ElementVariable variable;
	private Element startExpression;
	private Element endExpression;
	private Element stepExpression;
	
	/**
	 * This constructor creates a new for loop node with
	 * the given parameters.
	 * 
	 * @param variable the variable of the for loop.
	 * @param startExpression the starting value of
	 *                        the for loop. 
	 * @param endExpression the for loop stop value.
	 * @param stepExpression the value of the increment.
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression, Element stepExpression) {
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	/**
	 * Returns the variable of the for loop.
	 * 
	 * @return the variable of the for loop.
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * Returns the starting value of the for loop.
	 * 
	 * @return the starting value.
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * Returns the stop value of the for loop.
	 * 
	 * @return the stop value.
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * Returns the increment value of the for
	 * loop.
	 * 
	 * @return the increment value.
	 */
	public Element getStepExpression() {
		return stepExpression;
	}

	/**
	 * Returns the string representation of the for
	 * loop.
	 * 
	 * @return the string representation of the for
	 *         loop.
	 */
	@Override
	public String toString() {
		StringBuilder toString = new StringBuilder();
		toString.append("{$ FOR ");
		toString.append(variable + " ");
		toString.append(startExpression + " ");
		toString.append(endExpression);
		if (stepExpression != null) {
			toString.append(" " + stepExpression + " ");
		}
		toString.append("$}");
		for (int i = 0; i < numberOfChildren(); i++) {
			toString.append(getChild(i).toString());
		}
		toString.append("{$END$}");
		return toString.toString();
	}
	
	/**
	 * Checks if the given object is equal to this for loop.
	 * 
	 * @return true if the given object is equal, false
	 *         otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ForLoopNode)) {
			return false;
		}
		ForLoopNode otherFor = (ForLoopNode)obj;
		if (stepExpression != null) {
			if (
					variable.equals(otherFor.getVariable())               &&
					startExpression.equals(otherFor.getStartExpression()) &&
					endExpression.equals(otherFor.getEndExpression())     &&
					stepExpression.equals(otherFor.getStepExpression())
			   ) {
				return true;
			} else {
				return false;
			}
		} else {
			if (
					variable.equals(otherFor.getVariable())               &&
					startExpression.equals(otherFor.getStartExpression()) &&
					endExpression.equals(otherFor.getEndExpression())
			   ) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		if (stepExpression != null) {
			return variable.hashCode() + startExpression.hashCode() + endExpression.hashCode() + stepExpression.hashCode();
		} else {
			return variable.hashCode() + startExpression.hashCode() + endExpression.hashCode();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitForLoopNode(this);
	}
	
}
