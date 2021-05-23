package hr.fer.zemris.lsystems.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.lsystems.LSystem;

class LSystemBuilderImplTest {

	@Test
	void testGenerate() {
		LSystemBuilderImpl lSysBuild = new LSystemBuilderImpl();
		LSystem lSys = lSysBuild.setAxiom("F").registerProduction('F', "F+F--F+F").build();
		assertEquals("F", lSys.generate(0));
		assertEquals("F+F--F+F", lSys.generate(1));
		assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", lSys.generate(2));
	}

}
