package hr.fer.zemris.java.custom.collections;

/**
 * This class represents a custom implementation of a map
 * collection. It uses an ArrayIndexedCollection to store
 * its entries. Keys and values added to this map must
 * implement the equals method.
 * 
 * @author Tin Reiter
 * @version 1.0
 * @param <K> the key type.
 * @param <V> the value type.
 */
public class Dictionary<K, V> {
	
	private ArrayIndexedCollection<Entry<Object, Object>> collection;
	
	/**
	 * This class represents an entry in a map collection.
	 * 
	 * @author Tin Reiter
	 * @version 1.0
	 * @param <K> the key type.
	 * @param <V> the value type.
	 */
	private static class Entry<K, V> {
		
		private K key;
		private V value;
		
		/**
		 * Creates a new entry with the given key
		 * and value.
		 * 
		 * @param key the key of the entry
		 * @param value the value of the entry.
		 * @throws NullPointerException if the given
		 *                              key is null. 
		 */
		public Entry(K key, V value) {
			if (key == null) {
				throw new NullPointerException();
			}
			this.key = key;
			this.value = value;
		}
		
		/**
		 * Checks if the given object is equal to this
		 * entry by comparing the keys of the entries.
		 * 
		 * @return true if the given object is equal,
		 *         false otherwise.
		 */
		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Entry<?, ?>)) {
				return false;
			}
			Entry<?, ?> otherEntry = (Entry<?, ?>)obj;
			if (key.equals(otherEntry.key)) {
				return true;
			} else {
				return false;
			}
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public int hashCode() {
			return key.hashCode();
		}
		
	}
	
	/**
	 * Creates a new empty map collection.
	 */
	public Dictionary() {
		this.collection = new ArrayIndexedCollection<>();
	}
	
	/**
	 * Checks if the map is empty.
	 * 
	 * @return true if the map is empty,
	 *         false otherwise. 
	 */
	public boolean isEmpty() {
		return collection.isEmpty();
	}
	
	/**
	 * Returns the number of entries in this
	 * map.
	 * 
	 * @return the number of entries in this
	 *         map.
	 */
	public int size() {
		return collection.size();
	}
	
	/**
	 * Deletes all entries in this map.
	 */
	public void clear() {
		collection.clear();
	}
	
	/**
	 * Creates a new entry with the given key and value
	 * and places it in the map. If the given entry exists
	 * in the map its value is overwritten with the given
	 * value.
	 * 
	 * @param key the key of the entry.
	 * @param value the value of the entry.
	 * @throws NullPointerException if the given
	 *                              key is null.
	 */
	public void put(K key, V value) {
		Entry<Object, Object> entry = new Entry<>(key, value);
		int index = collection.indexOf(entry);
		if (index == -1) {
			collection.add(entry);
		} else {
			collection.get(index).value = value;
		}
	}
	
	/**
	 * Returns the value of the entry with the given
	 * key if it exists, null otherwise.
	 * 
	 * @param key the key of the entry.
	 * @return the value of the entry with the given
	 *         key if it exists, null otherwise.
	 * @throws NullPointerException if the given
	 *                              key is null.
	 */
	@SuppressWarnings("unchecked")
	public V get(Object key) {
		int index = collection.indexOf(new Entry<>(key, null));
		if (index == -1) {
			return null;
		} else {
			return (V)collection.get(index).value;
		}
	}
	
}
