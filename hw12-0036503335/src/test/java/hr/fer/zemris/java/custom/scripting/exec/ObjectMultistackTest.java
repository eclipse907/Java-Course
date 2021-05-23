package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ObjectMultistackTest {

	@Test
	void testPush() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		ValueWrapper price = new ValueWrapper(200.51);
		multistack.push("price", price);
		assertEquals(2000, (Integer)multistack.peek("year").getValue());
		assertEquals(200.51, (Double)multistack.peek("price").getValue());
	}
	
	@Test
	void testPushOverPush() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		multistack.push("year", new ValueWrapper(Integer.valueOf(1900)));
		assertEquals(1900, (Integer)multistack.peek("year").getValue());
	}
	
	@Test
	void testPeekChangeValue() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		multistack.push("year", new ValueWrapper(Integer.valueOf(1900)));
		multistack.peek("year").setValue(((Integer)multistack.peek("year").getValue()).intValue() + 50);
		assertEquals(1950, (Integer)multistack.peek("year").getValue());
	}
	
	@Test
	void testPop() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		multistack.push("year", new ValueWrapper(Integer.valueOf(1900)));
		multistack.peek("year").setValue(((Integer)multistack.peek("year").getValue()).intValue() + 50);
		multistack.pop("year");
		assertEquals(2000, (Integer)multistack.peek("year").getValue());
	}
	
	@Test
	void testPeekAdd() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		multistack.peek("year").add("5");
		assertEquals(2005, (Integer)multistack.peek("year").getValue());
		multistack.peek("year").add(5);
		assertEquals(2010, (Integer)multistack.peek("year").getValue());
		multistack.peek("year").add(5.0);
		assertEquals(2015.0, (Double)multistack.peek("year").getValue());
	}

}
