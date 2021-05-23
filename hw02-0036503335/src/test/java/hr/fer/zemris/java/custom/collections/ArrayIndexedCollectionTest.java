package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


class ArrayIndexedCollectionTest {

	@Test
	void isCollectionEmptyTest() {
		Collection col = new ArrayIndexedCollection();
		assertTrue(col.isEmpty());
	}
	
	@Test
	void collectionSizeTest() {
		Collection col = new ArrayIndexedCollection();
		col.add(Integer.valueOf(3));
		col.add(Integer.valueOf(8));
		col.add(Integer.valueOf(7));
		assertEquals(3, col.size());
	}
	
	@Test
	void collectionAddTest() {
		Collection col = new ArrayIndexedCollection();
		col.add(Integer.valueOf(8));
		assertEquals(1, col.size());
	}
	
	@Test
	void collectionContainsTest() {
		Collection col = new ArrayIndexedCollection();
		col.add(Integer.valueOf(4));
		assertTrue(col.contains(Integer.valueOf(4)));
	}
	
	@Test
	void collectionRemoveTest() {
		Collection col = new ArrayIndexedCollection();
		col.add(Integer.valueOf(4));
		col.add(Integer.valueOf(7));
		col.remove(Integer.valueOf(4));
		assertFalse(col.contains(Integer.valueOf(4)));
	}
	
	@Test
	void collectionToArrayTest() {
		Collection col = new ArrayIndexedCollection();
		Object[] ob = new Object[16];
		assertEquals(ob.length, col.toArray().length);
	}
	
	@Test
	void collectionForEachAndAddAllTest() {
		Collection col1 = new ArrayIndexedCollection();
		Collection col2 = new ArrayIndexedCollection();
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
		Collection col = new ArrayIndexedCollection();
		col.add(Integer.valueOf(4));
		col.add(Integer.valueOf(7));
		col.clear();
		assertTrue(col.isEmpty());
	}
	
	@Test
	void collectionGetTest() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add(Integer.valueOf(3));
		col.add(Integer.valueOf(5));
		col.add(Integer.valueOf(7));
		col.add(Integer.valueOf(14));
		col.add(Integer.valueOf(1));
		assertEquals(7, col.get(2));
	}
	
	@Test
	void collectionInsertTest() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(3);
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
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add(Integer.valueOf(3));
		col.add(Integer.valueOf(5));
		col.add(Integer.valueOf(7));
		col.add(Integer.valueOf(14));
		col.add(Integer.valueOf(5));
		assertEquals(1, col.indexOf(Integer.valueOf(5)));
	}
	
	@Test
	void collectionRemoveIndexTest() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
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
		ArrayIndexedCollection col1 = new ArrayIndexedCollection();
		col1.add(Integer.valueOf(3));
		col1.add(Integer.valueOf(5));
		col1.add(Integer.valueOf(7));
		col1.add(Integer.valueOf(14));
		col1.add(Integer.valueOf(1));
		ArrayIndexedCollection col2 = new ArrayIndexedCollection(col1);
		col2.add(Integer.valueOf(25));
		col2.add(Integer.valueOf(35));
		ArrayIndexedCollection col3 = new ArrayIndexedCollection(col2, 5);
		col3.add(Integer.valueOf(25));
		col3.add(Integer.valueOf(35));
		assertEquals(5, col1.size());
		assertEquals(7, col2.size());
		assertEquals(9, col3.size());
	}
}
