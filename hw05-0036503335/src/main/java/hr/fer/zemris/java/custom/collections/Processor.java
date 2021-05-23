package hr.fer.zemris.java.custom.collections;

/**
 * This interface provides users with a method
 * which accepts an object and performs some kind of
 * an operation on it.
 * 
 * @author Tin Reiter
 * @version 1.2
 * @param <T> the type of objects this processor
 *            is processing.
 */
@FunctionalInterface
public interface Processor<T> {
	
	/**
	 * This method accepts an object and performs some kind
	 * of an operation on it.
	 * 
	 * @param value the object to be processed.
	 */
	void process(T value);
	
}