package hr.fer.zemris.lsystems.impl;

import java.awt.Color;
import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;
import hr.fer.zemris.math.Vector2D;

/**
 * This class represents a Lindenmayer system builder that
 * builds a Lindenmayer system for the given values. It can
 * build a system from either the given text or the values
 * inputed trough its methods. It implements the LSystemBuilder
 * interface.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class LSystemBuilderImpl implements LSystemBuilder {
	
	private Dictionary<Character, Command> registeredCommands;
	private Dictionary<Character, String> registeredProductions;
	private double unitLength;
	private double unitLengthDegreeScaler;
	private Vector2D origin;
	private double angle;
	private String axiom;
	
	/**
	 * Creates a new Lindenmayer system builder.
	 */
	public LSystemBuilderImpl() {
		this.registeredCommands = new Dictionary<>();
		this.registeredProductions = new Dictionary<>();
		this.unitLength = 0.1;
		this.unitLengthDegreeScaler = 1;
		this.origin = new Vector2D(0, 0);
		this.angle = 0;
		this.axiom = ""; 
	}

	/**
	 * Builds a Lindenmayer system with the values
	 * inputed.
	 * 
	 * @return a Lindenmayer system with the given
	 *         values.
	 */
	@Override
	public LSystem build() {
		return new LindenmayerSystem();
	}

	/**
	 * Loads the values for the Lindenmayer system from the given
	 * lines.
	 * 
	 * @param arg0 the lines from which to load.
	 * @return a reference to this Lindenmayer system builder.
	 * @throws NullPointerException if the given lines are null.
	 * @throws IllegalArgumentException if the given lines are
	 *                                  wrong.
	 */
	@Override
	public LSystemBuilder configureFromText(String[] arg0) {
		if (arg0 == null) {
			throw new NullPointerException();
		}
		for (String line : arg0) {
			if (line.equals("")) {
				continue;
			}
			String[] splitedLine = line.trim().split("\\s+", 2);
			if (splitedLine[0].equalsIgnoreCase("origin")) {
				String[] originArgs = splitedLine[1].split("\\s+");
				if (originArgs.length == 2) {
					setOrigin(Double.parseDouble(originArgs[0]), Double.parseDouble(originArgs[1]));
				} else {
					throw new IllegalArgumentException("Wrong origin entered.");
				}	
			} else if (splitedLine[0].equalsIgnoreCase("angle")) {
				try {
					setAngle(Double.parseDouble(splitedLine[1]));
				} catch (NumberFormatException ex) {
					throw new IllegalArgumentException("Wrong angle entered.");
				}
			} else if (splitedLine[0].equalsIgnoreCase("unitLength")) {
				try {
					setUnitLength(Double.parseDouble(splitedLine[1]));
				} catch (NumberFormatException ex) {
					throw new IllegalArgumentException("Wrong unit length entered.");
				}
			} else if (splitedLine[0].equalsIgnoreCase("unitLengthDegreeScaler")) {
				String[] unitLengthScale = splitedLine[1].split("\\s*/\\s*");
				if (unitLengthScale.length == 1) {
					setUnitLengthDegreeScaler(Double.parseDouble(unitLengthScale[0]));
				} else if (unitLengthScale.length == 2) {
					setUnitLengthDegreeScaler(Double.parseDouble(unitLengthScale[0]) / Double.parseDouble(unitLengthScale[1]));
				} else {
					throw new IllegalArgumentException("Wrong unit length degree scaler entered.");
				}
			} else if (splitedLine[0].equalsIgnoreCase("command")) {
				String[] commandArgs = splitedLine[1].split("\\s+", 2);
				if (commandArgs.length == 2 && commandArgs[0].length() == 1) {
					registerCommand(commandArgs[0].charAt(0), commandArgs[1]);
				} else {
					throw new IllegalArgumentException("Wrong command entered.");
				}
			} else if (splitedLine[0].equalsIgnoreCase("axiom")) {
				if (!splitedLine[1].matches("\\s+")) {
					setAxiom(splitedLine[1]);
				} else {
					throw new IllegalArgumentException("Wrong axiom entered.");
				}
			} else if (splitedLine[0].equalsIgnoreCase("production")) {
				String[] productionArgs = splitedLine[1].split("\\s+", 2);
				if (productionArgs.length == 2 && productionArgs[0].length() == 1) {
					registerProduction(productionArgs[0].charAt(0), productionArgs[1]);
				} else {
					throw new IllegalArgumentException("Wrong production entered.");
				}
			} else {
				throw new IllegalArgumentException("Wrong directive given.");
			}
		}
		return this;
	}

	/**
	 * Associates the given action with the given symbol. The given symbol must
	 * not be already associated.
	 * 
	 * @param arg0 the symbol.
	 * @param arg1 the action.
	 * @return a reference to this Lindenmayer system builder.
	 * @throws NullPointerException if the given action is null.
	 * @throws IllegalArgumentException if the given symbol is associated
	 *                                  or the action is wrong. 
	 */
	@Override
	public LSystemBuilder registerCommand(char arg0, String arg1) {
		if (arg1 == null) {
			throw new NullPointerException();
		}
		if (registeredCommands.get(arg0) != null) {
			throw new IllegalArgumentException("Symbol already exists.");
		}
		String[] input = arg1.trim().split("\\s+");
		if (input.length == 1) {
			if (input[0].equalsIgnoreCase("push")) {
				registeredCommands.put(arg0, new PushCommand());
			} else if (input[0].equalsIgnoreCase("pop")) {
				registeredCommands.put(arg0, new PopCommand());
			} else {
				throw new IllegalArgumentException("Wrong command entered.");
			}
		} else if (input.length == 2) {
			if (input[0].equalsIgnoreCase("draw")) {
				registeredCommands.put(arg0, new DrawCommand(Double.parseDouble(input[1])));
			} else if (input[0].equalsIgnoreCase("skip")) {
				registeredCommands.put(arg0, new SkipCommand(Double.parseDouble(input[1])));
			} else if (input[0].equalsIgnoreCase("scale")) {
				registeredCommands.put(arg0, new ScaleCommand(Double.parseDouble(input[1])));
			} else if (input[0].equalsIgnoreCase("rotate")) {
				registeredCommands.put(arg0, new RotateCommand(Double.parseDouble(input[1])));
			} else if (input[0].equalsIgnoreCase("color")) {
				int red = Integer.parseInt(input[1].substring(0, 2), 16);
				int green = Integer.parseInt(input[1].substring(2, 4), 16);
				int blue = Integer.parseInt(input[1].substring(4, 6), 16);
				registeredCommands.put(arg0, new ColorCommand(new Color(red, green, blue)));
			} else {
				throw new IllegalArgumentException("Wrong command entered.");
			}
		} else {
			throw new IllegalArgumentException("Wrong command entered.");
		}
		return this;
	}

	/**
	 * Associates the given symbol with the given production. The symbol must
	 * not be already associated.
	 * 
	 * @param arg0 the symbol.
	 * @param arg1 the production.
	 * @return a reference to this Lindenmayer system builder.
	 * @throws NullPointerException if the given production is
	 *                              null.
	 * @throws IllegalArgumentException if the given symbol is already
	 *                                  associated or the production
	 *                                  is wrong.
	 */
	@Override
	public LSystemBuilder registerProduction(char arg0, String arg1) {
		if (arg1 == null) {
			throw new NullPointerException();
		}
		if (registeredProductions.get(arg0) != null) {
			throw new IllegalArgumentException("Symbol already exists.");
		}
		if (!arg1.matches("\\s+")) {
			registeredProductions.put(arg0, arg1);
		} else {
			throw new IllegalArgumentException("Wrong production entered.");
		}
		return this;
	}

	/**
	 * Sets the angle of the starting Lindenmayer system state.
	 * 
	 * @param arg0 the starting angle.
	 * @return a reference to this Lindenmayer system builder.
	 */
	@Override
	public LSystemBuilder setAngle(double arg0) {
		angle = arg0;
		return this;
	}

	/**
	 * Sets the axiom of the Lindenmayer system.
	 * 
	 * @param arg0 the axiom.
	 * @return a reference to this Lindenmayer system builder.
	 * @throws NullPointerException if the given axiom is null.
	 */
	@Override
	public LSystemBuilder setAxiom(String arg0) {
		if (arg0 == null) {
			throw new NullPointerException();
		}
		axiom = arg0.trim();
		return this;
	}

	/**
	 * Sets the starting point of the Lindenmayer system.
	 * 
	 * @param arg0 the x coordinate of the starting point.
	 * @param arg1 the y coordinate of the starting point.
	 * @return a reference to this Lindenmayer system builder.
	 * @throws IllegalArgumentException if the given point is out
	 *                                  of range of the screen.
	 */
	@Override
	public LSystemBuilder setOrigin(double arg0, double arg1) {
		if (arg0 < 0.0 || arg1 < 0.0 || arg0 > 1.0 || arg1 > 1.0) {
			throw new IllegalArgumentException("Wrong origin entered.");
		}
		origin = new Vector2D(arg0, arg1);
		return this;
	}

	/**
	 * Sets the unit length of the Lindenmayer system.
	 * 
	 * @param arg0 the unit length.
	 * @return a reference to this Lindenmayer system builder.
	 */
	@Override
	public LSystemBuilder setUnitLength(double arg0) {
		unitLength = arg0;
		return this;
	}

	/**
	 * Sets the unit length scaler of the Lindenmayer system.
	 * 
	 * @param arg0 the unit length scaler.
	 * @return a reference to this Lindenmayer system builder.
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double arg0) {
		unitLengthDegreeScaler = arg0;
		return this;
	}

	/**
	 * This class represents a Lindenmayer system. It implements the
	 * LSystem interface.
	 * 
	 * @author Tin Reiter
	 * @version 1.0
	 */
	private class LindenmayerSystem implements LSystem {

		/**
		 * Draws the given Lindenmayer system level on the screen 
		 * with the given Painter.
		 * 
		 * @param arg0 the Lindenmayer system level to draw.
		 * @param arg1 an object that can draw lines on a window.
		 */
		@Override
		public void draw(int arg0, Painter arg1) {
			Context context = new Context();
			context.pushState(new TurtleState(origin, new Vector2D(1.0, 0).rotated(angle), Color.BLACK, unitLength * Math.pow(unitLengthDegreeScaler, arg0)));
			for (char symbol : generate(arg0).toCharArray()) {
				Command action = registeredCommands.get(symbol);
				if (action != null) {
					action.execute(context, arg1);
				}
			}
		}

		/**
		 * Generates the grammar of the given Lindenmayer system level.
		 * 
		 * @param arg0 the Lindenmayer system level.
		 * @return the grammar of the given Lindenmayer system level.
		 */
		@Override
		public String generate(int arg0) {
			if (arg0 == 0) {
				return axiom;
			}
			StringBuilder nextSequence = new StringBuilder();
			String currentSequence = axiom;
			String production;
			for (int i = 0; i < arg0; i++) {
				for (char symbol: currentSequence.toCharArray()) {
					production = registeredProductions.get(symbol);
					if (production == null) {
						nextSequence.append(symbol);
					} else {
						nextSequence.append(production);
					}
				}
				currentSequence = nextSequence.toString();
				nextSequence = new StringBuilder();
			}
			return currentSequence;
		}
		
	}
	
}
