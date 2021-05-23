package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * This class represents an action that changes the
 * current color. This class implements the Command
 * interface.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class ColorCommand implements Command {
	
	private Color color;

	/**
	 * Creates a new color action with the given
	 * color value.
	 * 
	 * @param color the color value.
	 */
	public ColorCommand(Color color) {
		this.color = color;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setCurrentColor(color);
	}

}
