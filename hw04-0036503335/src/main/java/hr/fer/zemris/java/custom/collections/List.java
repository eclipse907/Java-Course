package hr.fer.zemris.java.custom.collections;

/**
 * This interface represents a list and provides users with a 
 * list of operations typical for an indexed collection. 
 * All objects that implement this interface allow duplicate 
 * elements and don't allow null values. This interface inherits
 * the Collection interface.
 * 
 * @author Tin Reiter
 * @version 1.1
 * @param <T> the type of objects stored in this
 *            list.
 */
public interface List<T> extends Collection<T> {
	
	/**
	 * Returns the element at the given index. Valid indexes are 
	 * from 0 to size - 1. The element is not removed from 
	 * the list.
	 * 
	 * @param index the index of the element to return.
	 * @return the element at the given index.
	 */
	T get(int index);
	
	/**
	 * Inserts the given object into the list at the 
	 * given index by shifting all elements at index or higher 
	 * towards the end of the list. Valid indexes are from 0 
	 * to size.
	 * 
	 * @param value the object to be inserted into the list.
	 * @param position the index at which to insert the object.
	 */
	void insert(T value, int position);
	
	/**
	 * Searches the list and returns the index 
	 * of the first occurrence of the given object, or -1
	 * if no object is found. This method uses the equals
	 * method to determine the equality.
	 * 
	 * @param value the object to search for.
	 * @return the index of the first occurrence of the
	 *          given object as determined by the equals 
	 *          method or -1 if no object is found.
	 */
	int indexOf(T value);
	
	/**
	 * Removes the element at the given index and shifts
	 * all elements at index + 1 or higher towards the beginning
	 * of the list. Legal indexes are from 0 to size - 1.
	 * 
	 * @param index the index of the element to remove.
	 */
	void remove(int index);
	
}
