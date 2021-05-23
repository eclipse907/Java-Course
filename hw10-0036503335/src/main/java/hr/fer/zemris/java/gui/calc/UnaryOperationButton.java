package hr.fer.zemris.java.gui.calc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.DoubleUnaryOperator;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * This class represents a button that performs a unary operation on
 * numbers of type double. It supports operation switching when the 
 * button is registered as a listener on a JCheckBox. If the JCheckBox
 * is checked the button performs an inverse operation instead of a
 * normal one. It inherits the JButton class and implements the
 * ActionListener interface.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class UnaryOperationButton extends JButton implements ActionListener {

	private static final long serialVersionUID = 2722986512058287388L;
	private String normalLabel;
	private String inverseLabel;
	private DoubleUnaryOperator normalOperator;
	private DoubleUnaryOperator inverseOperator;
	private Calculator calc;
	private boolean isInverse;

	/**
	 * Creates a new unary operation button with the parameters given.
	 * 
	 * @param normalLabel the normal label displayed when the JCheckBox is
	 *                    not checked or the button is not registered as
	 *                    a listener.
	 * @param inverseLabel the label displayed when the JCheckBox is checked.
	 * @param normalOperator the unary operation performed when he JCheckBox is
	 *                       not checked or the button is not registered as
	 *                       a listener. 
	 * @param inverseOperator the unary operation performed when the JCheckBox is checked.
	 * @param calc a reference to the calculator in which this button
	 *             is located.
	 */
	public UnaryOperationButton(String normalLabel, String inverseLabel, DoubleUnaryOperator normalOperator,
			                    DoubleUnaryOperator inverseOperator, Calculator calc) {
		this.normalLabel = normalLabel;
		this.inverseLabel = inverseLabel;
		this.normalOperator = normalOperator;
		this.inverseOperator = inverseOperator;
		this.calc = calc;
		setText(this.normalLabel);
		addActionListener(a -> {
			CalcModel model = this.calc.getModel();
			if (isInverse) {
				model.setValue(this.inverseOperator.applyAsDouble(model.getValue()));
			} else {
				model.setValue(this.normalOperator.applyAsDouble(model.getValue()));
			}
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
