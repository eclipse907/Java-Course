package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * This class represents a context in the drawing of a
 * Lindenmayer system. It uses a stack to store its
 * states.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class Context {

	private ObjectStack<TurtleState> turtleStates;
	
	/**
	 * Creates a new context.
	 */
	public Context() {
		this.turtleStates = new ObjectStack<>();
	}
	
	/**
	 * Returns the current state of the context.
	 * The state is not removed from the context.
	 * 
	 * @return the current state of the context.
	 */
	public TurtleState getCurrentState() {
		return turtleStates.peek();
	}
	
	/**
	 * Adds the given state to the top of the
	 * context.
	 * 
	 * @param state the state to add.
	 */
	public void pushState(TurtleState state) {
		turtleStates.push(state);
	}
	
	/**
	 * Removes the last state added from the
	 * context.
	 */
	public void popState() {
		turtleStates.pop();
	}
	
}
