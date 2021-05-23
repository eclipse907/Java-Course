package hr.fer.zemris.java.gui.calc;

import java.awt.Container;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;
import hr.fer.zemris.java.gui.layouts.CalcLayout;

/**
 * This class represents a GUI calculator that can perform
 * basic operations. This class inherits the JFrame class.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class Calculator extends JFrame {

	private static final long serialVersionUID = -5148492242213114522L;
	private CalcModel model;
	private List<Double> stack;
	
	/**
	 * Creates a new GUI calculator.
	 */
	public Calculator() {
		this.model = new CalcModelImpl();
		this.stack = new ArrayList<>();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Java Calculator v1.0");
		setLocation(200, 200);
		setSize(500, 500);
		initGUI();
	}
	
	/**
	 * Initializes the graphical user interface of this
	 * calculator.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(5));
		JCheckBox checkBox = new JCheckBox("Inv");
		Screen screen = new Screen(model);
		model.addCalcValueListener(screen);
		cp.add(screen, "1, 1");
		JButton equals = new JButton("=");
		equals.addActionListener(a -> {
			if (model.isActiveOperandSet()) {
				model.setValue(model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue()));
				model.clearActiveOperand();
				model.setPendingBinaryOperation(null);
			}
		});
		cp.add(equals, "1, 6");
		JButton clear = new JButton("clr");
		clear.addActionListener(a -> {
			model.clear();
		});
		cp.add(clear, "1, 7");
		JButton inverseX = new JButton("1/x");
		inverseX.addActionListener(a -> {
			model.setValue(1 / model.getValue());
		});
		cp.add(inverseX, "2, 1");
		UnaryOperationButton sin = new UnaryOperationButton("sin", "arcsin", Math::sin, Math::asin, this);
		checkBox.addActionListener(sin);
		cp.add(sin, "2, 2");
		cp.add(new DigitButton(7, this), "2, 3");
		cp.add(new DigitButton(8, this), "2, 4");
		cp.add(new DigitButton(9, this), "2, 5");
		cp.add(new BinaryOperationButton("/", (first, second) -> first / second, this), "2, 6");
		JButton reset = new JButton("reset");
		reset.addActionListener(a -> {
			model.clearAll();
			stack.clear();
		});
		cp.add(reset, "2, 7");
		UnaryOperationButton log = new UnaryOperationButton("log", "10^x", Math::log10, x -> Math.pow(10, x), this);
		checkBox.addActionListener(log);
		cp.add(log, "3, 1");
		UnaryOperationButton cos = new UnaryOperationButton("cos", "arccos", Math::cos, Math::acos, this);
		checkBox.addActionListener(cos);
		cp.add(cos, "3, 2");
		cp.add(new DigitButton(4, this), "3, 3");
		cp.add(new DigitButton(5, this), "3, 4");
		cp.add(new DigitButton(6, this), "3, 5");
		cp.add(new BinaryOperationButton("*", (first, second) -> first * second, this), "3, 6");
		JButton push = new JButton("push");
		push.addActionListener(a -> {
			stack.add(model.getValue());
		});
		cp.add(push, "3, 7");
		UnaryOperationButton ln = new UnaryOperationButton("ln", "e^x", Math::log, Math::exp, this);
		checkBox.addActionListener(ln);
		cp.add(ln, "4, 1");
		UnaryOperationButton tan = new UnaryOperationButton("tan", "arctan", Math::tan, Math::atan, this);
		checkBox.addActionListener(tan);
		cp.add(tan, "4, 2");
		cp.add(new DigitButton(1, this), "4, 3");
		cp.add(new DigitButton(2, this), "4, 4");
		cp.add(new DigitButton(3, this), "4, 5");
		cp.add(new BinaryOperationButton("-", (first, second) -> first - second, this), "4, 6");
		JButton pop = new JButton("pop");
		pop.addActionListener(a -> {
			if (stack.size() > 0) {
				model.setValue(stack.remove(stack.size() - 1));
			} else {
				JOptionPane.showMessageDialog(this, "Stack is empty.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		cp.add(pop, "4, 7");
		PowerButton power = new PowerButton("x^n", "x^(1/n)", this);
		checkBox.addActionListener(power);
		cp.add(power, "5, 1");
		UnaryOperationButton ctg = new UnaryOperationButton("ctg", "arcctg", x -> 1 / Math.tan(x), x -> Math.atan(1 / x), this);
		checkBox.addActionListener(ctg);
		cp.add(ctg, "5, 2");
		cp.add(new DigitButton(0, this), "5, 3");
		JButton swapSign = new JButton("+/-");
		swapSign.addActionListener(a -> {
			try {
				model.swapSign();
			} catch (CalculatorInputException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		cp.add(swapSign, "5, 4");
		JButton insertDecimalPoint = new JButton(".");
		insertDecimalPoint.addActionListener(a -> {
			try {
				model.insertDecimalPoint();
			} catch (CalculatorInputException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		cp.add(insertDecimalPoint, "5, 5");
		cp.add(new BinaryOperationButton("+", Double::sum, this), "5, 6");
		cp.add(checkBox, "5, 7");
	}
	
	/**
	 * Returns the data model this calculator is
	 * using to store data.
	 * 
	 * @return the data model of this calculator.
	 */
	public CalcModel getModel() {
		return model;
	}
	
	/**
	 * The main method from which the calculator starts.
	 * 
	 * @param args command line arguments. Not used in
	 *             this program.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new Calculator().setVisible(true);
      });
	}

}
