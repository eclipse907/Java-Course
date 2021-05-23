package hr.fer.zemris.java.hw05.db;

/**
 * This class represents an entry in a student database.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class StudentRecord {
	
	private String jmbag;
	private String lastName;
	private String firstName;
	private int finalGrade;
	
	/**
	 * Creates a new student entry with the given values.
	 * 
	 * @param jmbag the jmbag of the student.
	 * @param firstName the first name of the student.
	 * @param lastName the last name of the student.
	 * @param finalGrade the final grade of the student.
	 */
	public StudentRecord(String jmbag, String firstName, String lastName, int finalGrade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}

	/**
	 * Returns the jmbag of the student.
	 * 
	 * @return the jmbag of the student.
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Returns the last name of the student.
	 * 
	 * @return the last name of the student.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Returns the first name of the student.
	 * 
	 * @return the first name of the student.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Returns the final grade of the student.
	 * 
	 * @return the final grade of the student.
	 */
	public int getFinalGrade() {
		return finalGrade;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof StudentRecord)) {
			return false;
		}
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null) {
				return false;
			}
		} else if (!jmbag.equals(other.jmbag)) {
			return false;
		}
		return true;
	}

}
