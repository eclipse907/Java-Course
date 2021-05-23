package hr.fer.zemris.java.custom.collections;

/**
 * This interface provides users with a list of operations
 * typical for a collection. All objects that implement
 * this interface allow duplicate elements and don't allow
 * null values.
 *  
 * @author Tin Reiter
 * @version 1.1
 */
public interface Collection {

	/**
	 * Checks if the collection contains any
	 * elements. This method has a default implementation
	 * and doesn't have to be implemented.
	 * 
	 * @return true if the collection doesn't contain
	 *         any elements, false otherwise.
	 */
	default boolean isEmpty() {
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
	int size();

	/**
	 * Adds a new object to this collection.
	 * 
	 * @param value the object to be added.
	 */
	void add(Object value);

	/**
	 * Checks if the given object is in this
	 * collection using the equals method.
	 * 
	 * @param value the object to be checked
	 * @return true if the given object is inside this collection
	 *         as determined by the equals method, false otherwise.
	 */
	boolean contains(Object value);

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
	boolean remove(Object value);

	/**
	 * Returns a new array which contains all elements of
	 * the collection. This method never returns null.
	 * 
	 * @return an array which contains all elements of this collection.
	 */
	Object[] toArray();

	/**
	 * Calls the process method of the given object from 
	 * the Processor interface for each element of this collection.
	 * 
	 * @param processor an object that implements
	 *                  the Processor interface.
	 */
	default void forEach(Processor processor) {
		ElementsGetter getter = createElementsGetter();
		while (getter.hasNextElement()) {
			processor.process(getter.getNextElement());
		}
	}

	/**
	 * Adds all elements from the given collection to
	 * this collection. The given collection remains
	 * unchanged. This method has a default implementation
	 * and doesn't have to be implemented.
	 * 
	 * @param other a collection from which to add
	 *              objects.
	 */
	default void addAll(Collection other) {
		other.forEach(value -> add(value));
	}

	/**
	 * Deletes all elements of this collection.
	 */
	void clear();
	
	/**
	 * Creates a new iterator that iterates over this
	 * collection and returns its elements one by one.
	 * 
	 * @return a new iterator that iterates over this
	 *         collection.
	 */
	ElementsGetter createElementsGetter();
	
	/**
	 * This method adds all elements from the given collection
	 * that pass the test method from the Tester interface to 
	 * this collection.
	 * 
	 * @param col the collection to add to this collection.
	 * @param tester an object that implements the Tester
	 *               interface.
	 */
	default void addAllSatisfying(Collection col, Tester tester) {
		ElementsGetter getter = col.createElementsGetter();
		while (getter.hasNextElement()) {
			Object element = getter.getNextElement();
			if (tester.test(element)) {
				add(element);
			}
		}
	}

}