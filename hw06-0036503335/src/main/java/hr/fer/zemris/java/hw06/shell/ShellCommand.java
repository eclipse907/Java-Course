package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 * This interface represents a shell command used in the shell
 * from the hr.fer.zemris.java.hw06.shell package.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public interface ShellCommand {

	/**
	 * Executes this shell command and returns the shell status.
	 * 
	 * @param env the shell environment.
	 * @param arguments the unparsed arguments of this command.
	 * @return the shell status. It is one of the statuses from
	 *         the ShellStatus enumeration.
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	/**
	 * Returns the name of this command.
	 * 
	 * @return the name of this command.
	 */
	String getCommandName();
	
	/**
	 * Returns the help description of this command. Each entry
	 * in the list represents a line in the help description.
	 * 
	 * @return the help description of this command. The returned
	 *         list is unmodifiable.
	 */
	List<String> getCommandDescription();
	
}
