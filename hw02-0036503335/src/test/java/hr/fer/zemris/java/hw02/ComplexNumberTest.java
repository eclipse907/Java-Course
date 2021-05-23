package hr.fer.zemris.java.hw02;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ComplexNumberTest {

	@Test
	void factoryMethodsAndGettersTests() {
		ComplexNumber num = ComplexNumber.fromReal(3.0);
		assertEquals(3.0, num.getReal());
		num = ComplexNumber.fromImaginary(5.5);
		assertEquals(5.5, num.getImaginary());
		num = ComplexNumber.fromMagnitudeAndAngle(9, Math.PI / 2);
		assertEquals(9, num.getMagnitude());
		assertEquals(Math.PI / 2, num.getAngle());
		String s = "+3.15";
		num = ComplexNumber.parse(s);
		assertEquals(3.15, num.getReal());
		s = "-2.67i";
		num = ComplexNumber.parse(s);
		assertEquals(-2.67, num.getImaginary());
		s = "+3.28+1.3i";
		num = ComplexNumber.parse(s);
		assertEquals(3.28, num.getReal());
		assertEquals(1.3, num.getImaginary());
		s = "i";
		num = ComplexNumber.parse(s);
		assertEquals(1.0, num.getImaginary());
	}
	
	@Test
	void wrongStringForParsingTest() {
		assertThrows(NumberFormatException.class, ()-> {ComplexNumber.parse("++3.14");});
		assertThrows(NumberFormatException.class, ()-> {ComplexNumber.parse("34f.6");});
		assertThrows(NumberFormatException.class, ()-> {ComplexNumber.parse("34.");});
		assertThrows(NumberFormatException.class, ()-> {ComplexNumber.parse("21+-i");});
	}
	
	@Test
	void addTest() {
		ComplexNumber num1 = new ComplexNumber(3.0, 4.0);
		ComplexNumber num2 = new ComplexNumber(2.0, 3.0);
		ComplexNumber result = num1.add(num2);
		assertEquals(5.0, result.getReal());
		assertEquals(7.0, result.getImaginary());
	}
	
	@Test
	void subTest() {
		ComplexNumber num1 = new ComplexNumber(3.0, 4.0);
		ComplexNumber num2 = new ComplexNumber(2.0, 3.0);
		ComplexNumber result = num1.sub(num2);
		assertEquals(1.0, result.getReal());
		assertEquals(1.0, result.getImaginary());
	}
	
	@Test
	void mulTest() {
		ComplexNumber num1 = new ComplexNumber(3.0, 4.0);
		ComplexNumber num2 = new ComplexNumber(2.0, 3.0);
		ComplexNumber result = num1.mul(num2);
		assertEquals(-6.0, result.getReal(), 4);
		assertEquals(17.0, result.getImaginary(), 4);
	}
	
	@Test
	void divTest() {
		ComplexNumber num1 = new ComplexNumber(3.0, 4.0);
		ComplexNumber num2 = new ComplexNumber(2.0, 3.0);
		ComplexNumber result = num1.div(num2);
		assertEquals(18.0/13.0, result.getReal(), 4);
		assertEquals(-1.0/13.0, result.getImaginary(), 4);
	}
	
	@Test
	void powerTest() {
		ComplexNumber num = new ComplexNumber(3.0, 4.0);
		ComplexNumber result = num.power(2);
		assertEquals(-7.0, result.getReal(), 4);
		assertEquals(24.0, result.getImaginary(), 4);
	}
	
	@Test
	void rootTest() {
		ComplexNumber num = new ComplexNumber(3.0, 4.0);
		ComplexNumber[] results = num.root(3);
		ComplexNumber[] expected = {
				new ComplexNumber(1.628937, 0.5201745),
				new ComplexNumber(-1.26495, 1.150613),
				new ComplexNumber(-0.363984, -1.6707882)
		};
		for (int i = 0; i < 3; i++) {
			assertEquals(results[i].getReal(), expected[i].getReal(), 4);
			assertEquals(results[i].getImaginary(), expected[i].getImaginary(), 4);
		}
	}
	
	@Test
	void toStringTest() {
		ComplexNumber num = new ComplexNumber(3.0, 4.0);
		assertEquals("3.0+4.0i", num.toString());
	}

}
