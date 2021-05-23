package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleBinaryOperator;
import javax.swing.JButton;
import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * This class represents a button that performs a binary operation
 * on numbers of type double. This class inherits the JButton class.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class BinaryOperationButton extends JButton {

	private static final long serialVersionUID = 2491977077625981544L;
	private DoubleBinaryOperator operator;
	private Calculator calc;

	/**
	 * Creates a new binary operation button with the given parameters.
	 * 
	 * @param label the label of the button.
	 * @param operator the binary operation the button performs.
	 * @param calc a reference to the calculator in which this button
	 *             is located.
	 */
	public BinaryOperationButton(String label, DoubleBinaryOperator operator, Calculator calc) {
		this.operator = operator;
		this.calc = calc;
		setText(label);
		addActionListener(a -> {
			CalcModel model = this.calc.getModel();
			if (model.isActiveOperandSet()) {
				model.setActiveOperand(model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue()));
			} else {
				model.setActiveOperand(model.getValue());
			}
			model.setPendingBinaryOperation(this.operator);
			model.clear();
		});
	}
}
