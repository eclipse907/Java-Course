package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FieldValueGettersTest {

	@Test
	void testGetters() {
		StudentRecord record = new StudentRecord("0036514547", "Mirko", "Mirkulić", 4);
		assertEquals("0036514547", FieldValueGetters.JMBAG.get(record));
		assertEquals("Mirko", FieldValueGetters.FIRST_NAME.get(record));
		assertEquals("Mirkulić", FieldValueGetters.LAST_NAME.get(record));
	}

}
