package hr.fer.zemris.java.blog.web.forms;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import hr.fer.zemris.java.blog.dao.DAOProvider;
import hr.fer.zemris.java.blog.hash.Util;
import hr.fer.zemris.java.blog.model.BlogUser;

public class NewBlogUserForm {

	private String id;
	private String firstName;
	private String lastName;
	private String nick;
	private String email;
	private String password;
	private Map<String, String> errors;
	
	public NewBlogUserForm() {
		errors = new HashMap<>();
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * @return the nick
	 */
	public String getNick() {
		return nick;
	}
	
	/**
	 * @param nick the nick to set
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
		
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public boolean hasErrors() {
		return !errors.isEmpty();
	}
	
	public boolean hasError(String name) {
		return errors.containsKey(name);
	}
	
	public String getError(String name) {
		return errors.get(name);
	}
	
	public void fillFromHttpRequest(HttpServletRequest request) {
		id = request.getParameter("id") != null ? request.getParameter("id") : "";
		firstName = request.getParameter("firstName") != null ? request.getParameter("firstName") : "";
		lastName = request.getParameter("lastName") != null ? request.getParameter("lastName") : "";
		nick = request.getParameter("nick") != null ? request.getParameter("nick") : "";
		email = request.getParameter("email") != null ? request.getParameter("email") : "";
		password = request.getParameter("password") != null ? request.getParameter("password") : "";
	}
	
	public void fillFromBlogUser(BlogUser user) {
		if (user.getId() != null) {
			id = user.getId().toString();
		} else {
			id = "";
		}
		firstName = user.getFirstName() != null ? user.getFirstName() : "";
		lastName = user.getLastName() != null ? user.getLastName() : "";
		nick = user.getNick() != null ? user.getNick() : "";
		email = user.getEmail() != null ? user.getEmail() : "";
		password = user.getPasswordHash() != null ? user.getPasswordHash() : "";
	}
	
	public void fillGivenBlogUser(BlogUser user) throws NoSuchAlgorithmException, IOException {
		if (id.isBlank()) {
			user.setId(null);
		} else {
			user.setId(Long.valueOf(id));
		}
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setNick(nick);
		user.setEmail(email);
		user.setPasswordHash(Util.messageDigest(password));
	}
	
	public void validate() {
		errors.clear();
		if (!id.isBlank()) {
			try {
				Long.parseLong(id);
			} catch (NumberFormatException ex) {
				errors.put("id", "The given id is invalid.");
			}
		}
		if (firstName.isBlank()) {
			errors.put("firstName", "First name is required!");
		}
		if (lastName.isBlank()) {
			errors.put("lastName", "Last name is required!");
		}
		if (nick.isBlank()) {
			errors.put("nick", "Nickname is required!");
		} else {
			if (DAOProvider.getDAO().getBlogUserByNickname(nick) != null) {
				errors.put("nick", "Nickname already exists.");
			}
		}
		if(email.isBlank()) {
			errors.put("email", "E-mail is required!");
		} else {
			int l = email.length();
			int p = email.indexOf('@');
			if(l<3 || p==-1 || p==0 || p==l-1) {
				errors.put("email", "E-mail format is invalid.");
			}
		}
		if (password.isBlank()) {
			errors.put("password", "Password is required!");
		}
	}
		
}
