package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * This class represents an action that removes a state
 * from the current context. This class implements
 * the Command interface.
 * 
 * @author Tin Reiter
 * @author 1.0
 */
public class PopCommand implements Command{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.popState();
	}

}
