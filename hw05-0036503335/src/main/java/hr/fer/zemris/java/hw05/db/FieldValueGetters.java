package hr.fer.zemris.java.hw05.db;

/**
 * This class contains classes that are used as attribute getters
 * in database queries.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class FieldValueGetters {

	public static final IFieldValueGetter FIRST_NAME = StudentRecord::getFirstName;
	public static final IFieldValueGetter LAST_NAME = StudentRecord::getLastName;
	public static final IFieldValueGetter JMBAG = StudentRecord::getJmbag;
	
}
