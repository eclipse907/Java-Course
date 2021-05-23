package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * This class is an indexed collection of objects that uses a linked list to
 * store its elements and provides basic operations typical for an indexed collection. 
 * This class implements the List interface.
 * 
 * @author Tin Reiter
 * @version 1.1
 */
public class LinkedListIndexedCollection implements List {
	
	private int size;
	private ListNode first;
	private ListNode last;
	private long modificationCount;
	
	/**
	 * This class represents a node in a double linked
	 * list. The class has 3 attributes: a reference to
	 * the previous node in the list, a reference to the
	 * next node in the list and the value of the node.
	 * @author Tin Reiter
	 * @version 1.0
	 */
	private static class ListNode {
		private ListNode previousNode;
		private ListNode nextNode;
		private Object value;
	}
	
	/**
	 * This constructor creates an empty collection.
	 */
	public LinkedListIndexedCollection() {
	}
	
	/**
	 * This constructor creates an empty collection and fills
	 * it with all elements from the given collection.
	 * 
	 * @param col the collection from which the elements are copied
	 *            into this collection.
	 * @throws NullPointerException if the given collection is null.
	 */
	public LinkedListIndexedCollection(Collection col) {
		if (col == null) {
			throw new NullPointerException();
		}
		addAll(col);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * Adds the given object to this collection at the end of the collection. 
	 * The newly added element becomes the element at the biggest index.
	 * This method has an average complexity of O(1).
	 * 
	 * @throws NullPointerException if the given object is null.
	 */
	@Override
	public void add(Object value) {
		if (value == null) {
			throw new NullPointerException();
		}
		ListNode newNode = new ListNode();
		newNode.value = value;
		if (first == null) {
			first = newNode;
			last = newNode;
		} else {
			newNode.previousNode = last;
			last.nextNode = newNode;
			last = newNode;
		}
		size++;
		modificationCount++;
	}
	
	/**
	 * {@inheritDoc} 
	 */
	@Override
	public boolean contains(Object value) {
		if (value == null) {
			return false;
		}
		ListNode current = first;
		while(current != null) {
			if (current.value.equals(value)) {
				return true;
			}
			current = current.nextNode;
		}
		return false;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean remove(Object value) {
		if (value == null) {
			return false;
		}
		ListNode current = first;
		while (current != null) {
			if (current.value.equals(value)) {
				if (first == last) {
					first = null;
					last = null;
				} else if (current == first) {
					first = current.nextNode;
					current.nextNode.previousNode = null;
				} else if (current == last) {
					last = current.previousNode;
					current.previousNode.nextNode = null;
				} else {
					current.previousNode.nextNode = current.nextNode;
					current.nextNode.previousNode = current.previousNode;
				}
				size--;
				modificationCount++;
				return true;
			}
			current = current.nextNode;
		}
		return false;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object[] toArray() {
		Object[] newArray = new Object[size];
		ListNode current = first;
		int i = 0;
		while (current != null) {
			newArray[i] = current.value;
			i++;
			current = current.nextNode;
		}
		return newArray;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		first = null;
		last = null;
		size = 0;
		modificationCount++;
	}
	
	/**
	 * {@inheritDoc} This method has an average complexity of O(n/2+1).
	 * 
	 * @throws IndexOutOfBoundsException if the index is not
	 *                                   between 0 and size - 1.
	 */
	@Override
	public Object get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		Object value = null;
		ListNode current;
		if (index <= size / 2) {
			current = first;
			int i = 0;
			while (current != null) {
				if (i == index) {
					value = current.value;
				}
				i++;
				current = current.nextNode;
			}
		} else {
			current = last;
			int i = size - 1;
			while (current != null) {
				if (i == index) {
					value = current.value;
				}
				i--;
				current = current.previousNode;
			}
		}
		return value;
	}
	
	/**
	 * {@inheritDoc} This method has an average complexity of O(n/2+1).
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
		ListNode newNode = new ListNode();
		newNode.value = value;
		if (size == 0) {
			first = newNode;
			last = newNode;
		} else if (position == 0) {
			newNode.nextNode = first;
			first.previousNode = newNode;
			first = newNode;
		} else if (position == size ) {
			add(value);
		} else {
			ListNode current;
			if (position <= size / 2) {
				current = first.nextNode;
				int i = 1;
				while (i != position) {
					i++;
					current = current.nextNode;
				}
			} else {
				current = last;
				int i = size - 1;
				while (i != position) {
					i--;
					current = current.previousNode;
				}
			}
			newNode.nextNode = current;
			newNode.previousNode = current.previousNode;
			current.previousNode.nextNode = newNode;
			current.previousNode = newNode;
		}
		size++;
		modificationCount++;
	}
	
	/**
	 * {@inheritDoc} This method has an average complexity of O(n/2+1).
	 */
	@Override
	public int indexOf(Object value) {
		if (value == null) {
			return -1;
		}
		ListNode current = first;
		int i = 0;
		while (current != null) {
			if (current.value.equals(value)) {
				return i;
			}
			i++;
			current = current.nextNode;
		}
		return -1;
	}
	
	/**
	 * {@inheritDoc} This method has an average complexity of O(n/2+1) 
	 * unless the element at the first or last index is removed. 
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
		if (index == 0) {
			first.nextNode.previousNode = null;
			first = first.nextNode;
		} else if (index == size - 1) {
			last.previousNode.nextNode = null;
			last = last.previousNode;
		} else {
			ListNode current;
			if (index <= size / 2) {
				current = first.nextNode;
				int i = 1;
				while (i != index) {
					i++;
					current = current.nextNode;
				}
			} else {
				current = last;
				int i = size - 1;
				while (i != index) {
					i--;
					current = current.previousNode;
				}
			}
			current.previousNode.nextNode = current.nextNode;
			current.nextNode.previousNode = current.previousNode;
		}
		size--;
		modificationCount++;
	}
	
	/**
	 * This class represents an iterator that iterates over a
	 * LinkedListIndexedCollection and returns its elements one
	 * by one. The iteration starts at the first node in the
	 * list. This class implements the ElementsGetter interface.
	 * 
	 * @author Tin Reiter
	 * @version 1.0
	 */
	private static class LinkedListGetter implements ElementsGetter {
		
		private ListNode currentNode;
		private final long savedModificationCount;
		private LinkedListIndexedCollection collection;
		
		/**
		 * This constructor creates a new iterator that iterates
		 * from the first node in the list.
		 * 
		 * @param modificationCount number of changes made to the
		 *                          collection iterated on.
		 * @param collection the collection to iterate on.
		 */
		private LinkedListGetter(long modificationCount, LinkedListIndexedCollection collection) {
			this.currentNode = collection.first;
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
			return currentNode != null;
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
				Object nextElement = currentNode.value;
				currentNode = currentNode.nextNode;
				return nextElement;
			} else {
				throw new NoSuchElementException();
			}
		}
		
	}

	/**
	 * {@inheritDoc} The iteration starts at the first node
	 * in the list.
	 */
	@Override
	public ElementsGetter createElementsGetter() {
		return new LinkedListGetter(modificationCount, this);
	}

}
