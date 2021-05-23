package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ComplexRootedPolynomialTest {

	@Test
	void testComplexRootedPolynomial() {
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(
				new Complex(2,0), Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG
		);
		assertEquals("(2.0+i0.0)*(z-(1.0+i0.0))*(z-(-1.0+i0.0))*(z-(0.0+i1.0))*(z-(0.0-i1.0))", crp.toString());
		crp = new ComplexRootedPolynomial(Complex.ONE, new Complex(3, 5), new Complex(8, 2), Complex.ONE);
		assertEquals("(1.0+i0.0)*(z-(3.0+i5.0))*(z-(8.0+i2.0))*(z-(1.0+i0.0))", crp.toString());
	}

}
