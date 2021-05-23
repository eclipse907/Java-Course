package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * This class implements a simple expression evaluator using an
 * object stack. The class calculates the result of expressions in
 * the following format: "[2 whole numbers] [arithmetic operation]". Supported
 * arithmetic operations are: +, -, /, *, %(the remainder of a whole number division).
 * The expression must be given as the first command line argument.
 *    
 * @author Tin Reiter
 * @version 1.0
 */
public class StackDemo {
	
	/**
	 * The method from which the program starts.
	 * 
	 * @param args command line arguments.
	 * @throws IllegalArgumentException if the given expression
	 *                                  is invalid.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			throw new IllegalArgumentException("Wrong number of command line arguments entered.");
		}
		String[] expression = args[0].split("\\s+");
		ObjectStack stack = new ObjectStack();
		for (String element : expression) {
			try {
				stack.push(Integer.valueOf(element));
			} catch (NumberFormatException ex) {
				try {
					Integer second = (Integer)stack.pop();
					Integer first = (Integer)stack.pop();
					if (element.equals("+")) {
						stack.push(Integer.valueOf(first + second));
					} else if (element.equals("-")) {
						stack.push(Integer.valueOf(first - second));
					} else if (element.equals("/")) {
						if (second == 0) {
							throw new IllegalArgumentException("The expression contains division with zero.");
						}
						stack.push(Integer.valueOf(first / second));
					} else if (element.equals("*")) {
						stack.push(Integer.valueOf(first * second));
					} else if (element.equals("%")) {
						stack.push(Integer.valueOf(first % second));
					} else {
						throw new IllegalArgumentException(
								"The given expression contains a string that is neither a number or an acceptable arithmetic operator."
								);
					}
				} catch (EmptyStackException e) {
					throw new IllegalArgumentException("The given expression has too few numbers for the given arithmetic operations.");
				}
			}
		}
		if (stack.size() != 1) {
			throw new IllegalArgumentException("The given expression has too much numbers for the given arithmetic operations."); 
		} else {
			System.out.println("Expression evaluates to " + stack.pop() + ".");
		}
	}
}
