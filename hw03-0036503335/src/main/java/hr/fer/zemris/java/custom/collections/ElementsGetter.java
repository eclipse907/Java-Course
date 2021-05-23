package hr.fer.zemris.java.custom.collections;

/**
 * This interface provides users with an iterator that
 * iterates over a collection and returns its elements
 * one by one.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public interface ElementsGetter {
	
	/**
	 * This method checks if the iteration has
	 * any more elements.
	 * 
	 * @return true if the iteration has more 
	 *         elements, false otherwise.
	 */
	boolean hasNextElement();
	
	/**
	 * Returns the next element in the
	 * iteration.
	 * 
	 * @return the next element in the
	 *         iteration.
	 */
	Object getNextElement();
	
	/**
	 * This method calls the process method from the
	 * Processor interface of the given object for each 
	 * remaining element of the iteration.
	 * 
	 * @param p an object that implements the Processor 
	 *          interface.
	 */
	default void processRemaining(Processor p) {
		while (hasNextElement()) {
			p.process(getNextElement());
		}
	}
	
}
