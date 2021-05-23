package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
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
 * This class represents a copy command used in the shell from
 * the hr.fer.zemris.java.hw06.shell package. This class implements
 * the ShellCommand interface.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class CopyShellCommand implements ShellCommand {

	private String name;
	private List<String> description;
	
	/**
	 * Creates a new copy command.
	 */
	public CopyShellCommand() {
		this.name = "copy";
		List<String> description = new ArrayList<>();
		description.add("Command copy copies the file given as first argument to the location");
		description.add("given as second argument. If the file already exists in the location");
		description.add("given the user is asked if he wishes to overwrite the file. The command");
		description.add("can only copy files.");
		this.description = Collections.unmodifiableList(description);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			List<String> args = Util.argumentsParser(arguments);
			if (args.size() != 2) {
				throw new IllegalArgumentException("Wrong arguments given.");
			}
			Path file = Paths.get(args.get(0));
			if (!Files.exists(file)) {
				throw new IllegalArgumentException("Given file or path to file to copy does not exist.");
			}
			Path destination = Paths.get(args.get(1));
			if (Files.isDirectory(file)) {
				throw new IllegalArgumentException("Can't copy directories.");
			}
			if (Files.isDirectory(destination)) {
				destination = destination.resolve(file.getFileName());
			}
			if (Files.exists(destination)) {
				env.writeln("File exists in the given destination.");
				env.writeln("Would you like to override (enter Yes/No):");
				while (true) {
					String input = env.readLine().trim();
					if (input.equalsIgnoreCase("Yes")) {
						break;
					} else if (input.equalsIgnoreCase("No")) {
						env.writeln("Entered No so file won't be copied.");
						return ShellStatus.CONTINUE;
					} else {
						env.writeln("Wrong input entered. Enter Yes/No:");
					}
				}
			}
			try (
				 InputStream is = new BufferedInputStream(Files.newInputStream(file));
				 OutputStream os = new BufferedOutputStream(Files.newOutputStream(destination));
				) {
				byte[] data = new byte[4 * 1024];
				while (true) {
					int r = is.read(data);
					if (r == -1) {
						break;
					}
					os.write(data, 0, r);
				}
			}
			env.writeln("File copied.");
			return ShellStatus.CONTINUE;
		} catch (IllegalArgumentException ex) {
			env.writeln(ex.getMessage());
			return ShellStatus.CONTINUE;
		} catch (NoSuchFileException ex) {
			env.writeln("The path " + ex.getMessage() + " does not exist.");
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
