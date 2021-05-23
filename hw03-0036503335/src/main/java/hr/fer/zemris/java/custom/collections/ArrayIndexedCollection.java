package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * This class is an indexed collection of objects that uses an array to
 * store its elements and provides basic operations typical for an indexed collection. 
 * This class implements the List interface.
 * 
 * @author Tin Reiter
 * @version 1.1
 */
public class ArrayIndexedCollection implements List {
	
	private int size;
	private Object[] elements;
	private long modificationCount;
	
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
	 * @throws IllegalArgumentException if initialCapacity is smaller
	 *                                  than 1.
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
		Object[] newArray = new Object[size];
		for (int i = 0; i < size; i++) {
			newArray[i] = elements[i];
		}
		return newArray;
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
		modificationCount++;
	}
	
	/**
     * {@inheritDoc} This method has an average complexity of O(1).
	 * 
	 * @throws IndexOutOfBoundsException if the index is not
	 *                                   between 0 and size - 1.
	 */
	@Override
	public Object get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		return elements[index];
	}
	
	/**
	 * {@inheritDoc} If the array is full, it is reallocated with 
	 * double the previous capacity. This method has an average 
	 * complexity of O(n) unless the object is added at the size index. 
	 * Then the complexity is O(1).
	 * 
	 * @throws IndexOutOfBoundsException if the index is not
	 *                                     between 0 and size.
	 * @throws NullPointerException if the given object is null.
	 */
	@Override
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
		modificationCount++;
	}
	
	/**
	 * {@inheritDoc} This method has an average complexity of O(n).
	 */
	@Override
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
	 * {@inheritDoc} This method has an average complexity 
	 * of O(n) unless the element at the last index is removed. 
	 * Then the complexity is O(1).
	 * 
	 * @throws IndexOutOfBoundsException if the index is not
	 *                                   between 0 and size - 1.
	 */
	@Override
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
		modificationCount++;
	}
	
	/**
	 * This class represents an iterator that iterates over an
	 * ArrayIndexedCollection and returns its elements one
	 * by one. The iteration starts at index 0. This class
	 * implements the ElementsGetter interface.
	 * 
	 * @author Tin Reiter
	 * @version 1.0
	 */
	private static class ArrayGetter implements ElementsGetter {
		
		private int currentIndex;
		private final long savedModificationCount;
		private ArrayIndexedCollection collection;
		
		/**
		 * This constructor creates a new iterator that iterates
		 * from index 0.
		 * 
		 * @param modificationCount number of changes made to the
		 *                          collection iterated on.
		 * @param collection the collection to iterate on.
		 */
		private ArrayGetter(long modificationCount, ArrayIndexedCollection collection) {
			this.savedModificationCount = modificationCount;
			this.collection = collection; 
		}

		/**
		 * {@inheritDoc}
		 * @throws ConcurrentModificationException if the collection iterated on
		 *                                         has been changed.
		 */
		@Override
		public boolean hasNextElement() {
			if (savedModificationCount != collection.modificationCount) {
				throw new ConcurrentModificationException();
			}
			return currentIndex < collection.size;
		}

		/**
		 * {@inheritDoc}
		 * @throws NoSuchElementException if the iteration
	     *                                has no more elements.
	     * @throws ConcurrentModificationException if the collection iterated on
		 *                                         has been changed.
		 */
		@Override
		public Object getNextElement() {
			if (savedModificationCount != collection.modificationCount) {
				throw new ConcurrentModificationException();
			}
			if (hasNextElement()) {
				Object nextElements = collection.elements[currentIndex];
				currentIndex++;
				return nextElements;
			} else {
				throw new NoSuchElementException();
			}
		}
		
	}

	/**
	 * {@inheritDoc} The iteration starts at index 0.
	 */
	@Override
	public ElementsGetter createElementsGetter() {
		return new ArrayGetter(modificationCount, this);
	}
	
}
