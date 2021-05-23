package hr.fer.zemris.java.blog.web.forms;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import hr.fer.zemris.java.blog.model.BlogComment;

public class NewCommentForm {

	private String usersEMail;
	private String message;
	private Map<String, String> errors;
	
	public NewCommentForm() {
		errors = new HashMap<>();
	}

	/**
	 * @return the usersEMail
	 */
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * @param usersEMail the usersEMail to set
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
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
		usersEMail = request.getParameter("usersEMail") != null ? request.getParameter("usersEMail") : "";
		message = request.getParameter("message") != null ? request.getParameter("message") : "";
	}
	
	public void fillFromBlogComment(BlogComment comment) {
		usersEMail = comment.getUsersEMail() != null ? comment.getUsersEMail() : "";
		message = comment.getMessage() != null ? comment.getMessage() : "";
	}
	
	public void fillGivenBlogComment(BlogComment comment) {
		comment.setUsersEMail(usersEMail);
		comment.setMessage(message);
	}
	
	public void validate() {
		errors.clear();
		if (usersEMail.isBlank()) {
			errors.put("usersEMail", "E-mail is required!");
		} else {
			int l = usersEMail.length();
			int p = usersEMail.indexOf('@');
			if(l<3 || p==-1 || p==0 || p==l-1) {
				errors.put("usersEMail", "E-mail format is invalid.");
			}
		}
		if (message.isBlank()) {
			errors.put("message", "Message is required!");
		} 
	}

}
