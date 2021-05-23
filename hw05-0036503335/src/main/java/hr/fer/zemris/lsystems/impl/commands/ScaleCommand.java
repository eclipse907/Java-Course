package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * This class represents an action that scales the
 * current effective step length. This class implements
 * the Command interface.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class ScaleCommand implements Command {
	
	private double factor;

	/**
	 * Creates a new scale action that scales for
	 * the given factor.
	 * 
	 * @param factor the scale factor.
	 */
	public ScaleCommand(double factor) {
		this.factor = factor;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();
		currentState.setCurrentEffectiveStepLength(factor * currentState.getCurrentEffectiveStepLength());
	}

}
