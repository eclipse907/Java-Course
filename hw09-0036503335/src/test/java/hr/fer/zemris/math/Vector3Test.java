package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Vector3Test {

	@Test
	void testVector3() {
		Vector3 i = new Vector3(1,0,0);
		Vector3 j = new Vector3(0,1,0);
		Vector3 k = i.cross(j);
		Vector3 l = k.add(j).scale(5);
		Vector3 m = l.normalized();
		assertEquals("(1.0, 0.0, 0.0)", i.toString());
		assertEquals("(0.0, 1.0, 0.0)", j.toString());
		assertEquals("(0.0, 0.0, 1.0)", k.toString());
		assertEquals("(0.0, 5.0, 5.0)", l.toString());
		assertEquals(7.0710678118654755, l.norm());
		assertEquals("(0.0, 0.7071067811865475, 0.7071067811865475)", m.toString());
		assertEquals(5.0, l.dot(j));
		assertEquals(0.4999999999999999, i.add(new Vector3(0,1,0)).cosAngle(l));
	}

}
