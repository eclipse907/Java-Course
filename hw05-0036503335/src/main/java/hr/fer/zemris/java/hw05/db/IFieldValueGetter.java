package hr.fer.zemris.java.hw05.db;

/**
 * This interface represents a record getter used in
 * expressions found in database queries.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
@FunctionalInterface
public interface IFieldValueGetter {

	/**
	 * Returns the value of this getters record.
	 * 
	 * @param record the record to return.
	 * @return the value of this getters record.
	 */
	public String get(StudentRecord record);

}
