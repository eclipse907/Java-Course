package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
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
 * This class represents a hexdump command used in the shell from
 * the hr.fer.zemris.java.hw06.shell package. This class implements
 * the ShellCommand interface.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class HexdumpShellCommand implements ShellCommand {

	private String name;
	private List<String> description;
	
	/**
	 * Creates a new hexdump command.
	 */
	public HexdumpShellCommand() {
		this.name = "hexdump";
		List<String> description = new ArrayList<>();
		description.add("Command hexdump produces a hex-output of the file given as first");
		description.add("argument. The first argument must be a file.");
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
			Path path = Paths.get(args.get(0));
			if (!Files.exists(path)) {
				throw new IllegalArgumentException("The given file or path to file does not exist.");
			}
			if (!Files.isDirectory(path)) {
				try (InputStream is = new BufferedInputStream(Files.newInputStream(path))) {
					byte [] byteArray = new byte[4 * 1024];
					int memCounter = 0;
					int lineCharNextIndex = 1;
					StringBuilder charRepresentation = new StringBuilder();
					while (true) {
						int r = is.read(byteArray);
						if (r == -1) {
							break;
						}
						for (int i = 0; i < r; i++) {
							if (lineCharNextIndex == 1) {
								env.write(String.format("%08d: ", memCounter * 10));
								charRepresentation = new StringBuilder();
							}
							if (lineCharNextIndex == 8) {
								env.write(byteToHex(byteArray[i]) + "|");
								charRepresentation.append(byteToChar(byteArray[i]));
								lineCharNextIndex++;
								continue;
							}
							if (lineCharNextIndex == 16) {
								env.write(byteToHex(byteArray[i]) + " | ");
								charRepresentation.append(byteToChar(byteArray[i]));
								env.writeln(charRepresentation.toString());
								lineCharNextIndex = 1;
								memCounter++;
								continue;
							}
							env.write(byteToHex(byteArray[i]) + " ");
							charRepresentation.append(byteToChar(byteArray[i]));
							lineCharNextIndex++;
						}
					}
					if (lineCharNextIndex > 1) {
						while (lineCharNextIndex < 16) {
							if (lineCharNextIndex == 8) {
								env.write("  |");
								lineCharNextIndex++;
								continue;
							}
							env.write("   ");
							lineCharNextIndex++;
						}
						env.writeln("   | " + charRepresentation.toString());
					}
				}
				return ShellStatus.CONTINUE;
			} else {
				throw new IllegalArgumentException("Can't produce a hex-output for a directory.");
			}
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
	
	/**
	 * This method returns a string containing the hexadecimal representation
	 * of the given byte. All hex letters are written as upper case characters.
	 *  
	 * @param hexByte the byte to convert.
	 * @return a string containing the hexadecimal representation
	 *         of the given byte.
	 */
	private String byteToHex(byte hexByte) {
		StringBuilder hexString = new StringBuilder();
		char hex;
		hex = Character.toUpperCase(Character.forDigit((hexByte >> 4) & 0xF, 16));
		hexString.append(hex);
		hex = Character.toUpperCase(Character.forDigit(hexByte & 0xF, 16));
		hexString.append(hex);
		return hexString.toString();
	}
	
	/**
	 * This method converts the given byte to a character if
	 * the value of the byte is between 32 and 127 or returns
	 * a '.' character otherwise.
	 * 
	 * @param charByte the byte to convert.
	 * @return the character representation of
	 *         this byte.
	 */
	private char byteToChar(byte charByte) {
		if (charByte < 32 || charByte > 127) {
			return '.';
		} else {
			return (char)charByte;
		}
	}
	
}
