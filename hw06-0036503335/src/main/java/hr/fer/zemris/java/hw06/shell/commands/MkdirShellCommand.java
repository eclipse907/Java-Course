package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Util;

/**
 * This class represents a mkdir command used in the shell from
 * the hr.fer.zemris.java.hw06.shell package. This class implements
 * the ShellCommand interface.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class MkdirShellCommand  implements ShellCommand{

	private String name;
	private List<String> description;
	
	/**
	 * Creates a new mkdir command.
	 */
	public MkdirShellCommand() {
		this.name = "mkdir";
		List<String> description = new ArrayList<>();
		description.add("Command mkdir creates the appropriate directory structure for");
		description.add("the given argument. The arguemnt must be a directory.");
		this.description = Collections.unmodifiableList(description);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			List<String> args = Util.argumentsParser(arguments);
			if (args.size() != 1) {
				throw new IllegalArgumentException("Wrong arguments given.");
			}
			Path dir = Paths.get(args.get(0));
			Files.createDirectories(dir);
			env.writeln("Directory structure created.");
			return ShellStatus.CONTINUE;
		} catch (IllegalArgumentException ex) {
			env.writeln(ex.getMessage());
			return ShellStatus.CONTINUE;
		} catch (IOException ex) {
			env.writeln(ex.getMessage());
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
