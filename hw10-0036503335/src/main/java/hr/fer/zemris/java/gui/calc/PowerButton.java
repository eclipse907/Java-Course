package hr.fer.zemris.java.gui.calc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.DoubleBinaryOperator;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * This class represents a button that raises the first argument to
 * the power of the second. It also can calculate the n-th root of
 * the first argument where n is the second argument by being assigned
 * as an observer to a JCheckBox. If the JCheckBox is checked the button
 * calculates the root, otherwise it calculates the power. This class inherits
 * the JButton class and implements the ActionListener interface.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class PowerButton extends JButton implements ActionListener {

	private static final long serialVersionUID = -4839739083673043302L;
	private String normalLabel;
	private String inverseLabel;
	private DoubleBinaryOperator normal;
	private DoubleBinaryOperator inverse;
	private Calculator calc;
	private boolean isInverse;

	/**
	 * Creates a new power button with the parameters given.
	 * 
	 * @param normalLabel the normal label displayed when the JCheckBox is
	 *                    not checked or the button is not registered as
	 *                    a listener.
	 * @param inverseLabel the label displayed when the JCheckBox is checked.
	 * @param calc a reference to the calculator in which this button
	 *             is located.
	 */
	public PowerButton(String normalLabel, String inverseLabel, Calculator calc) {
		this.normalLabel = normalLabel;
		this.inverseLabel = inverseLabel;
		this.calc = calc;
		this.normal = Math::pow;
		this.inverse = (x, n) -> Math.pow(x, 1 / n);
		setText(normalLabel);
		addActionListener(a -> {
			CalcModel model = this.calc.getModel();
			if (model.isActiveOperandSet()) {
				model.setActiveOperand(model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue()));
			} else {
				model.setActiveOperand(model.getValue());
			}
			model.setPendingBinaryOperation(isInverse ? this.inverse : this.normal);
			model.clear();
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JCheckBox box = (JCheckBox)e.getSource();
		if (box.isSelected()) {
			isInverse = true;
			setText(inverseLabel);
			repaint();
		} else {
			isInverse = false;
			setText(normalLabel);
			repaint();
		}
	}

}
