package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Util;

/**
 * This class represents a tree command used in the shell from
 * the hr.fer.zemris.java.hw06.shell package. This class implements
 * the ShellCommand interface.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class TreeShellCommand implements ShellCommand {

	private String name;
	private List<String> description;
	
	/**
	 * Creates a new tree command.
	 */
	public TreeShellCommand() {
		this.name = "tree";
		List<String> description = new ArrayList<>();
		description.add("Command tree lists all the subfolders and their content of the");
		description.add("given directory. Tree accepts one argument which is the directory");
		description.add("whose subfolders and their content needs to be listed.");
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
				throw new IllegalArgumentException("The given directory or path to directory does not exist.");
			}
			if (Files.isDirectory(path)) {
				Files.walkFileTree(path, new FileVisitor<Path>() {

					private int level = 0;
					
					@Override
					public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
						for (int i = 0; i < level; i++) {
							env.write("  ");
						}
						env.write(dir.getFileName().toString() + "\n");
						level++;
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
						for (int i = 0; i < level; i++) {
							env.write("  ");
						}
						env.write(file.getFileName().toString() + "\n");
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
						level--;
						return FileVisitResult.CONTINUE;
					}
					
				});
				return ShellStatus.CONTINUE;
			} else {
				throw new IllegalArgumentException("The given argument is not a directory.");
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

}
