package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * This class represents a stack which stores objects. The stack uses 
 * an array backed collection to store its objects. It
 * supports basic operations typical of a stack.
 * 
 * @author Tin
 * @version 1.0
 */
public class ObjectStack {
	
	private ArrayIndexedCollection array;
	
	/**
	 * This constructor initializes an empty stack.
	 */
	public ObjectStack() {
		this.array = new ArrayIndexedCollection();
	}
	
	/**
	 * Checks if the stack contains any
	 * elements.
	 * 
	 * @return true if the stack doesn't contain
	 *         any elements, false otherwise.
	 */
	public boolean isEmpty() {
		return array.isEmpty();
	}
	
	/**
	 * Returns the number of stored objects in this
	 * stack.
	 * 
	 * @return the number of objects in this stack.
	 */
	public int size() {
		return array.size();
	}
	
	/**
	 * Adds the given object to the stack.
	 * 
	 * @param value the object to be added.
	 */
	public void push(Object value) {
		array.add(value);
	}
	
	/**
	 * Returns the last object added to the stack and
	 * removes it from the stack.
	 * 
	 * @return the object last added to the stack.
	 * @throws EmptyStackException if the stack is
	 *                             empty.
	 */
	public Object pop() {
		if (array.size() == 0) {
			throw new EmptyStackException();
		}
		Object ob = array.get(array.size() - 1);
		array.remove(array.size() - 1);
		return ob;
	}
	
	/**
	 * Returns the last object added to the stack. The
	 * object is not removed from the stack.
	 * 
	 * @return the last object added to the stack.
	 * @throws EmptyStackException if the stack is
	 *                             empty.
	 */
	public Object peek() {
		if (array.size() == 0) {
			throw new EmptyStackException();
		}
		return array.get(array.size() - 1);
	}
	
	/**
	 * Removes all objects stored in the
	 * stack.
	 */
	public void clear() {
		array.clear();
	}
	
}
