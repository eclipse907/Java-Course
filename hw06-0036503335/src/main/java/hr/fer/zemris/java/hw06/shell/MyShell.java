package hr.fer.zemris.java.hw06.shell;

import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.CatShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HexdumpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.LsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.TreeShellCommand;

/**
 * This class represents a custom shell that supports the following commands:
 * charsets, cat, ls, tree, copy, mkdir, hexdump, symbol and help. The shell
 * exits when the command exit is entered. Detailed descriptions of the
 * commands can be gained by entering the help command into the console.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class MyShell {

	/**
	 * The method from which the program starts.
	 * 
	 * @param args command line arguments. Not used
	 *             in this program.
	 * @throws ShellIOException if there is an error while
	 *                          reading from or writing to
	 *                          the standard input/output.
	 */
	public static void main(String[] args) {
		SortedMap<String, ShellCommand> commandsMap = new TreeMap<>();
		commandsMap.put("charsets", new CharsetsShellCommand());
		commandsMap.put("ls", new LsShellCommand());
		commandsMap.put("tree", new TreeShellCommand());
		commandsMap.put("copy", new CopyShellCommand());
		commandsMap.put("mkdir", new MkdirShellCommand());
		commandsMap.put("symbol", new SymbolShellCommand());
		commandsMap.put("help", new HelpShellCommand());
		commandsMap.put("exit", new ExitShellCommand());
		commandsMap.put("cat", new CatShellCommand());
		commandsMap.put("hexdump", new HexdumpShellCommand());
		Environment shellEnvironment = new Environment() {
			
			private Scanner sc = new Scanner(System.in);
			private char promptSymbol = '>';
			private char multilineSymbol = '|';
			private char morelinesSymbol = '\\';
			private SortedMap<String, ShellCommand> commands = Collections.unmodifiableSortedMap(commandsMap);
			
			@Override
			public void writeln(String text) throws ShellIOException {
				try {
					System.out.println(text);
				} catch (Exception ex) {
					throw new ShellIOException(ex.getMessage());
				}
			}
			
			@Override
			public void write(String text) throws ShellIOException {
				try {
					System.out.printf("%s", text);
				} catch (Exception ex) {
					throw new ShellIOException(ex.getMessage());
				}
			}
			
			@Override
			public void setPromptSymbol(Character symbol) {
				promptSymbol = symbol;
			}
			
			@Override
			public void setMultilineSymbol(Character symbol) {
				multilineSymbol = symbol;
			}
			
			@Override
			public void setMorelinesSymbol(Character symbol) {
				morelinesSymbol = symbol;
			}
			
			@Override
			public String readLine() throws ShellIOException {
				try {
					return sc.nextLine();
				} catch (Exception ex) {
					throw new ShellIOException(ex.getMessage());
				}
			}
			
			@Override
			public Character getPromptSymbol() {
				return promptSymbol;
			}
			
			@Override
			public Character getMultilineSymbol() {
				return multilineSymbol;
			}
			
			@Override
			public Character getMorelinesSymbol() {
				return morelinesSymbol;
			}
			
			@Override
			public SortedMap<String, ShellCommand> commands() {
				return commands;
			}
			
		};
		shellEnvironment.writeln("Welcome to MyShell v 1.0");
		ShellStatus status;
		do {
			shellEnvironment.write(shellEnvironment.getPromptSymbol().toString() + " ");
			String input = shellEnvironment.readLine();
			if (input.equals("") || input.trim().length() == 0) {
				status = ShellStatus.CONTINUE;
				continue;
			}
			while (input.charAt(input.length() - 1) == shellEnvironment.getMorelinesSymbol()) {
				input = removeMorelinesSymbol(input, shellEnvironment.getMorelinesSymbol());
				shellEnvironment.write(shellEnvironment.getMultilineSymbol().toString() + " ");
				input += shellEnvironment.readLine();
			}
			String[] splitedInput = input.trim().split("\\s+", 2);
			ShellCommand command = shellEnvironment.commands().get(splitedInput[0]);
			if (command != null) {
				status = command.executeCommand(shellEnvironment, splitedInput.length == 2 ? splitedInput[1] : "");
			} else {
				shellEnvironment.writeln("Unknown command.");
				status = ShellStatus.CONTINUE;
			}
		} while (status == ShellStatus.CONTINUE);
	}
	
	/**
	 * This method removes the morelines symbols from the given
	 * input.
	 * 
	 * @param input the input from which to remove the morelines
	 *              symbols.
	 * @param morelinesSymbol the morelines symbol of the shell.
	 * @return the given input with the morelines symbols removed.
	 */
	private static String removeMorelinesSymbol(String input, char morelinesSymbol) {
		int i = input.length() - 1;
		while (input.charAt(i) == morelinesSymbol) {
			i--;
		}
		return input.substring(0, i + 1);
	}
	
}
