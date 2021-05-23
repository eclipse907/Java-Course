package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class represents a map collection that uses a hash table
 * to store its entries. The hash table uses linked lists to solve
 * collisions. When the hash table load factor reaches 75% the table
 * is automatically resized to twice the previous size. This class 
 * implements the Iterable interface.
 * 
 * @author Tin Reiter
 * @version 1.0
 * @param <K> the key type.
 * @param <V> the value type.
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K,V>> {
	
	private TableEntry<K, V>[] table;
	private int size;
	private long modificationCount;

	/**
	 * This class represents an entry in a hash map that uses
	 * linked lists to solve collisions.
	 * 
	 * @author Tin Reiter
	 * @version 1.0
	 * @param <K> the key type.
	 * @param <V> the value type.
	 */
	public static class TableEntry<K,V> {
		
		private K key;
		private V value;
		private TableEntry<K, V> next;
		
		/**
		 * Creates a new map entry with the given key
		 * and value.
		 * 
		 * @param key the key of the map entry.
		 * @param value the value of the map entry.
		 * @param next reference to the next node in the
		 *             list.
		 * @throws NullPointerException if the given key is
		 *                              null.
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			if (key == null) {
				throw new NullPointerException();
			}
			this.key = key;
			this.value = value;
			this.next = next;
		}
		
		/**
		 * Returns the value of the map entry.
		 * 
		 * @return the value of the map entry.
		 */
		public V getValue() {
			return value;
		}
		
		/**
		 * Sets the value of the map entry.
		 * 
		 * @param value the new value of the 
		 *              map entry.
		 */
		public void setValue(V value) {
			this.value = value;
		}
		
		/**
		 * Returns the key of the map entry.
		 * 
		 * @return the key of the map entry.
		 */
		public K getKey() {
			return key;
		}
		
		/**
		 * Returns a string representation of the map entry.
		 * 
		 * @return a string representation of the map entry.
		 */
		@Override
		public String toString() {
			StringBuilder toString = new StringBuilder();
			toString.append(key.toString() + "=");
			if (value == null) {
				toString.append("null");
			} else {
				toString.append(value.toString());
			}
			return toString.toString();
		}
		
	}
	
	/**
	 * Creates a new empty hash map with an initial hash table
	 * capacity of 16.
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable() {
		this.table = (TableEntry<K, V>[])new TableEntry[16];
	}
	
	/**
	 * Creates a new empty hash map with the given hash table
	 * capacity if the given capacity is a power of 2. If the 
	 * given capacity is not a power of 2 the nearest higher 
	 * power of 2 is taken as the hash table capacity.
	 * 
	 * @param capacity the capacity of the hash table.
	 * @throws IllegalArgumentException if the given capacity
	 *                                  is less than 1.
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if (capacity < 1) {
			throw new IllegalArgumentException();
		}
		this.table = (TableEntry<K, V>[])new TableEntry[(int)Math.pow(2, Math.ceil(Math.log(capacity) / Math.log(2)))];
	}
	
	/**
	 * Creates a new map entry with the given key and value
	 * and stores it inside this hash map. If the given key
	 * already exists the previous value is overwritten with
	 * the given value. This method has an average complexity
	 * of O(1) if the underlying hash table load factor is not
	 * too high.
	 * 
	 * @param key the key of the map entry.
	 * @param value the value of the map entry.
	 * @throws NullPointerException if the given key is
	 *                              null. 
	 */
	public void put(K key, V value) {
		if (key == null) {
			throw new NullPointerException();
		}
		int index = Math.abs(key.hashCode()) % table.length;
		if (table[index] == null) {
			table[index] = new TableEntry<>(key, value, null);
			size++;
			modificationCount++;
		} else {
			TableEntry<K, V> currentEntry = table[index];
			while (true) {
				if (key.equals(currentEntry.getKey())) {
					currentEntry.setValue(value);
					return;
				}
				if (currentEntry.next == null) {
					break;
				}
				currentEntry = currentEntry.next;
			}
			currentEntry.next = new TableEntry<>(key, value, null);
			size++;
			modificationCount++;
		}
		rehashTable();
	}
	
	/**
	 * Returns the value of the map entry associated with this key
	 * if the key is present in this map, null otherwise. This method 
	 * has an average complexity of O(1) if the underlying hash table 
	 * load factor is not too high.
	 * 
	 * @param key the key of the value to return.
	 * @return the value associated with this key
	 *         if the key exists, null otherwise.
	 */
	public V get(Object key) {
		if (key == null) {
			return null;
		}
		int index = Math.abs(key.hashCode()) % table.length;
		if (table[index] == null) {
			return null;
		} else {
			TableEntry<K, V> currentEntry = table[index];
			do {
				if (key.equals(currentEntry.getKey())) {
					return currentEntry.getValue();
				}
				currentEntry = currentEntry.next;
			} while (currentEntry != null);
			return null;
		}
	}
	
	/**
	 * Returns the number of entries stored in this
	 * hash map.
	 * 
	 * @return the number of entries stored in this
	 *         hash map.
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Checks if the given key is present in this hash
	 * map. This method has an average complexity of O(1) 
	 * if the underlying hash table load factor is not
	 * too high.
	 * 
	 * @param key the key to check.
	 * @return true if the given key is present in
	 *         this hash map, false otherwise.
	 */
	public boolean containsKey(Object key) {
		if (key == null) {
			return false;
		}
		int index = Math.abs(key.hashCode()) % table.length;
		if (table[index] == null) {
			return false;
		} else {
			TableEntry<K, V> currentEntry = table[index];
			do {
				if (key.equals(currentEntry.getKey())) {
					return true;
				}
				currentEntry = currentEntry.next;
			} while (currentEntry != null);
			return false;
		}
	}
	
	/**
	 * Checks if the given value is present in this hash
	 * map. This method has an average complexity of O(n).
	 * 
	 * @param value the value to check.
	 * @return true if the given value is present in this
	 *         hash map, false otherwise.
	 */
	public boolean containsValue(Object value) {
		TableEntry<K, V> currentEntry;
		for (int i = 0; i < table.length; i++) {
			currentEntry = table[i];
			while (currentEntry != null) {
				if (
						(value != null                         && 
						value.equals(currentEntry.getValue())) || 
						(value == null                         && 
						currentEntry.getValue() == null)
				   ) {
					return true;
				}
				currentEntry = currentEntry.next;
			}
		}
		return false;
	}
	
	/**
	 * Deletes the map entry associated with this key if
	 * it exists in the map, does nothing otherwise. This 
	 * method has an average complexity of O(1) if the 
	 * underlying hash table load factor is not too high.
	 * 
	 * @param key the key of the map entry to delete.
	 */
	public void remove(Object key) {
		if (key == null) {
			return;
		}
		int index = Math.abs(key.hashCode()) % table.length;
		if (table[index] == null) {
			return;
		} else if (key.equals(table[index].getKey())) {
			table[index] = table[index].next;
			size--;
			modificationCount++;
		} else {
			TableEntry<K, V> currentEntry = table[index];
			while (currentEntry.next != null) {
				if (key.equals(currentEntry.next.getKey())) {
					currentEntry.next = currentEntry.next.next;
					size--;
					modificationCount++;
					return;
				}
				currentEntry = currentEntry.next;
			}
		}
	}
	
	/**
	 * Checks if the hash map is empty.
	 * 
	 * @return true if the hash map is
	 *         empty, false otherwise.
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * Deletes all entries in this hash map. The hash
	 * table capacity is not changed.
	 */
	public void clear() {
		for (int i = 0; i < table.length; i++) {
			table[i] = null;
		}
		size = 0;
		modificationCount++;
	}
	
	/**
	 * Creates a new hash table with double the capacity of the
	 * previous one and copies all entries into it if the hash
	 * table load factor reaches 75%.
	 */
	@SuppressWarnings("unchecked")
	private void rehashTable() {
		if (Math.round(((double)size / table.length) * 100.0) / 100.0 < 0.75) {
			return;
		}
		TableEntry<K, V>[] newTable = (TableEntry<K, V>[])new TableEntry[2 * table.length];
		TableEntry<K, V> currentEntry, nextEntry, lastEntry;
		for (int i = 0, newIndex; i < table.length; i++) {
			currentEntry = table[i];
			while (currentEntry != null) {
				newIndex = Math.abs(currentEntry.getKey().hashCode()) % newTable.length;
				if (newTable[newIndex] == null) {
					newTable[newIndex] = currentEntry;
				} else {
					lastEntry = newTable[newIndex];
					while (lastEntry.next != null) {
						lastEntry = lastEntry.next;
					}
					lastEntry.next = currentEntry;
				}
				nextEntry = currentEntry.next;
				currentEntry.next = null;
				currentEntry = nextEntry;
			}
		}
		table = newTable;
		modificationCount++;
	}
	
	/**
	 * Returns a string representation of this hash
	 * map.
	 * 
	 * @return a string representation of this hash
	 *         map.
	 */
	@Override
	public String toString() {
		StringBuilder toString = new StringBuilder();
		toString.append("[");
		TableEntry<K, V> currentEntry;
		for (int i = 0; i < table.length; i++) {
			currentEntry = table[i];
			while (currentEntry != null) {
				toString.append(currentEntry.toString() + ", ");
				currentEntry = currentEntry.next;
			}
		}
		if (size >= 1) {
			return toString.replace(toString.length() - 2, toString.length(), "]").toString();
		} else {
			return toString.append("]").toString();
		}
	}
	
	/**
	 * This class represents an iterator that iterates over a hash map.
	 * This class implements the Iterator interface.
	 * 
	 * @author Tin Reiter
	 * @version 1.0
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K,V>> {
		
		private int currentSlot;
		private TableEntry<K, V> nextEntryInSlot;
		private TableEntry<K, V> lastReturned;
		private boolean hasCalledRemove;
		private long savedModificationCount;
		
		/**
		 * Creates a new iterator that iterates over this hash map.
		 * 
		 * @param modificationCount the number of modifications done
		 *                          to this hash map.
		 */
		public IteratorImpl(long modificationCount) {
			this.nextEntryInSlot = table[currentSlot];
			this.hasCalledRemove = false;
			this.savedModificationCount = modificationCount;
		}
		
		/**
		 * {@inheritDoc}
		 * 
		 * @throws ConcurrentModificationException if the collection iterated on
		 *                                         has been modified.
		 */
		@Override
		public boolean hasNext() {
			if (savedModificationCount != modificationCount) {
				throw new ConcurrentModificationException();
			}
			if (nextEntryInSlot != null) {
				return true;
			} else {
				for (int i = currentSlot + 1; i < table.length; i++) {
					if (table[i] != null) {
						return true;
					}
				}
				return false;
			}
		} 
		
		/**
		 * {@inheritDoc}
		 * 
		 * @throws ConcurrentModificationException if the collection iterated on
		 *                                         has been modified.
		 * @throws NoSuchElementException if the iteration doesn't have any more
		 *                                elements. 
		 */
		@Override
		public SimpleHashtable.TableEntry<K,V> next() {
			if (savedModificationCount != modificationCount) {
				throw new ConcurrentModificationException();
			}
			while (nextEntryInSlot == null) {
				if (currentSlot + 1 >= table.length) {
					throw new NoSuchElementException();
				}
				currentSlot++;
				nextEntryInSlot = table[currentSlot];
			}
			lastReturned = nextEntryInSlot;
			hasCalledRemove = false;
			nextEntryInSlot = nextEntryInSlot.next;
			return lastReturned;
		}
		
		/**
		 * {@inheritDoc}
		 * 
		 * @throws ConcurrentModificationException if the collection iterated on
		 *                                         has been modified.
		 * @throws IllegalStateException if this method is called before the next
		 *                               method has been called at least once or
		 *                               if this method has already been called
		 *                               for the last next method call.
		 */
		@Override
		public void remove() {
			if (savedModificationCount != modificationCount) {
				throw new ConcurrentModificationException();
			}
			if (lastReturned == null || hasCalledRemove) {
				throw new IllegalStateException();
			}
			SimpleHashtable.this.remove(lastReturned.getKey());
			savedModificationCount++;
			hasCalledRemove = true;
		}
		
	} 
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<SimpleHashtable.TableEntry<K,V>> iterator() {
		return new IteratorImpl(modificationCount);
	}
	
}
