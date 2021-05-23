package hr.fer.zemris.java.hw06.shell;

import java.util.SortedMap;

/**
 * This interface represents a shell environment that stores shell
 * attributes and provides methods to change them. This interface
 * also allows users to read from and write to the shell.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public interface Environment {

	/**
	 * Reads a line from the shell.
	 * 
	 * @return the line read.
	 * @throws ShellIOException if there is an error while
	 *                          reading the line.
	 */
	String readLine() throws ShellIOException;
	
	/**
	 * Writes the given text on the shell.
	 * 
	 * @param text the text to write.
	 * @throws ShellIOException if there is an error while
	 *                          writing the text.
	 */
	void write(String text) throws ShellIOException;
	
	/**
	 * Writes the given text on the shell and terminates
	 * the line.
	 * 
	 * @param text the text to write.
	 * @throws ShellIOException if there is an error while
	 *                          writing the text.
	 */
	void writeln(String text) throws ShellIOException;
	
	/**
	 * Returns an unmodifiable sorted map containing all the 
	 * commands the shell supports. The keys are the command 
	 * names and the values are objects that implement the 
	 * ShellCommand interface.
	 * 
	 * @return an unmodifiable sorted map containing all the 
	 *         commands the shell supports.
	 */
	SortedMap<String, ShellCommand> commands();
	
	/**
	 * Returns the multiline symbol of the shell.
	 * 
	 * @return the multiline symbol of the shell.
	 */
	Character getMultilineSymbol();
	
	/**
	 * Sets the multiline symbol of the shell to
	 * the given character.
	 * 
	 * @param symbol the character to set.
	 */
	void setMultilineSymbol(Character symbol);
	
	/**
	 * Returns the prompt symbol of the shell.
	 * 
	 * @return the prompt symbol of the shell.
	 */
	Character getPromptSymbol();
	
	/**
	 * Sets the prompt symbol of the shell to
	 * the given character.
	 * 
	 * @param symbol the character to set.
	 */
	void setPromptSymbol(Character symbol);
	
	/**
	 * Returns the morelines symbol of the shell.
	 * 
	 * @return the morelines symbol of the shell.
	 */
	Character getMorelinesSymbol();
	
	/**
	 * Sets the morelines symbol of the shell to
	 * the given character.
	 * 
	 * @param symbol the character to set.
	 */
	void setMorelinesSymbol(Character symbol);
	
}
