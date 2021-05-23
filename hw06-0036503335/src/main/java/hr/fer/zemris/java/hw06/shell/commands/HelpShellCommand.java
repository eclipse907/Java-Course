package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This class represents a help command used in the shell from
 * the hr.fer.zemris.java.hw06.shell package. This class implements
 * the ShellCommand interface.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class HelpShellCommand implements ShellCommand {

	private String name;
	private List<String> description;
	
	/**
	 * Creates a new help command.
	 */
	public HelpShellCommand() {
		this.name = "help";
		List<String> description = new ArrayList<>();
		description.add("Command help lists all the descriptions for the commands supported");
		description.add("by this shell if no arguments are given. If one argument is given the");
		description.add("command lists the description for the command name given as argument if");
		description.add("it exists.");
		this.description = Collections.unmodifiableList(description);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = arguments.trim().split("\\s+");
		if (args.length > 1) {
			env.writeln("Wrong arguments given.");
			return ShellStatus.CONTINUE;
		}
		if (args[0].length() == 0) {
			env.commands().values().forEach(command -> {
				env.writeln(command.getCommandName() + " description:");
				for (String line : command.getCommandDescription()) {
					env.writeln(line);
				}
				env.writeln("");
			});
			return ShellStatus.CONTINUE;
		} else {
			ShellCommand command = env.commands().get(args[0]);
			if (command != null) {
				env.writeln(command.getCommandName() + " description:");
				for (String line : command.getCommandDescription()) {
					env.writeln(line);
				}
				return ShellStatus.CONTINUE;
			} else {
				env.writeln("No command with given name found.");
				return ShellStatus.CONTINUE;
			}
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
