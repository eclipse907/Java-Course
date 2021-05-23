package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * This class represents an action that rotates the
 * current state. This class implements the Command
 * interface.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class RotateCommand implements Command{
	
	private double angle;

	/**
	 * Creates a new rotate action that rotates
	 * for the given value.
	 * 
	 * @param angle the rotation value.
	 */
	public RotateCommand(double angle) {
		this.angle = angle;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().getCurrentOrientation().rotate(angle);
	}

}
