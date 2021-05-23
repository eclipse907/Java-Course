package hr.fer.zemris.java.gui.calc;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * This class represents a digit button that adds a digit
 * to the number displayed when pressed. It inherits the
 * JButton class.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class DigitButton extends JButton {

	private static final long serialVersionUID = 3080598747335307598L;
	private int digit;
	private Calculator calc;
	
	/**
	 * Creates a new digit button with the parameters given.
	 * 
	 * @param digit the digit this button represents.
	 * @param calc a reference to the calculator in which this button
	 *             is located.
	 */
	public DigitButton(int digit, Calculator calc) {
		if (digit < 0 || digit > 9) {
			throw new IllegalArgumentException("Digit must be between 0 and 9.");
		}
		this.digit = digit;
		this.calc = calc;
		setText(Integer.toString(digit));
		setFont(getFont().deriveFont(30f));
		addActionListener(a -> {
			try {
				this.calc.getModel().insertDigit(this.digit);
			} catch (CalculatorInputException | IllegalArgumentException ex) {
				JOptionPane.showMessageDialog(this.calc, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
	}

}
