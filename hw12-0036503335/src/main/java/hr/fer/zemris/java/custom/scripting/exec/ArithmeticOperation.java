package hr.fer.zemris.java.custom.scripting.exec;

/**
 * This interface represents an arithmetic operation that can
 * be applied to objects of type Double or Integer.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public interface ArithmeticOperation {
	
	/**
	 * Applies this artihemtic operation to the Double arguments given.
	 * 
	 * @param first the first Double argument of the arithmetic operation.
	 * @param second the second Double argument of the arithmetic operation.
	 * @return the result of this arithmetic operation. The result is of
	 *         type Double but is returned as type Number. 
	 */
	Number doOperationInDouble(Double first, Double second);
	
	/**
	 * Applies this artihemtic operation to the Integer arguments given.
	 * 
	 * @param first the first Integer argument of the arithmetic operation.
	 * @param second the second Integer argument of the arithmetic operation.
	 * @return the result of this arithmetic operation. The result is of
	 *         type Integer but is returned as type Number. 
	 */
	Number doOperationInInteger(Integer first, Integer second);
	
}
