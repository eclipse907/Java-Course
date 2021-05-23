package hr.fer.zemris.java.gui.calc.model;

import java.util.HashSet;
import java.util.Set;
import java.util.function.DoubleBinaryOperator;

/**
 * This class represents a custom implementation of a calculator
 * data model. It implements the CalcModel interface.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class CalcModelImpl implements CalcModel {
	
	private boolean isEditable;
	private boolean isNegative;
	private String stringValue;
	private double value;
	private String frozenValue;
	private Double activeOperand;
	private DoubleBinaryOperator pendingOperation;
	private Set<CalcValueListener> observers;
	
	/**
	 * Creates a new calculator data model.
	 */
	public CalcModelImpl() {
		this.isEditable = true;
		this.stringValue = "";
		this.observers = new HashSet<>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addCalcValueListener(CalcValueListener l) {
		observers.add(l);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		observers.remove(l);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getValue() {
		return value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setValue(double value) {
		this.value = value;
		stringValue = Math.abs(value) % 1.0 > 0 ? Double.toString(Math.abs(value)) : Integer.toString((int)Math.abs(value));
		isEditable = false;
		isNegative = value < 0;
		freezeValue(null);
		notifyObservers();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEditable() {
		return isEditable;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		value = 0;
		stringValue = "";
		isEditable = true;
		isNegative = false;
		notifyObservers();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clearAll() {
		clearActiveOperand();
		setPendingBinaryOperation(null);
		freezeValue(null);
		clear();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void swapSign() throws CalculatorInputException {
		if (!isEditable) {
			throw new CalculatorInputException("The value is not editable.");
		}
		isNegative = !isNegative;
		value = -value;
		freezeValue(null);
		notifyObservers();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if (!isEditable) {
			throw new CalculatorInputException("The value is not editable.");
		}
		if (stringValue.equals("")) {
			throw new CalculatorInputException("No number entered before decimal point.");
		}
		if (stringValue.contains(".")) {
			throw new CalculatorInputException("Number already contains a decimal point.");
		}
		stringValue += ".";
		value = Double.parseDouble(stringValue) * (isNegative ? -1 : 1);
		freezeValue(null);
		notifyObservers();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if (!isEditable) {
			throw new CalculatorInputException("The model is not editable.");
		}
		if (digit < 0 || digit > 9) {
			throw new IllegalArgumentException("Digit must be between 0 and 9");
		}
		try {
			String newStringValue = stringValue.equals("0") ? Integer.toString(digit) : stringValue + Integer.toString(digit);
			double newValue = Double.parseDouble(newStringValue) * (isNegative ? -1 : 1);
			if (Double.isInfinite(newValue)) {
				throw new CalculatorInputException("The given number is to big.");
			}
			value = newValue;
			stringValue = newStringValue;
			freezeValue(null);
			notifyObservers();
		} catch (NumberFormatException ex) {
			throw new CalculatorInputException(ex.getMessage());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isActiveOperandSet() {
		return activeOperand != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getActiveOperand() throws IllegalStateException {
		if (!isActiveOperandSet()) {
			throw new IllegalStateException("Active operand is not set.");
		}
		return activeOperand;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
		freezeValue(activeOperand % 1.0 > 0 ? Double.toString(activeOperand) : Integer.toString((int)activeOperand));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clearActiveOperand() {
		activeOperand = null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		pendingOperation = op;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		if (hasFrozenValue()) {
			return isNegative ? "-" + frozenValue : frozenValue;
		} else if (!stringValue.equals("")) {
			return isNegative ? "-" + stringValue : stringValue;
		} else {
			return isNegative ? "-0" : "0";
		}
	}
	
	/**
	 * Sets the frozenValue attribute to the value
	 * given.
	 * 
	 * @param value the value to set the frozenValue
	 *              attribute to.
	 */
	public void freezeValue(String value) {
		frozenValue = value;
	}
	
	/**
	 * Checks if there is a frozen value.
	 * 
	 * @return true if there is a frozen value,
	 *         false otherwise.
	 */
	public boolean hasFrozenValue() {
		return frozenValue != null;
	}
	
	/**
	 * Notifies all observers of this calculator data model
	 * that the model changed.
	 */
	private void notifyObservers() {
		for (CalcValueListener listener: observers) {
			listener.valueChanged(this);
		}
	}

}
