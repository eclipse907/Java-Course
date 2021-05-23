package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This class represents a charsets command used in the shell from
 * the hr.fer.zemris.java.hw06.shell package. This class implements
 * the ShellCommand interface.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class CharsetsShellCommand implements ShellCommand {
	
	private String name;
	private List<String> description;
	private SortedMap<String, Charset> availableCharsets;

	/**
	 * Creates a new charsets command.
	 */
	public CharsetsShellCommand() {
		this.name = "charsets";
		List<String> description = new ArrayList<>();
		description.add("Command charsets lists the available charsets in this console.");
		description.add("The command does not accept any arguemnts.");
		this.description = Collections.unmodifiableList(description);
		this.availableCharsets = Charset.availableCharsets();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		arguments = arguments.trim();
		if (arguments.length() != 0) {
			env.writeln("charset does not accept any arguments.");
			return ShellStatus.CONTINUE;
		}
		env.writeln("Supported charsets are:");
		for (Charset charset : availableCharsets.values()) {
			env.writeln(charset.toString());
		}
		return ShellStatus.CONTINUE;
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
