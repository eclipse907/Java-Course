package hr.fer.zemris.java.gui.layouts;

/**
 * This class represents a constraint used in the CalcLayout
 * manager to specify the desired position of the component.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class RCPosition {

	private int row;
	private int column;
	
	/**
	 * Creates a new RCPosition constraint with the desired
	 * row and column.
	 * 
	 * @param row the row of the desired position.
	 * @param column the column of the desired position.
	 */
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	/**
	 * Returns the row of the desired position.
	 * 
	 * @return the row of the desired position.
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Returns the column of the desired position.
	 * 
	 * @return the column of the desired position.
	 */
	public int getColumn() {
		return column;
	}
	
	/**
	 * Returns a new RCPosition constraint with the desired row
	 * and column parsed from the given string. The string must be
	 * in the following format: "row number, column number".
	 *  
	 * @param text the string from which to parse the desired row and
	 *             column.
	 * @return a new RCPosition constraint with the row and column
	 *         given.
	 */
	public static RCPosition parse(String text) {
		String[] splited = text.split(",");
		if (splited.length != 2) {
			throw new IllegalArgumentException("Wrong position format.");
		}
		try {
			int row = Integer.parseInt(splited[0].trim());
			int column = Integer.parseInt(splited[1].trim());
			return new RCPosition(row, column);
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException("Wrong position format.");
		}
	}

}
