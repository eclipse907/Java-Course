package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This class represents an exit command used in the shell from
 * the hr.fer.zemris.java.hw06.shell package. This class implements
 * the ShellCommand interface.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class ExitShellCommand implements ShellCommand {

	private String name;
	private List<String> description;
	
	/**
	 * Creates a new exit command.
	 */
	public ExitShellCommand() {
		this.name = "exit";
		List<String> description = new ArrayList<>();
		description.add("Command exit closes the shell. It accepts no arguments.");
		this.description = Collections.unmodifiableList(description);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		arguments = arguments.trim();
		if (arguments.length() != 0) {
			env.writeln("Command exit does not accept any arguments");
			return ShellStatus.CONTINUE;
		}
		return ShellStatus.TERMINATE;
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
