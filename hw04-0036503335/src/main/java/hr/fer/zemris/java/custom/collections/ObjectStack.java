package hr.fer.zemris.java.custom.collections;

/**
 * This class represents a stack which stores objects. The stack uses 
 * an array backed collection to store its objects. It
 * supports basic operations typical of a stack.
 * 
 * @author Tin Reiter
 * @version 1.1
 * @param <T> the type of objects stored
 *            in this stack.
 */
public class ObjectStack<T> {
	
	private ArrayIndexedCollection<T> array;
	
	/**
	 * This constructor initializes an empty stack.
	 */
	public ObjectStack() {
		this.array = new ArrayIndexedCollection<>();
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
	public void push(T value) {
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
	public T pop() {
		if (array.size() == 0) {
			throw new EmptyStackException();
		}
		T object = array.get(array.size() - 1);
		array.remove(array.size() - 1);
		return object;
	}
	
	/**
	 * Returns the last object added to the stack. The
	 * object is not removed from the stack.
	 * 
	 * @return the last object added to the stack.
	 * @throws EmptyStackException if the stack is
	 *                             empty.
	 */
	public T peek() {
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
