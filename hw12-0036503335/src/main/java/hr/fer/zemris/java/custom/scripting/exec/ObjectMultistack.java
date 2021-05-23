package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a special map that pairs each key with a stack.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class ObjectMultistack {
	
	private Map<String, MultistackEntry> map;

	/**
	 * This class represents an entry in the stack.
	 * 
	 * @author Tin Reiter
	 * @version 1.0
	 */
	private static class MultistackEntry {
		
		private ValueWrapper value;
		private MultistackEntry nextEntry;
		
	}
	
	/**
	 * Creates a new ObjectMultistack.
	 */
	public ObjectMultistack() {
		map = new HashMap<>();
	}
	
	/**
	 * Adds the given value to the stack associated with the
	 * key given.
	 * 
	 * @param keyName the key associated with the desired stack.
	 * @param valueWrapper the value to add to the desired stack.
	 */
	public void push(String keyName, ValueWrapper valueWrapper) {
		MultistackEntry entry = new MultistackEntry();
		entry.value = valueWrapper;
		if (map.containsKey(keyName)) {
			entry.nextEntry = map.get(keyName);
			map.put(keyName, entry);
		} else {
			map.put(keyName, entry);
		}
	}
	
	/**
	 * Removes the last value pushed to the stack associated with
	 * the key given and returns it's value.
	 * 
	 * @param keyName the key associated with the desired stack.
	 * @return the last value pushed to the stack associated with
	 *         given key.
	 * @throws EmptyStackException if the stack associated with the
	 *                             given key is empty.
	 */
	public ValueWrapper pop(String keyName) {
		if (isEmpty(keyName)) {
			throw new EmptyStackException("The stack of the given key is empty.");
		}
		MultistackEntry entry = map.get(keyName);
		if (entry.nextEntry != null) {
			map.put(keyName, entry.nextEntry);
		} else {
			map.remove(keyName);
		}
		return entry.value;
	}
	
	/**
	 * Returns the last value pushed to the stack associated with the given
	 * key. The value is not removed from the stack.
	 * 
	 * @param keyName the key associated with the desired stack.
	 * @return the last value pushed to the stack associated with
	 *         given key.
	 * @throws EmptyStackException if the stack associated with the
	 *                             given key is empty.
	 */
	public ValueWrapper peek(String keyName) {
		if (isEmpty(keyName)) {
			throw new EmptyStackException("The stack of the given key is empty.");
		}
		return map.get(keyName).value;
	}
	
	/**
	 * Checks if the stack associated with the given key
	 * is empty.
	 * 
	 * @param keyName the key associated with the desired stack.
	 * @return true if the stack is empty, false otherwise.
	 */
	public boolean isEmpty(String keyName) {
		if (map.containsKey(keyName)) {
			return false;
		} else {
			return true;
		}
	}
	
}
