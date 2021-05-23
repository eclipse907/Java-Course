package hr.fer.zemris.java.custom.collections;

/**
 * This interface provides users with a tester that
 * tests if the given object is acceptable or not.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
@FunctionalInterface
public interface Tester {
	
	/**
	 * Tests if the given object is acceptable
	 * or not.
	 * 
	 * @param obj the object to be tested.
	 * @return true if the given object is
	 *         acceptable, false otherwise.
	 */
	boolean test(Object obj);
	
}
