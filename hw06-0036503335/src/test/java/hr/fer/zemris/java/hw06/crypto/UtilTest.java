package hr.fer.zemris.java.hw06.crypto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class UtilTest {

	@Test
	void testHexToByte() {
		byte[] expectedBytes = {1, -82, 34};
		assertTrue(Arrays.equals(expectedBytes, Util.hextobyte("01aE22")));
		expectedBytes = new byte[0];
		assertTrue(Arrays.equals(expectedBytes, Util.hextobyte("")));
		expectedBytes = new byte[2];
		expectedBytes[0] = 2;
		expectedBytes[1] = 13;
		assertTrue(Arrays.equals(expectedBytes, Util.hextobyte("020d")));
		assertThrows(IllegalArgumentException.class, () -> {
			Util.hextobyte("01aefjbc65");
		});
		assertThrows(IllegalArgumentException.class, () -> {
			Util.hextobyte("45af5");
		});
	}
	
	@Test
	void testByteToHex() {
		byte[] inputBytes = {1, -82, 34};
		assertEquals("01ae22", Util.bytetohex(inputBytes));
		inputBytes = new byte[0];
		assertEquals("", Util.bytetohex(inputBytes));
		inputBytes = new byte[2];
		inputBytes[0] = 2;
		inputBytes[1] = 13;
		assertEquals("020d", Util.bytetohex(inputBytes));
	}

}
