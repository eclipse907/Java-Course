package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;


class ArrayIndexedCollectionTest {

	@Test
	void isCollectionEmptyTest() {
		Collection<String> col = new ArrayIndexedCollection<>();
		assertTrue(col.isEmpty());
	}
	
	@Test
	void collectionSizeTest() {
		Collection<Integer> col = new ArrayIndexedCollection<>();
		col.add(Integer.valueOf(3));
		col.add(Integer.valueOf(8));
		col.add(Integer.valueOf(7));
		assertEquals(3, col.size());
	}
	
	@Test
	void collectionAddTest() {
		Collection<Integer> col = new ArrayIndexedCollection<>();
		col.add(Integer.valueOf(8));
		assertEquals(1, col.size());
	}
	
	@Test
	void collectionContainsTest() {
		Collection<Integer> col = new ArrayIndexedCollection<>();
		col.add(Integer.valueOf(4));
		assertTrue(col.contains(Integer.valueOf(4)));
	}
	
	@Test
	void collectionRemoveTest() {
		Collection<Integer> col = new ArrayIndexedCollection<>();
		col.add(Integer.valueOf(4));
		col.add(Integer.valueOf(7));
		col.remove(Integer.valueOf(4));
		assertFalse(col.contains(Integer.valueOf(4)));
	}
	
	@Test
	void collectionToArrayTest() {
		Collection<Integer> col = new ArrayIndexedCollection<>();
		col.add(Integer.valueOf(3));
		col.add(Integer.valueOf(5));
		col.add(Integer.valueOf(7));
		col.add(Integer.valueOf(14));
		col.add(Integer.valueOf(1));
		assertEquals(5, col.toArray().length);
	}
	
	@Test
	void collectionForEachAndAddAllTest() {
		Collection<Integer> col1 = new ArrayIndexedCollection<>();
		Collection<Integer> col2 = new ArrayIndexedCollection<>();
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
		Collection<Integer> col = new ArrayIndexedCollection<>();
		col.add(Integer.valueOf(4));
		col.add(Integer.valueOf(7));
		col.clear();
		assertTrue(col.isEmpty());
	}
	
	@Test
	void collectionGetTest() {
		List<Integer> col = new ArrayIndexedCollection<>();
		col.add(Integer.valueOf(3));
		col.add(Integer.valueOf(5));
		col.add(Integer.valueOf(7));
		col.add(Integer.valueOf(14));
		col.add(Integer.valueOf(1));
		assertEquals(7, col.get(2));
	}
	
	@Test
	void collectionInsertTest() {
		List<Integer> col = new ArrayIndexedCollection<>(3);
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
		List<Integer> col = new ArrayIndexedCollection<>();
		col.add(Integer.valueOf(3));
		col.add(Integer.valueOf(5));
		col.add(Integer.valueOf(7));
		col.add(Integer.valueOf(14));
		col.add(Integer.valueOf(5));
		assertEquals(1, col.indexOf(Integer.valueOf(5)));
	}
	
	@Test
	void collectionRemoveIndexTest() {
		List<Integer> col = new ArrayIndexedCollection<>();
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
		List<Integer> col1 = new ArrayIndexedCollection<>();
		col1.add(Integer.valueOf(3));
		col1.add(Integer.valueOf(5));
		col1.add(Integer.valueOf(7));
		col1.add(Integer.valueOf(14));
		col1.add(Integer.valueOf(1));
		List<Integer> col2 = new ArrayIndexedCollection<>(col1);
		col2.add(Integer.valueOf(25));
		col2.add(Integer.valueOf(35));
		List<Integer> col3 = new ArrayIndexedCollection<>(col2, 5);
		col3.add(Integer.valueOf(25));
		col3.add(Integer.valueOf(35));
		assertEquals(5, col1.size());
		assertEquals(7, col2.size());
		assertEquals(9, col3.size());
	}
	
	@Test
	void collectionIteratorTest() {
		List<Integer> col = new ArrayIndexedCollection<>();
		col.add(Integer.valueOf(3));
		col.add(Integer.valueOf(5));
		col.add(Integer.valueOf(7));
		col.add(Integer.valueOf(14));
		col.add(Integer.valueOf(1));
		ElementsGetter<Integer> iter = col.createElementsGetter();
		assertEquals(3, iter.getNextElement());
		assertTrue(iter.hasNextElement());
		col.remove(Integer.valueOf(14));
		assertThrows(ConcurrentModificationException.class, () -> {
			iter.getNextElement();
		});
		assertThrows(ConcurrentModificationException.class, () -> {
			iter.hasNextElement();
		});
		ElementsGetter<Integer> iter2 = col.createElementsGetter();
		assertEquals(3, iter2.getNextElement());
		assertEquals(5, iter2.getNextElement());
		assertEquals(7, iter2.getNextElement());
		assertEquals(1, iter2.getNextElement());
		assertThrows(NoSuchElementException.class, () -> {
			iter2.getNextElement();
		});
		assertFalse(iter2.hasNextElement());
	}
	
	@Test
	void collectionToStringTest() {
		List<Integer> col = new ArrayIndexedCollection<>();
		col.add(Integer.valueOf(3));
		col.add(Integer.valueOf(5));
		col.add(Integer.valueOf(7));
		col.add(Integer.valueOf(14));
		col.add(Integer.valueOf(1));
		assertEquals("[3, 5, 7, 14, 1]", col.toString());
	}
}
