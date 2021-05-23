package hr.fer.zemris.java.custom.collections;

/**
 * This class represents a collection of objects and supports
 * basic operations typical for a collection.
 * This class is a template and should only be
 * used as a base class for a specific implementation
 * of a collection and therefore should not be instanced.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class Collection {
	
	/**
	 * Default constructor. Only subclasses and
	 * classes from the same package can access
	 * this constructor. 
	 */
	protected Collection() {		
	}
	
	/**
	 * Checks if the collection contains any
	 * elements.
	 * 
	 * @return true if the collection doesn't contain
	 *         any elements, false otherwise.
	 */
	public boolean isEmpty() {
		if (size() == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Returns the number of stored objects in this
	 * collection.
	 * 
	 * @return the number of objects in this collection.
	 */
	public int size() {
		return 0;
	}
	
	/**
	 * Adds a new object to this collection.
	 * 
	 * @param value the object to be added.
	 */
	public void add(Object value) {		
	}
	
	/**
	 * Checks if the given object is in this
	 * collection using the equals method.
	 * 
	 * @param value the object to be checked
	 * @return true if the given object is inside this collection
	 *         as determined by the equals method, false otherwise.
	 */
	public boolean contains(Object value) {
		return false;
	}
	
	/**
	 * Removes one occurrence of the given object from
	 * the collection. Objects are compared using
	 * the equals method.
	 * 
	 * @param value the object to be removed.
	 * @return true only if the collection contains
	 *         the given object as determined by the
	 *         equals method and one occurrence of it is 
	 *         removed, false otherwise.
	 */
	public boolean remove(Object value) {
		return false;
	}
	
	/**
	 * Returns a new array which contains all elements of
	 * the collection. This method never returns null.
	 * 
	 * @return an array which contains all elements of this collection.
	 * @throws UnsupportedOperationException if not implemented
	 *         in subclasses.
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Calls the process method of the given object
	 * for each element of this collection.
	 * 
	 * @param processor an object that implements
	 *                  the process method from
	 *                  the Processor class.
	 */
	public void forEach(Processor processor) {	
	}
	
	/**
	 * Adds all elements from the given collection to
	 * this collection. The given collection remains
	 * unchanged.
	 * 
	 * @param other a collection from which to add
	 *              objects.
	 */
	public void addAll(Collection other) {
		class AddProcessor extends Processor {
			@Override
			public void process(Object value) {
				add(value);
			}
		}
		other.forEach(new AddProcessor());
	}
	
	/**
	 * Deletes all elements of this collection.
	 */
	public void clear() {		
	}
	
}
