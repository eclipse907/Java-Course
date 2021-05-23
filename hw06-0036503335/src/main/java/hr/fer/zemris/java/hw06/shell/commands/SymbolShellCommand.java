package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This class represents a symbol command used in the shell from
 * the hr.fer.zemris.java.hw06.shell package. This class implements
 * the ShellCommand interface.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class SymbolShellCommand implements ShellCommand {

	private String name;
	private List<String> description;
	
	/**
	 * Creates a new symbol command.
	 */
	public SymbolShellCommand() {
		this.name = "symbol";
		List<String> description = new ArrayList<>();
		description.add("Command symbol writes the symbol for the given first argument which");
		description.add("must be a shell special symbol name. If a second argument is given the");
		description.add("command changes the symbol for the given shell special symbol name to");
		description.add("the given argument which must be a single character.");
		this.description = Collections.unmodifiableList(description);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = arguments.trim().split("\\s+");
		if (arguments.length() == 0 || args.length > 2 || args.length == 2 && args[1].length() > 1) {
			env.writeln("Wrong arguments given.");
			return ShellStatus.CONTINUE;
		}
		if (args[0].equalsIgnoreCase("PROMPT")) {
			if (args.length == 2) {
				char oldSymbol = env.getPromptSymbol();
				env.setPromptSymbol(args[1].charAt(0));
				env.writeln("Symbol for PROMPT changed from '" + oldSymbol + "' to '" + env.getPromptSymbol() + "'");
				return ShellStatus.CONTINUE;
			}
			env.writeln("Symbol for PROMPT is '" + env.getPromptSymbol() + "'");
			return ShellStatus.CONTINUE;
		} else if (args[0].equalsIgnoreCase("MORELINES")) {
			if (args.length == 2) {
				char oldSymbol = env.getMorelinesSymbol();
				env.setMorelinesSymbol(args[1].charAt(0));
				env.writeln("Symbol for MORELINES changed from '" + oldSymbol + "' to '" + env.getMorelinesSymbol() + "'");
				return ShellStatus.CONTINUE;
			}
			env.writeln("Symbol for MORELINES is '" + env.getMorelinesSymbol() + "'");
			return ShellStatus.CONTINUE;
		} else if (args[0].equalsIgnoreCase("MULTILINE")) {
			if (args.length == 2) {
				char oldSymbol = env.getMultilineSymbol();
				env.setMultilineSymbol(args[1].charAt(0));
				env.writeln("Symbol for MULTILINE changed from '" + oldSymbol + "' to '" + env.getMultilineSymbol() + "'");
				return ShellStatus.CONTINUE;
			}
			env.writeln("Symbol for MULTILINE is '" + env.getMultilineSymbol() + "'");
			return ShellStatus.CONTINUE;
		} else {
			env.writeln("Wrong shell special symbol name given.");
			return ShellStatus.CONTINUE;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		return description;
	}

}
