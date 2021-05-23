package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

class SimpleHashtableTest {

	@Test
	void testPut() {
		SimpleHashtable<String, Integer> map = new SimpleHashtable<>(1);
		map.put("Ana", 3);
		map.put("Ivana", 1);
		map.put("Filip", 5);
		assertEquals(3, map.size());
		assertThrows(NullPointerException.class, () -> {
			map.put(null, 3);
		});
	}
	
	@Test
	void testGet() {
		SimpleHashtable<String, Integer> map = new SimpleHashtable<>(2);;
		map.put("Ana", 3);
		map.put("Ivana", 1);
		map.put("Filip", 5);
		assertEquals(5, map.get("Filip"));
		assertEquals(1, map.get("Ivana"));
		assertEquals(3, map.get("Ana"));
		assertEquals(null, map.get("Mirko"));
		assertEquals(null, map.get(null));
		map.put("Ivan", null);
		assertEquals(null, map.get("Ivan"));
	}
	
	@Test
	void testPutSameKey() {
		SimpleHashtable<String, Integer> map = new SimpleHashtable<>(5);
		map.put("Ana", 3);
		map.put("Ivana", 1);
		map.put("Filip", 5);
		assertEquals(3, map.get("Ana"));
		map.put("Ana", 5);
		assertEquals(5, map.get("Ana"));
	}

	@Test
	void testContainsKey() {
		SimpleHashtable<String, Integer> map = new SimpleHashtable<>(25);
		map.put("Ana", 3);
		map.put("Ivana", 1);
		map.put("Filip", 5);
		map.put("Ivan", null);
		assertTrue(map.containsKey("Ana"));
		assertTrue(map.containsKey("Ivana"));
		assertTrue(map.containsKey("Filip"));
		assertTrue(map.containsKey("Ivan"));
		assertFalse(map.containsKey("bla"));
		assertFalse(map.containsKey(null));
		assertFalse(map.containsKey(123));
		assertFalse(map.containsKey(5.1232));
	}
	
	@Test
	void testContainsValue() {
		SimpleHashtable<String, Integer> map = new SimpleHashtable<>(3);
		map.put("Ana", 3);
		map.put("Ivana", 1);
		map.put("Filip", 5);
		map.put("Ivan", null);
		assertTrue(map.containsValue(3));
		assertTrue(map.containsValue(1));
		assertTrue(map.containsValue(5));
		assertTrue(map.containsValue(null));
		assertFalse(map.containsKey(24));
		assertFalse(map.containsKey(128));
		assertFalse(map.containsKey("bla"));
	}
	
	@Test
	void testRemove() {
		SimpleHashtable<String, Integer> map = new SimpleHashtable<>(12);
		map.put("Ana", 3);
		map.put("Ivana", 1);
		map.put("Filip", 5);
		map.remove("Ana");
		assertFalse(map.containsKey("Ana"));
		assertFalse(map.containsValue(3));
		assertEquals(2, map.size());
		map.remove(null);
		assertEquals(2, map.size());
		map.remove("Ante");
		assertEquals(2, map.size());
	}
	
	@Test
	void testIsEmpty() {
		SimpleHashtable<String, Integer> map = new SimpleHashtable<>();
		assertTrue(map.isEmpty());
		map.put("Ana", 3);
		map.put("Ivana", 1);
		map.put("Filip", 5);
		map.remove("Ana");
		map.remove("Ivana");
		map.remove("Filip");
		assertTrue(map.isEmpty());
	}
	
	@Test
	void testToString() {
		SimpleHashtable<String, Integer> map = new SimpleHashtable<>(3);
		map.put("Ivana", 2);
		map.put("Ante", 2);
		map.put("Jasna", 2);
		map.put("Kristina", 5);
		map.put("Ivana", 5);
		assertEquals("[Ante=2, Ivana=5, Jasna=2, Kristina=5]", map.toString());
	}
	
	@Test
	void testIterator() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		StringBuilder output = new StringBuilder();
		for(SimpleHashtable.TableEntry<String,Integer> pair : examMarks) {
			output.append(String.format("%s => %d\n", pair.getKey(), pair.getValue()));
		}
		assertEquals("Ante => 2\nIvana => 5\nJasna => 2\nKristina => 5\n", output.toString());
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
			if(pair.getKey().equals("Ivana")) {
				iter.remove();  // sam iterator kontrolirano uklanja trenutni element
			}
		}
		assertEquals(3, examMarks.size());
		assertFalse(examMarks.containsKey("Ivana"));
		assertThrows(IllegalStateException.class, () -> {
			SimpleHashtable<String, Integer> examMarks1 = new SimpleHashtable<>(2);
			examMarks1.put("Ivana", 2);
			examMarks1.put("Ante", 2);
			examMarks1.put("Jasna", 2);
			examMarks1.put("Kristina", 5);
			examMarks1.put("Ivana", 5);
			Iterator<SimpleHashtable.TableEntry<String,Integer>> iter1 = examMarks1.iterator();
			while(iter1.hasNext()) {
				SimpleHashtable.TableEntry<String,Integer> pair = iter1.next();
				if(pair.getKey().equals("Ivana")) {
					iter1.remove(); 
					iter1.remove();
				}
			}
		});
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter2 = examMarks.iterator();
		examMarks.remove("Ante");
		assertThrows(ConcurrentModificationException.class , () -> {
			iter2.hasNext();
		});
		assertThrows(ConcurrentModificationException.class , () -> {
			iter2.next();
		});
		assertThrows(ConcurrentModificationException.class , () -> {
			iter2.remove();
		});
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter3 = examMarks.iterator();
		output = new StringBuilder();
		while(iter3.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter3.next();
			output.append(String.format("%s => %d\n", pair.getKey(), pair.getValue()));
			iter3.remove();
		}
		assertEquals("Ante => 2\nJasna => 2\nKristina => 5\nIvana => 5\n", output.toString());
		assertTrue(examMarks.isEmpty());
	}
	
}
