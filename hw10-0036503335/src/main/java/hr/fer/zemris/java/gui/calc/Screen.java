package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;

/**
 * This class represents a calculator screen that does not
 * accept input from a keyboard. It changes the display when 
 * the underlying data model is changed. It inherits the JLabel 
 * class and implements the CalcValueListener interface.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class Screen extends JLabel implements CalcValueListener {

	private static final long serialVersionUID = 3160237516075176395L;
	
	/**
	 * Creates a new calculator screen with the parameters given.
	 * 
	 * @param model a reference to the underlying data model used
	 *              by the calculator.
	 */
	public Screen(CalcModel model) {
		setBackground(Color.YELLOW);
		setHorizontalAlignment(SwingConstants.RIGHT);
		setFont(getFont().deriveFont(30f));
		setText(model.toString());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void valueChanged(CalcModel model) {
		setText(model.toString());
	}

}
