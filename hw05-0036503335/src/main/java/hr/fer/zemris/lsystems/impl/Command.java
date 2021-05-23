package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * This interface represents an action in the drawing of
 * Lindenmayer systems.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
@FunctionalInterface
public interface Command {

	/**
	 * Executes this action for the given context.
	 * 
	 * @param ctx the current context.
	 * @param painter an object that can draw lines
	 *                on a window.
	 */
	void execute(Context ctx, Painter painter);
	
}
