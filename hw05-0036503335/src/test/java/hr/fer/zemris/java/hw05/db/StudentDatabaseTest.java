package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

class StudentDatabaseTest {

	@Test
	void testforJMBAG() throws IOException {
		StudentDatabase database = new StudentDatabase(Files.readAllLines(
				Paths.get("C:\\Users\\Tin\\Documents\\College\\Opjj\\Homeworks\\hw05-0036503335\\src\\main\\java\\hr\\fer\\zemris\\java\\hw05\\db\\database.txt"),
				StandardCharsets.UTF_8)
				);
		assertEquals(new StudentRecord("0000000030", "Boris", "Kovačević", 3), database.forJMBAG("0000000030"));
	}
	
	@Test
	void testFilter() throws IOException {
		StudentDatabase database = new StudentDatabase(Files.readAllLines(
				Paths.get("C:\\Users\\Tin\\Documents\\College\\Opjj\\Homeworks\\hw05-0036503335\\src\\main\\java\\hr\\fer\\zemris\\java\\hw05\\db\\database.txt"),
				StandardCharsets.UTF_8)
				);
		assertEquals(63, database.filter(record -> true).size());
		assertEquals(0, database.filter(record -> false).size());
	}

}
