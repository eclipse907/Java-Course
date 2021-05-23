package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ValueWrapperTest {

	@Test
	void testAddBothNull() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue());
		assertEquals(0, (Integer)v1.getValue());
		assertEquals(null, v2.getValue());
	}
	
	@Test
	void testAddOneDoubleOneInteger() {
		ValueWrapper v3 = new ValueWrapper("1.2E1");
		ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));
		v3.add(v4.getValue());
		assertEquals(13.0, (Double)v3.getValue());
		assertEquals(1, (Integer)v4.getValue());
	}
	
	@Test
	void testAddTwoIntegers() {
		ValueWrapper v5 = new ValueWrapper("12");
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));
		v5.add(v6.getValue());
		assertEquals(13, (Integer)v5.getValue());
		assertEquals(1, (Integer)v6.getValue());
	}
	
	@Test
	void testAddIntegerAndString() {
		ValueWrapper v7 = new ValueWrapper("Ankica");
		ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));
		assertThrows(RuntimeException.class, () -> {
			v7.add(v8.getValue());
		});
	}
	
	@Test
	void testAddOneBooleanOneInteger() {
		ValueWrapper vv1 = new ValueWrapper(Boolean.valueOf(true));
		assertThrows(RuntimeException.class, () -> {
			vv1.add(Integer.valueOf(5));
		});
		ValueWrapper vv2 = new ValueWrapper(Integer.valueOf(5));
		assertThrows(RuntimeException.class, () -> {
			vv2.add(Boolean.valueOf(true));
		});
	}

}
