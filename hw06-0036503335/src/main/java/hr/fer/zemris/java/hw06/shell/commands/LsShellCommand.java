package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Util;

/**
 * This class represents a ls command used in the shell from
 * the hr.fer.zemris.java.hw06.shell package. This class implements
 * the ShellCommand interface.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class LsShellCommand implements ShellCommand {

	private String name;
	private List<String> description;
	
	/**
	 * Creates a new ls command.
	 */
	public LsShellCommand() {
		this.name = "ls";
		List<String> description = new ArrayList<>();
		description.add("Command ls lists all the files and folders in the given directory as well");
		description.add("as their atributes. ls accepts one argument which is the path to the folder");
		description.add("whos content needs to be listed.");
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
				List<Path> content = new ArrayList<>();
				try (Stream<Path> conStream = Files.list(path)) {
					conStream.forEach(content::add);
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				for (Path p : content) {
					BasicFileAttributeView faView = Files.getFileAttributeView(p, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
					BasicFileAttributes attributes = faView.readAttributes();
					FileTime fileTime = attributes.creationTime();
					String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
					env.writeln(
							    String.format("%c%c%c%c %10d %s %s",
							    		      Files.isDirectory(p) ? 'd' : '-',
							    		      Files.isReadable(p) ? 'r' : '-',
							    		      Files.isWritable(p) ? 'w' : '-',
							    		      Files.isExecutable(p) ? 'x' : '-',
							    		      attributes.size(),
							    		      formattedDateTime,
							    		      p.getFileName().toString()
							    		     )
							   );
				}
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
