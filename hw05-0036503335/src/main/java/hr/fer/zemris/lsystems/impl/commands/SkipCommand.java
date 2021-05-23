package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * This class represents an action that moves the current
 * state to the position reached with the given step. This
 * class implements the Command interface.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class SkipCommand implements Command {
	
	private double step;
	
	/**
	 * Creates a new move action that moves the current
     * state to the position reached with the given step.
     * 
	 * @param step the value of the step.
	 */
	public SkipCommand(double step) {
		this.step = step;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();
		double moveLength = step * currentState.getCurrentEffectiveStepLength();
		currentState.getCurrentPosition().translate(new Vector2D(moveLength, moveLength));
	}
	
}
