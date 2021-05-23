package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DictionaryTest {

	@Test
	void testIsEmpty() {
		Dictionary<String, String> dict = new Dictionary<>();
		assertTrue(dict.isEmpty());
	}
	
	@Test
	void testSize() {
		Dictionary<String, Integer> dict = new Dictionary<>();
		dict.put("broj1", 1);
		dict.put("broj2", 2);
		dict.put("broj3", 3);
		assertEquals(3, dict.size());
	}
	
	@Test
	void testClear() {
		Dictionary<String, Integer> dict = new Dictionary<>();
		dict.put("broj1", 1);
		dict.put("broj2", 2);
		dict.put("broj3", 3);
		dict.clear();
		assertTrue(dict.isEmpty());
	}
	
	@Test
	void testPutAndGet() {
		Dictionary<Integer, String> dict = new Dictionary<>();
		dict.put(3, "tri");
		dict.put(5, "pet");
		dict.put(7, "sedam");
		dict.put(2, "dva");
		assertEquals(4, dict.size());
		assertThrows(NullPointerException.class, () -> {
			dict.put(null, "null");
		});
		dict.put(7, "7");
		assertEquals("7", dict.get(7));
		assertEquals(null, dict.get(25));
	}

}
