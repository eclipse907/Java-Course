package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * This class represents an action that draws a line
 * from the current position to the position reached with
 * the given step. This class implements the Command interface. 
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class DrawCommand implements Command {
	
	private double step;

	/**
	 * Creates a new draw action that draws a line
	 * to the position reached with the given step.
	 * 
	 * @param step the value of the step.
	 */
	public DrawCommand(double step) {
		this.step = step;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();
		Vector2D newOrientation = currentState.getCurrentOrientation().scaled(step * currentState.getCurrentEffectiveStepLength());
		Vector2D newPosition = currentState.getCurrentPosition().translated(newOrientation);
		painter.drawLine(currentState.getCurrentPosition().getX(),
				         currentState.getCurrentPosition().getY(),
				         newPosition.getX(),
				         newPosition.getY(),
				         currentState.getCurrentColor(), 
				         1.0f
				         );
		currentState.setCurrentPosition(newPosition);
	}

}
