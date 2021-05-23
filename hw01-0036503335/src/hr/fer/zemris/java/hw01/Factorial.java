package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * This class calculates the factorial of a number given through a standard
 * input (default keyboard). The class accepts a number between including 3 and
 * 20 but its method can calculate a factorial from 0 up to 20. 
 * The program exits when the keyword "kraj" is entered.
 *   
 * @author Tin Reiter
 * @version 1.0 
 */
public class Factorial {

	/**
	 * The method from which the program starts. The method accepts
	 * numbers between including 3 and 20 from a standard input and calculates 
	 * their factorial until the keyword "kraj" is entered.
	 * 
	 * @param args command line arguments. Not used in this program.
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		
		while(true) {
			System.out.print("Enter a number > ");
			if (sc.hasNext()) {
				String inputString = sc.next();
				if (inputString.equals("kraj")) {
					System.out.println("Goodbye.");
					break;
				}
				int number;
				try {
					number = Integer.parseInt(inputString);
				} catch (NumberFormatException ex) {
					System.out.println(inputString + " is not a whole number.");
					continue;
				}
				if (number < 3 || number > 20) {
					System.out.println(number + " is not between 3 and 20.");
					continue;
				}
				long factorial;
				try {
					factorial = calculateFactorial(number);
				} catch (IllegalArgumentException ex) {
					System.out.println(ex.getMessage());
					continue;
				}
				System.out.println(number + "! = " + factorial);
			}
		}
		
		
		sc.close();
	}
	
	/**
	 * The method calculates the factorial of a given number.
	 * The number given must be between including 0 and 20.
	 * 
	 * @param number the number for which the factorial is calculated.
	 * @return the factorial of a given number.
	 * @throws IllegalArgumentException if the number is not between including 0 and 20.
	 */
	public static long calculateFactorial(int number) {
		if (number < 0 || number > 20) {
			throw new IllegalArgumentException("Can't calculate factorial for given argument.");
		}
		long factorial = 1;
		for (int i = 2; i <= number; i++) {
			factorial *= i;
		}
		return factorial;
	}

}
