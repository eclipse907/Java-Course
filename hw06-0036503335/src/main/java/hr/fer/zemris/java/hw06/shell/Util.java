package hr.fer.zemris.java.hw06.shell;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains static methods which are utilized by the shell
 * commands from the hr.fer.zemris.java.hw06.shell.commands package.
 * This class should not be instanced.
 *  
 * @author Tin Reiter
 * @version 1.0
 */
public class Util {

	/**
	 * This method parses the shell command arguments from the given string 
	 * and stores them in a list.
	 * 
	 * @param argumentsString the unparsed string containing the shell
	 *                        command arguments.
	 * @return a list containing the shell command arguments.
	 * @throws IllegalArgumentException if the given arguments are
	 *                                  wrong.
	 */
	public static List<String> argumentsParser(String argumentsString) {
		List<String> args = new ArrayList<>();
		char[] charArrayArguments = argumentsString.trim().toCharArray();
		StringBuilder argument = new StringBuilder();
		for (int i = 0; i < charArrayArguments.length; i++) {
			if (charArrayArguments[i] == '\r' || charArrayArguments[i] == '\t' || charArrayArguments[i] == ' ') {
				if (argument.length() > 0) {
					args.add(argument.toString().trim());
					argument = new StringBuilder();
				}
				while (
					   i < charArrayArguments.length &&
					   (charArrayArguments[i] == '\r' ||
					   charArrayArguments[i] == '\t' ||
					   charArrayArguments[i] == ' ')
					  ) {
					i++;
				}
			}
			if (charArrayArguments[i] == '"' ) {
				i++;
				while(i < charArrayArguments.length && charArrayArguments[i] != '"') {
					if (
						charArrayArguments[i] == '\\' &&
						i + 1 < charArrayArguments.length &&
						(charArrayArguments[i + 1] == '"' || charArrayArguments[i + 1] == '\\')
					   ) {
						i++;
					}
					argument.append(charArrayArguments[i]);
					i++;
				}
				if (i >= charArrayArguments.length) {
					throw new IllegalArgumentException("Path not closed with a '\"'.");
				}
				if (
					i + 1 < charArrayArguments.length &&
					charArrayArguments[i + 1] != '\r' &&
					charArrayArguments[i + 1] != '\t' &&
					charArrayArguments[i + 1] != ' '
				   ) {
					throw new IllegalArgumentException("Wrong arguments given.");
				}
				args.add(argument.toString().trim());
				argument = new StringBuilder();
				continue;
			}
			argument.append(charArrayArguments[i]);
		}
		if (argument.length() > 0) {
			args.add(argument.toString().trim());
		}
		return args;
	}

}
