package hr.fer.zemris.java.hw06.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * This class represents a program that can encrypt/decrypt a file given as a command line argument
 * or check if its SHA-256 file digest matches the given SHA-256 file digest. The program uses the
 * AES cryptoalgorithm and a 128-bit encryption key to encrypt/decrypt the given file. The program requires
 * command line arguments in the following order. First a string that has a value "checksha", 
 * "encrypt" or "decrypt" (without quotes) which sets the desired operation (check sha-256 file digest, 
 * encrypt/decrypt file). Second the name of the file for which to check file digest or encrypt/decrypt.
 * The file given must be located in the src/main/resources directory of this project. Finally if the encrypt
 * or decrypt operation is chosen the name of the output file.  
 *  
 * @author Tin Reiter
 * @version 1.0
 */
public class Crypto {
	
	/**
	 * The method from which the program starts.
	 * 
	 * @param args command line arguments.
	 * @throws IOException if there is an error while reading from or
	 *                     writing to a file.
	 * @throws IllegalArgumentException if a wrong number of command line arguments
	 *                                  is given or a wrong operation string is given.
	 */
	public static void main(String[] args) throws IOException {
		if (args.length < 1) {
			throw new IllegalArgumentException("Wrong number of command line arguments given.");
		}
		boolean encrypt = false;
		if (args[0].equals("checksha")) {
			if (args.length == 2) {
				try (
						InputStream is = new BufferedInputStream(Files.newInputStream(Paths.get("src\\main\\resources\\" + args[1])));
						Scanner sc = new Scanner(System.in);
				) {
					System.out.println("Please provide expected sha-256 digest for " + args[1] + ":");
					if (sc.hasNext()) {
						String shaDigest = sc.next();
						String calculatedDigest = messageDigest(is);
						if (shaDigest.equalsIgnoreCase(calculatedDigest)) {
							System.out.println("Digesting completed. Digest of " + args[1] +" matches expected digest.");
						} else {
							System.out.println(
								"Digesting completed. Digest of " +
							    args[1] +
							    " does not match the expected digest. Digest was:\n" +
							    calculatedDigest
							);
						}
						return;
					}
				} catch (Exception ex) {
					System.out.println(ex.getMessage() + " Program will now exit.");
					return;
				}
			} else {
				throw new IllegalArgumentException("Wrong number of command line arguments given."); 
			}
		} else if (args[0].equals("encrypt") || args[0].equals("decrypt")) {
			if (args.length == 3) {
				encrypt = args[0].equals("encrypt");
			} else {
				throw new IllegalArgumentException("Wrong number of command line arguments given.");
			}
		} else {
			throw new IllegalArgumentException("Wrong command given.");
		}
		try (
				InputStream is = new BufferedInputStream(Files.newInputStream(Paths.get("src\\main\\resources\\" + args[1])));
				OutputStream os = new BufferedOutputStream(Files.newOutputStream(Paths.get("src\\main\\resources\\" + args[2])));
				Scanner sc = new Scanner(System.in);
			) {
			System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
			String keyText = null;
			if (sc.hasNext()) {
				keyText = sc.next();
			}
			System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
			String ivText = null;
			if (sc.hasNext()) {
				ivText = sc.next();
			}
			encryptOrDecrypt(is, os, keyText, ivText, encrypt);
			if (encrypt) {
				System.out.println("Encryption completed. Generated file " + args[2] + " based on file " + args[1] + ".");
			} else {
				System.out.println("Decryption completed. Generated file " + args[2] + " based on file " + args[1] + ".");
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage() + " Program will now exit.");
			return;
		}
	}
	
	/**
	 * This method calculates the file digest for the given file input stream.
	 * 
	 * @param is the file input stream for which to calculate the file digest.
	 * @return a string containing the SHA-256 file digest of the given file
	 *         written in hexadecimal digits.
	 * @throws IOException if there is an error while reading the file.
	 */
	private static String messageDigest(InputStream is) throws NoSuchAlgorithmException, IOException {
		MessageDigest sha = MessageDigest.getInstance("SHA-256");
		byte[] input = new byte[4 * 1024];
		byte[] byteDigest;
		while (true) {
			int r = is.read(input);
			if (r == -1) {
				byteDigest = sha.digest();
				break;
			}
			sha.update(input, 0, r);
		}
		return Util.bytetohex(byteDigest);
	}
	
	/**
	 * This method encrypts/decrypts the given file input stream and writes the result to the
	 * given file output stream.
	 * 
	 * @param is the file input stream to encrypt/decrypt.
	 * @param os the file output stream to write the result to.
	 * @param keyText the encryption/decryption key in hexadecimal format.
	 * @param ivText the initialization vector in hexadecimal format.
	 * @param encrypt the parameter used to choose between encryption/decryption.
	 * @throws IOException if there is an error while reading from or writing to the
	 *                     given streams.
	 */
	private static void encryptOrDecrypt(InputStream is, OutputStream os, String keyText, String ivText, boolean encrypt)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException,
			IOException, IllegalBlockSizeException, BadPaddingException
	{
		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
		byte[] input = new byte[4 * 1024];
		byte[] output;
		while (true) {
			int r = is.read(input);
			if (r == -1) {
				output = cipher.doFinal();
				os.write(output);
				break;
			}
			output = cipher.update(input, 0, r);
			os.write(output);
		}
	}
	
}
