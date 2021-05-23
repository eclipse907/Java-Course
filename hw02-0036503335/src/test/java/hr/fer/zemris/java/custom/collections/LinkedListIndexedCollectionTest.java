package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LinkedListIndexedCollectionTest {

	@Test
	void isCollectionEmptyTest() {
		Collection col = new LinkedListIndexedCollection();
		assertTrue(col.isEmpty());
	}
	
	@Test
	void collectionSizeTest() {
		Collection col = new LinkedListIndexedCollection();
		col.add(Integer.valueOf(3));
		col.add(Integer.valueOf(8));
		col.add(Integer.valueOf(7));
		assertEquals(3, col.size());
	}
	
	@Test
	void collectionAddTest() {
		Collection col = new LinkedListIndexedCollection();
		col.add(Integer.valueOf(8));
		assertEquals(1, col.size());
	}
	
	@Test
	void collectionContainsTest() {
		Collection col = new LinkedListIndexedCollection();
		col.add(Integer.valueOf(4));
		assertTrue(col.contains(Integer.valueOf(4)));
	}
	
	@Test
	void collectionRemoveTest() {
		Collection col = new LinkedListIndexedCollection();
		col.add(Integer.valueOf(4));
		col.add(Integer.valueOf(7));
		col.remove(Integer.valueOf(4));
		assertFalse(col.contains(Integer.valueOf(4)));
	}
	
	@Test
	void collectionToArrayTest() {
		Collection col = new LinkedListIndexedCollection();
		col.add(Integer.valueOf(4));
		col.add(Integer.valueOf(7));
		assertEquals(2, col.toArray().length);
	}
	
	@Test
	void collectionForEachAndAddAllTest() {
		Collection col1 = new LinkedListIndexedCollection();
		Collection col2 = new LinkedListIndexedCollection();
		col1.add(Integer.valueOf(3));
		col1.add(Integer.valueOf(5));
		col2.add(Integer.valueOf(7));
		col2.add(Integer.valueOf(14));
		col2.add(Integer.valueOf(1));
		col2.addAll(col1);
		assertEquals(5, col2.size());
	}
	
	@Test
	void collectionClearTest() {
		Collection col = new LinkedListIndexedCollection();
		col.add(Integer.valueOf(4));
		col.add(Integer.valueOf(7));
		col.clear();
		assertTrue(col.isEmpty());
	}
	
	@Test
	void collectionGetTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add(Integer.valueOf(3));
		col.add(Integer.valueOf(5));
		col.add(Integer.valueOf(7));
		col.add(Integer.valueOf(14));
		col.add(Integer.valueOf(1));
		assertEquals(7, col.get(2));
	}
	
	@Test
	void collectionInsertTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add(Integer.valueOf(3));
		col.add(Integer.valueOf(5));
		col.add(Integer.valueOf(7));
		col.add(Integer.valueOf(14));
		col.add(Integer.valueOf(1));
		col.insert(23, 3);
		assertEquals(23, col.get(3));
	}
	
	@Test
	void collectionIndexOfTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add(Integer.valueOf(3));
		col.add(Integer.valueOf(5));
		col.add(Integer.valueOf(7));
		col.add(Integer.valueOf(14));
		col.add(Integer.valueOf(5));
		assertEquals(1, col.indexOf(Integer.valueOf(5)));
	}
	
	@Test
	void collectionRemoveIndexTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add(Integer.valueOf(3));
		col.add(Integer.valueOf(5));
		col.add(Integer.valueOf(7));
		col.add(Integer.valueOf(14));
		col.add(Integer.valueOf(1));
		col.remove(2);
		assertFalse(col.contains(Integer.valueOf(7)));
	}
	
	@Test
	void collectionConstructorsTest() {
		LinkedListIndexedCollection col1 = new LinkedListIndexedCollection();
		col1.add(Integer.valueOf(3));
		col1.add(Integer.valueOf(5));
		col1.add(Integer.valueOf(7));
		col1.add(Integer.valueOf(14));
		col1.add(Integer.valueOf(1));
		LinkedListIndexedCollection col2 = new LinkedListIndexedCollection(col1);
		col2.add(Integer.valueOf(25));
		col2.add(Integer.valueOf(35));
		assertEquals(5, col1.size());
		assertEquals(7, col2.size());
	}

}
