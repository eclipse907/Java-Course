package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class represents an interactive student database that accepts
 * queries from a console and returns the entries in a table format.
 * The database loads entries from a file called database.txt placed
 * in the same directory as the database. The database quits when 
 * the keyword exit is entered.
 *  
 * @author Tin Reiter
 * @version 1.0
 */
public class StudentDB {

	/**
	 * The method from which the program starts.
	 * 
	 * @param args command line arguments. Not used in this
	 *             program.
	 * @throws IOException if the database.txt can't be loaded. 
	 */
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(
				Paths.get("src\\main\\java\\hr\\fer\\zemris\\java\\hw05\\db\\database.txt"),
				StandardCharsets.UTF_8
				);
		StudentDatabase db = new StudentDatabase(lines);
		Scanner sc = new java.util.Scanner(System.in);
		QueryParser qParser;
		String query;
		List<StudentRecord> records;
		while (true) {
			System.out.printf("> ");
			query = sc.nextLine();
			if (query.trim().isBlank()) {
				continue;
			}
			if (query.equalsIgnoreCase("exit")) {
				System.out.println("Goodbye!");
				break;
			}
			String[] splitedQuery = query.split("\\s+", 2);
			if (!splitedQuery[0].equals("query") || splitedQuery.length != 2) {
				System.out.println("Wrong command entered.");
				continue;
			}
			try {
				qParser = new QueryParser(splitedQuery[1]);
				records = new ArrayList<>();
				if (qParser.isDirectQuery()) {
					StudentRecord record = db.forJMBAG(qParser.getQueriedJMBAG());
					if (record != null) {
						records.add(record);
					}
					System.out.println("Using index for record retrieval.");
					RecordFormatter.format(records).forEach(System.out::println);
					System.out.println("Records selected: " + Integer.toString(records.size()));
				} else {
					for(StudentRecord r : db.filter(new QueryFilter(qParser.getQuery()))) {
						records.add(r);
					}
					RecordFormatter.format(records).forEach(System.out::println);
					System.out.println("Records selected: " + Integer.toString(records.size()));
				}
			} catch (IllegalArgumentException ex) {
				System.out.println(ex.getMessage());
			} catch (IllegalStateException ex) {
				System.out.println(ex.getMessage());
			}
		}
		sc.close();
	}

	/**
	 * This class represents a formatter that formats the database output to a
	 * table form.
	 * 
	 * @author Tin Reiter
	 * @version 1.0
	 */
	private static class RecordFormatter {
		
		/**
		 * This method accepts a list of entries from the student database 
		 * and formats them to a table form.
		 * 
		 * @param records a list of entries from the student database.
		 * @return a list of database entries formated to table form.
		 */
		private static List<String> format(List<StudentRecord> records) {
			List<String> table = new ArrayList<>();
			if (records.size() == 0)
				return table;
			int jmbagLen = 0; 
			int nameLen = 0; 
			int surnameLen = 0;
			for (StudentRecord record : records) {
				if (record.getJmbag().length() > jmbagLen) {
					jmbagLen = record.getJmbag().length();
				}
				if (record.getFirstName().length() > nameLen) {
					nameLen = record.getFirstName().length();
				}
				if (record.getLastName().length() > surnameLen) {
					surnameLen = record.getLastName().length();
				}
			}
			table.add(createTableEdge(jmbagLen, nameLen, surnameLen));
			StringBuilder output;
			for (StudentRecord record : records) {
				output = new StringBuilder();
				output.append("| " + record.getJmbag() + " | ");
				output.append(record.getLastName());
				for (int i = 0; i < surnameLen - record.getLastName().length(); i++) {
					output.append(" ");
				}
				output.append(" | ");
				output.append(record.getFirstName());
				for (int i = 0; i < nameLen - record.getFirstName().length(); i++) {
					output.append(" ");
				}
				output.append(" | ");
				output.append(Integer.toString(record.getFinalGrade()));
				output.append(" |");
				table.add(output.toString());
			}
			table.add(createTableEdge(jmbagLen, nameLen, surnameLen));
			return table;
		}
		
		/**
		 * This method creates a table edge for the given parameters.
		 * 
		 * @param jmbagLen the length of the biggest jmbag.
		 * @param nameLen the length of the biggest first name.
		 * @param surnameLen the length of the biggest last name.
		 * @return a string representing a table edge for the given
		 *         parameters.
		 */
		private static String createTableEdge(int jmbagLen, int nameLen, int surnameLen) {
			StringBuilder output = new StringBuilder();
			output.append("+=");
			for (int i = 0; i < jmbagLen; i++) {
				output.append("=");
			}
			output.append("=+");
			output.append("=");
			for (int i = 0; i < surnameLen; i++) {
				output.append("=");
			}
			output.append("=+");
			output.append("=");
			for (int i = 0; i < nameLen; i++) {
				output.append("=");
			}
			output.append("=+");
			output.append("===+");
			return output.toString();
		}
		
	}
	
}
