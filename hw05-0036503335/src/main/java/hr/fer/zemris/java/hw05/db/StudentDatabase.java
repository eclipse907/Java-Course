package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents a database that contains entries about
 * students. It stores this entries in the form of a StudentRecord
 * class. 
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class StudentDatabase {

	private List<StudentRecord> database;
	private Map<String, StudentRecord> index;
	
	/**
	 * Creates a new student database from the given student
	 * data.
	 * 
	 * @param data the student data.
	 * @throws IllegalArgumentException if the given data is
	 *                                  wrong.
	 */
	public StudentDatabase(List<String> data) {
		this.database = new ArrayList<>();
		this.index = new HashMap<>();
		for (String entry : data) {
			String[] line = entry.trim().split("\\s+");
			int grade;
			try {
				grade = Integer.parseInt(line[line.length - 1]);
			} catch (NumberFormatException ex) {
				throw new IllegalArgumentException("Given grade is not a number.");
			}
			if (index.containsKey(line[0])) {
				throw new IllegalArgumentException("Duplicate entry given.");
			}
			if (grade < 1 || grade > 5) {
				throw new IllegalArgumentException("Wrong grade entered.");
			}
			StudentRecord record;
			if (line.length == 4) {
				record = new StudentRecord(line[0], line[2], line[1], grade);
			} else if (line.length == 5) {
				record = new StudentRecord(line[0], line[3], line[1] + " " + line[2], grade);
			} else {
				throw new IllegalArgumentException("Given entry is wrong.");
			}
			database.add(record);
			index.put(line[0], record);
		}
	}
	
	/**
	 * Returns the student data associated with this
	 * jmbag if it exists, null otherwise. This method
	 * has a complexity of O(1).
	 * 
	 * @param jmbag the jmbag of the student.
	 * @return the student data associated with this
	 *         jmbag if it exists, null otherwise.
	 * @throws NullPointerException if the given jmbag
	 *                              is null.
	 */
	public StudentRecord forJMBAG(String jmbag) {
		if (jmbag == null) {
			throw new NullPointerException();
		}
		return index.get(jmbag);
	}
	
	/**
	 * Returns a list containing student data from the database 
	 * that the given filter accepted.
	 * 
	 * @param filter an object that checks if the student data is
	 *               acceptable.
	 * @return a list containing student data from the database.
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> filteredEntries = new ArrayList<>();
		for (StudentRecord record : database) {
			if (filter.accepts(record)) {
				filteredEntries.add(record);
			}
		}
		return filteredEntries;
	}
	
}
