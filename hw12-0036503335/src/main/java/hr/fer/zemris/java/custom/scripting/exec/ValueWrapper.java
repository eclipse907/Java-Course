package hr.fer.zemris.java.custom.scripting.exec;

/**
 * This class represents an object that is used as a wrapper
 * around a value.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class ValueWrapper {

	private Object value;
	
	/**
	 * This class represents an add arithmetic operation that can be
	 * performed on objects of type Double or Integer.
	 */
	private static ArithmeticOperation addOperation = new ArithmeticOperation() {
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public Integer doOperationInInteger(Integer first, Integer second) {
			return first + second;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public Double doOperationInDouble(Double first, Double second) {
			return first + second;
		}
		
	};
	
	/**
	 * This class represents a subtract arithmetic operation that can be
	 * performed on objects of type Double or Integer.
	 */
	private static ArithmeticOperation subtractOperation = new ArithmeticOperation() {
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public Integer doOperationInInteger(Integer first, Integer second) {
			return first - second;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public Double doOperationInDouble(Double first, Double second) {
			return first - second;
		}
		
	};
	
	/**
	 * This class represents a multiply arithmetic operation that can be
	 * performed on objects of type Double or Integer.
	 */
	private static ArithmeticOperation multiplyOperation = new ArithmeticOperation() {
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public Integer doOperationInInteger(Integer first, Integer second) {
			return first * second;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public Double doOperationInDouble(Double first, Double second) {
			return first * second;
		}
		
	};
	
	/**
	 * This class represents a divide arithmetic operation that can be
	 * performed on objects of type Double or Integer.
	 */
	private static ArithmeticOperation divideOperation = new ArithmeticOperation() {
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public Integer doOperationInInteger(Integer first, Integer second) {
			return first / second;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public Double doOperationInDouble(Double first, Double second) {
			return first / second;
		}
		
	};
	
	/**
	 * This class represents a compare arithmetic operation that can be
	 * performed on objects of type Double or Integer.
	 */
	private static ArithmeticOperation compareOperation = new ArithmeticOperation() {
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public Integer doOperationInInteger(Integer first, Integer second) {
			return first.compareTo(second);
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public Integer doOperationInDouble(Double first, Double second) {
			return first.compareTo(second);
		}
		
	};

	/**
	 * Creates a new ValueWrapper with the value
	 * given.
	 * 
	 * @param value the value to wrap.
	 */
	public ValueWrapper(Object value) {
		this.value = value;
	}

	/**
	 * Returns the value wrapped in this object.
	 * 
	 * @return the value wrapped in this object.
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Sets the value wrapped in this object to
	 * the value given.
	 * 
	 * @param value the value to set.
	 */
	public void setValue(Object value) {
		this.value = value;
	}
	
	/**
	 * Adds the value given to the value wrapped in this object.
	 * Only integers, doubles or strings representing a number
	 * can be added.
	 * 
	 * @param incValue the value to add.
	 */
	public void add(Object incValue) {
		value = calculateOperation(checkIfAcceptableAndReturn(value), checkIfAcceptableAndReturn(incValue), addOperation);
	}
	
	/**
	 * Subtracts the value given from the value wrapped in this object.
	 * Only integers, doubles or strings representing a number
	 * can be subtracted.
	 * 
	 * @param decValue the value to subtract.
	 */
	public void subtract(Object decValue) {
		value = calculateOperation(checkIfAcceptableAndReturn(value), checkIfAcceptableAndReturn(decValue), subtractOperation);
	}
	
	/**
	 * Multiplies the value given with the value wrapped in this object.
	 * Only integers, doubles or strings representing a number
	 * can be multiplied.
	 * 
	 * @param mulValue the value to multiply.
	 */
	public void multiply(Object mulValue) {
		value = calculateOperation(checkIfAcceptableAndReturn(value), checkIfAcceptableAndReturn(mulValue), multiplyOperation);
	}
	
	/**
	 * Divides the value wrapped in this object with the value given.
	 * Only integers, doubles or strings representing a number
	 * can be divided.
	 * 
	 * @param divValue the value with which to divide.
	 */
	public void divide(Object divValue) {
		value = calculateOperation(checkIfAcceptableAndReturn(value), checkIfAcceptableAndReturn(divValue), divideOperation);
	}
	
	/**
	 * Compares the value wrapped in this object with the
	 * value given.
	 * 
	 * @param withValue the value to compare.
	 * @return a number greater than 0 if the value wrapped is greater than the
	 *         value given, a number less than 0 if the value wrapped is smaller
	 *         than the value given or zero if the value wrapped is equal to the
	 *         value given. Only integers, doubles or strings representing a number
	 *         can be compared.
	 */
	public int numCompare(Object withValue) {
		return (Integer)calculateOperation(checkIfAcceptableAndReturn(value), checkIfAcceptableAndReturn(withValue), compareOperation);
	}
	
	/**
	 * Checks if the given object is a Double, Integer or
	 * String representing a number and returns it's value.
	 * 
	 * @param object the object to check.
	 * @return the object given casted to either
	 *         Integer or Double.
	 * @throws RuntimeException if the given object is not
	 *                          a number.
	 */
	private Number checkIfAcceptableAndReturn(Object object) {
		if (object instanceof Integer || object instanceof Double) {
			return (Number)object;
		} else if (object instanceof String) {
			String number = (String)object;
			try {
				if (number.contains(".") || number.contains("E")) {
					return Double.parseDouble(number);
				} else {
					return Integer.parseInt(number);
				}
			} catch (NumberFormatException ex) {
				throw new RuntimeException("The given string for the arithmetic operation is not an integer or double.");
			}
		} else if (object == null) {
			return Integer.valueOf(0);
		} else {
			throw new RuntimeException("The given value must be either integer, double, string or null.");
		}
	}
	
	/**
	 * Calculates the arithmetic operation given with the arguments
	 * given.
	 * 
	 * @param first the first argument of the operation.
	 * @param second the second argument of the operation.
	 * @param operation the arithmetic operation to calculate.
	 * @return the result of the arithmetic operation as either
	 *         type Double or Integer casted to Number type.
	 */
	private Number calculateOperation(Number first, Number second, ArithmeticOperation operation) {
		if (first instanceof Integer && second instanceof Integer) {
			return operation.doOperationInInteger((Integer)first, (Integer)second);
		} else if (first instanceof Double && second instanceof Double) {
			return operation.doOperationInDouble((Double)first, (Double)second);
		} else if (first instanceof Integer && second instanceof Double) {
			return operation.doOperationInDouble(Double.valueOf((Integer)first.intValue()), (Double)second);
		} else {
			return operation.doOperationInDouble((Double)first, Double.valueOf((Integer)second.intValue()));
		}
	}
	
}
