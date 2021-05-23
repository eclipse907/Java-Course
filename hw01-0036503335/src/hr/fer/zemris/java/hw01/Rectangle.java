package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * This class calculates the circumference and surface of a rectangle
 * for the given width and height. The class receives the width and height
 * through a standard input or over command line arguments (first argument width, 
 * second argument height). If the wrong number of command line arguments is given,
 * the program displays an error message and exits.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class Rectangle {

	/**
	 * The main method from which the program starts. The method accepts
	 * width and height from either the standard input, or from the command
	 * line arguments and prints the circumference and surface for 
	 * the appropriate rectangle.
	 * 
	 * @param args command line arguments. If used the first must be the width,
	 *             and the second must be height.
	 */
	public static void main(String[] args) {
		if (args.length == 2) {
			double width = widthParser(args[0]);
			if (width == -1) {
				System.out.println("Program will now exit.");
				System.exit(-1);
			}
			double height = heightParser(args[1]);
			if (height == -1) {
				System.out.println("Program will now exit.");
				System.exit(-1);
			}
			
			
			System.out.println(
					"Rectangle with a width of " + Double.toString(width) +
					" and a height of " + Double.toString(height) +
					" has a circumference of " + Double.toString(circumferenceRectangle(width, height)) +
					" and a surface of " + Double.toString(surfaceRectangle(width, height)) + "."
			);
			
			
			System.exit(0);
		} else if(args.length == 0) {
			Scanner sc = new Scanner(System.in);
			
			
			double width;
			while (true) {
				System.out.print("Enter width > ");
				String inputString;
				if (sc.hasNext()) {
					inputString = sc.next();
					width = widthParser(inputString);
					if (width == -1) {
						continue;
					}
					break;
				}
			}
			double height;
			while (true) {
				System.out.print("Enter height > ");
				String inputString;
				if (sc.hasNext()) {
					inputString = sc.next();
					height = heightParser(inputString);
					if (height == -1) {
						continue;
					}
					break;
				}
			}
			
			
			System.out.println(
					"Rectangle with a width of " + Double.toString(width) +
					" and a height of " + Double.toString(height) +
					" has a circumference of " + Double.toString(circumferenceRectangle(width, height)) +
					" and a surface of " + Double.toString(surfaceRectangle(width, height)) + "."
			);
			
			
			sc.close();
			System.exit(0);
		} else {
			System.out.printf("Wrong number of command line arguments entered.%nProgram will now exit.");
			System.exit(1);
		}
	}
	
	/**
	 * The method parses the width of the rectangle from the given string.
	 * 
	 * @param inputString string from which to parse the width of the rectangle.
	 * @return the width of the rectangle.
	 */
	public static double widthParser(String inputString) {
		double width;
		inputString = inputString.trim();
		try {
			width = Double.parseDouble(inputString);
		} catch (NumberFormatException ex) {
			System.out.println(inputString + " is not a number.");
			return -1;
		}
		if (width <= 0) {
			System.out.println("You have entered a negative or zero value for width.");
			return -1;
		}
		return width;
	}
	
	/**
	 * The method parses the height of the rectangle from the given string.
	 * 
	 * @param inputString string from which to parse the height of the rectangle.
	 * @return the height of the rectangle.
	 */
	public static double heightParser(String inputString) {
		double height;
		inputString = inputString.trim();
		try {
			height = Double.parseDouble(inputString);
		} catch (NumberFormatException ex) {
			System.out.println(inputString + " is not a number.");
			return -1;
		}
		if (height <= 0) {
			System.out.println("You have entered a negative or zero value for height.");
			return -1;
		}
		return height;
	}

	/**
	 * The method calculates the circumference of a rectangle for
	 * for the given width and height.
	 * 
	 * @param width the width of the rectangle.
	 * @param height the height of the rectangle.
	 * @return the circumference of the rectangle.
	 */
	public static double circumferenceRectangle(double width, double height) {
		return 2 * width + 2 * height;
	}
	
	/**
	 * The method calculates the surface of a rectangle for
	 * the given width and height.
	 * 
     * @param width the width of the rectangle.
	 * @param height the height of the rectangle.
	 * @return the surface of the rectangle.
	 */
	public static double surfaceRectangle(double width, double height) {
		return width * height;
	}
}
