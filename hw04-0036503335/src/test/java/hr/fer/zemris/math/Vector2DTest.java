package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Vector2DTest {

	@Test
	void testGet() {
		Vector2D vector = new Vector2D(3.0, 5.0);
		assertEquals(3.0, vector.getX());
		assertEquals(5.0, vector.getY());
	}

	@Test
	void testTranslate() {
		Vector2D vector = new Vector2D(3.6, 5.7);
		vector.translate(new Vector2D(7.0, 9.1));
		assertEquals(10.6, vector.getX());
		assertEquals(14.8, vector.getY());
	}
	
	@Test
	void testTranslated() {
		Vector2D vector1 = new Vector2D(3.6, 5.7);
		Vector2D vector2 = vector1.translated(new Vector2D(7.0, 9.1));
		assertEquals(10.6, vector2.getX());
		assertEquals(14.8, vector2.getY());
	}
	
	@Test
	void testRotate() {
		Vector2D vector = new Vector2D(5.8, 6.3);
		vector.rotate(Math.PI / 2);
		assertEquals(-6.3, vector.getX());
		assertEquals(5.8, vector.getY());
	}
	
	@Test
	void testRotated() {
		Vector2D vector1 = new Vector2D(5.8, 6.3);
		Vector2D vector2 = vector1.rotated(Math.PI / 2);
		assertEquals(-6.3, vector2.getX());
		assertEquals(5.8, vector2.getY());
	}
	
	@Test
	void testScale() {
		Vector2D vector = new Vector2D(3.6, 7.1);
		vector.scale(2.1);
		assertEquals(7.56, vector.getX(), 2);
		assertEquals(14.91, vector.getY(), 2);
	}
	
	@Test
	void testScaled() {
		Vector2D vector1 = new Vector2D(3.6, 7.1);
		Vector2D vector2 = vector1.scaled(2.1);
		assertEquals(7.56, vector2.getX(), 2);
		assertEquals(14.91, vector2.getY(), 2);
	}
	
	@Test
	void testCopy() {
		Vector2D vector1 = new Vector2D(10.7, 12.3);
		Vector2D vector2 = vector1.copy();
		assertEquals(10.7, vector2.getX());
		assertEquals(12.3, vector2.getY());
	}
	
}
