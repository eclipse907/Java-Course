package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
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
 * This class represents a cat command used in the shell from
 * the hr.fer.zemris.java.hw06.shell package. This class implements
 * the ShellCommand interface.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class CatShellCommand implements ShellCommand {

	private String name;
	private List<String> description;
	
	/**
	 * Creates a new cat command.
	 */
	public CatShellCommand() {
		this.name = "cat";
		List<String> description = new ArrayList<>();
		description.add("Command cat opens the file given as first argument and displays its content");
		description.add("on the console. If a second argument is given it is taken as a charset to use");
		description.add("to decode the bytes from the given file. The first argument must be a file and");
		description.add("if a seconda argument is given it must be a valid charset name.");
		this.description = Collections.unmodifiableList(description);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			List<String> args = Util.argumentsParser(arguments);
			if (args.size() != 1 && args.size() != 2) {
				throw new IllegalArgumentException("Wrong arguments given.");
			}
			Path path = Paths.get(args.get(0));
			if (!Files.exists(path)) {
				throw new IllegalArgumentException("Given file or path to file does not exist.");
			}
			Charset charset;
			if (args.size() == 2) {
				charset = Charset.forName(args.get(1));
			} else {
				charset = Charset.defaultCharset();
			}
			if (!Files.isDirectory(path)) {
				try (BufferedReader br = Files.newBufferedReader(path, charset)) {
					while (true) {
						String line = br.readLine();
						if (line == null) {
							break;
						}
						env.writeln(line);
					}
				}
				return ShellStatus.CONTINUE;
			} else {
				throw new IllegalArgumentException("Can't display directories.");
			}
		} catch (IllegalCharsetNameException | UnsupportedCharsetException ex) {
			env.writeln("Wrong or unsupported charset given.");
			return ShellStatus.CONTINUE;
		} catch (IllegalArgumentException ex) {
			env.writeln(ex.getMessage());
			return ShellStatus.CONTINUE;
		} catch (CharacterCodingException ex) {
			env.writeln("Can't display given file.");
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
