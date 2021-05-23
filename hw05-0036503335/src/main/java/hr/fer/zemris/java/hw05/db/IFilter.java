package hr.fer.zemris.java.hw05.db;

/**
 * This interface represents an object that checks if
 * the given record is acceptable.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
@FunctionalInterface
public interface IFilter {
	
	/**
	 * Checks if the given record is acceptable.
	 * 
	 * @param record the record to check.
	 * @return true if the given record is acceptable,
	 *         false otherwise.
	 */
	public boolean accepts(StudentRecord record);
	
}
