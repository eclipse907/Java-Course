package hr.fer.zemris.java.hw06.crypto;

/**
 * This class contains static methods used for converting strings containing
 * hexadecimal digits to byte arrays and vice versa. This class should not
 * be instanced.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class Util {
	
	/**
	 * This method converts the given string containing hexadecimal digits
	 * to a byte array representing the hexadecimal digits in the string given.
	 * 
	 * @param keyText the string to convert.
	 * @return a byte array representing the hexadecimal digits in the string given.
	 * @throws IllegalArgumentException if the string contains an odd number of
	 *                                  hexadecimal digits or a non hex character
	 *                                  is in the string.
	 */
	public static byte[] hextobyte(String keyText) {
		if (keyText.length() % 2 != 0) {
			throw new IllegalArgumentException("Wrong number of hex characters provided.");
		}
		byte[] keyBytes = new byte[keyText.length() / 2];
		for (int i = 0, digit1, digit2; i < keyText.length(); i += 2) {
			digit1 = Character.digit(keyText.charAt(i), 16);
			digit2 = Character.digit(keyText.charAt(i + 1), 16);
			if (digit1 == -1 || digit2 == -1) {
				throw new IllegalArgumentException("Wrong hex character entered.");
			}
			keyBytes[i / 2] = (byte) ((digit1 << 4) + digit2);
		}
		return keyBytes;
	}
	
	/**
	 * This method returns a string containing the hexadecimal representation
	 * of the given byte array. All hex letters are written as lower case
	 * characters.
	 *  
	 * @param bytearray the byte array to convert.
	 * @return a string containing the hexadecimal representation
	 *         of the given byte array.
	 */
	public static String bytetohex(byte[] bytearray) {
		StringBuilder hexString = new StringBuilder();
		char hex;
		for (byte hexByte : bytearray) {
			hex = Character.forDigit((hexByte >> 4) & 0xF, 16);
			hexString.append(hex);
			hex = Character.forDigit(hexByte & 0xF, 16);
			hexString.append(hex);
		}
		return hexString.toString();
	}

}
