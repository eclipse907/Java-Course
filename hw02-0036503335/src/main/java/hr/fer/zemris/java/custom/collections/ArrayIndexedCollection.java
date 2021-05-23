package hr.fer.zemris.java.custom.collections;

/**
 * This class is an indexed collection of objects that uses an array to
 * store its elements and provides basic operations typical for an indexed collection. 
 * This class is an implementation of the Collection template class.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class ArrayIndexedCollection extends Collection {
	
	private int size;
	private Object[] elements;
	
	/**
	 * This constructor creates an empty collection
	 * with an initial capacity of 16.
	 */
	public ArrayIndexedCollection() {
		this(16);
	}
	
	/**
	 * This constructor creates an empty collection
	 * with the initial capacity specified by the initialCapacity
	 * parameter.
	 *  
	 * @param initialCapacity the initial capacity of the collection.
	 * @throws IllegalArgumentException - if initialCapacity is smaller
	 *                                    than 1.
	 */
	public ArrayIndexedCollection(int initialCapacity ) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException("Initial capacity must be at least 1.");
		}
		this.elements = new Object[initialCapacity];
	}
	
	/**
	 * This constructor creates an empty collection and fills
	 * it with all elements from the given collection. The initial
	 * capacity of the underlying array is set to either 16 or the size
	 * of the given collection if it contains more than 16 elements.
	 * 
	 * @param col the collection from which the elements are copied
	 *            into this collection.
	 * @throws NullPointerException if the given collection is null.
	 */
	public ArrayIndexedCollection(Collection col) {
		this(col, 16);
	}
	
	/**
	 * This constructor creates an empty collection and fills
	 * it with all elements from the given collection. The initial
	 * capacity of the collection is set to either the initialCapacity parameter 
	 * or the size of the given collection if it contains more than initialCapacity 
	 * elements.
	 * 
	 * @param col the collection from which the elements are copied
	 *            into this collection.
	 * @param initialCapacity the initial capacity of the collection.
	 * @throws NullPointerException if the given collection is null.
	 */
	public ArrayIndexedCollection(Collection col, int initialCapacity) {
		if (col == null) {
			throw new NullPointerException();
		}
		if (initialCapacity < col.size()) {
			this.elements = col.toArray();
			this.size = col.size();
		} else {
			this.elements = new Object[initialCapacity];
			addAll(col);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * Adds a new object to this collection at the first empty place
	 * in the array. If the array is full, it is reallocated with double
	 * the previous capacity. This method has an average complexity of O(1) 
	 * except when an reallocation occurs. Then the complexity is O(n).
	 * 
	 * @throws NullPointerException if the given object is null.
	 */
	@Override
	public void add(Object value) {
		insert(value, size);
	}
	
	/**
	 * {@inheritDoc} 
	 */
	@Override
	public boolean contains(Object value) {
		if (value == null) {
			return false;
		}
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean remove(Object value) {
		if (!(contains(value))) {
			return false;
		}
		int index = indexOf(value);
		remove(index);
		return true;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object[] toArray() {
		Object[] newArray = new Object[elements.length];
		for (int i = 0; i < size; i++) {
			newArray[i] = elements[i];
		}
		return newArray;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void forEach(Processor processor) {
		for (Object ob : elements) {
			if (ob != null) {
				processor.process(ob);
			}
		}
	}
	
	/**
	 * {@inheritDoc} The capacity of the underlying array
	 * is left unchanged.
	 */
	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
	}
	
	/**
	 * Returns the element at the given index. Valid indexes are 
	 * from 0 to size - 1. The element is not removed from 
	 * the collection. This method has an average
	 * complexity of O(1).
	 * 
	 * @param index the index of the element to return.
	 * @return the element at the given index.
	 * @throws IndexOutOfBoundsException if the index is not
	 *                                   between 0 and size - 1.
	 */
	public Object get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		return elements[index];
	}
	
	/**
	 * Inserts the given object into the collection at the 
	 * given index by shifting all elements at index or higher 
	 * towards the end of the array. Valid indexes are from 0 to size.
	 * If the array is full, it is reallocated with double the previous 
	 * capacity. This method has an average complexity of O(n) unless
	 * the object is added at the size index. Then the complexity is O(1).
	 * 
	 * @param value the object to be inserted into the collection.
	 * @param position the index at which to insert the object.
	 * @throws IndexOutOfBoundsException if the index is not
	 *                                     between 0 and size.
	 * @throws NullPointerException if the given object is null.
	 */
	public void insert(Object value, int position) {
		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException();
		}
		if (value == null) {
			throw new NullPointerException();
		}
		if (elements.length < size + 1) {
			Object[] oldElements = elements;
			elements = new Object[2 * elements.length];
			for (int i = 0; i < oldElements.length; i++) {
				elements[i] = oldElements[i];				
			}
		}
		if (position < size) {
			for (int i = size; i > position; i--) {
				elements[i] = elements[i - 1];
			}
		}
		elements[position] = value;
		size++;
	}
	
	/**
	 * Searches the collection and returns the index 
	 * of the first occurrence of the given object, or -1
	 * if no object is found. The method uses the equals
	 * method to determine the equality. This method has
	 * an average complexity of O(n).
	 * 
	 * @param value the object to be searched.
	 * @return the index of the first occurrence of the
	 *          given object as determined by the equals 
	 *          method or -1 if no object is found.
	 */
	public int indexOf(Object value) {
		if (value == null) {
			return -1;
		}
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Removes the element at the given index and shifts
	 * all elements at index or higher towards the beginning
	 * of the array. Legal indexes are from 0 to size - 1.
	 * This method has an average complexity of O(n) unless
	 * the element at the last index is removed. Then the
	 * complexity is O(1).
	 * 
	 * @param index the index of the element to remove.
	 * @throws IndexOutOfBoundsException if the index is not
	 *                                   between 0 and size - 1.
	 */
	public void remove(int index) {
		if (index < 0 || index >= size ) {
			throw new IndexOutOfBoundsException();
		}
		if (index < size - 1) {
			for (int i = index; i < size - 1; i++) {
				elements[i] = elements[i + 1];
			}
		}
		size--;
		elements[size] = null;
	}
	
}
