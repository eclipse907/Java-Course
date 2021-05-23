package hr.fer.zemris.java.blog.web.forms;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import hr.fer.zemris.java.blog.hash.Util;
import hr.fer.zemris.java.blog.model.BlogUser;

public class LoginForm {

	private String nick;
	private String password;
	private Map<String, String> errors;
	
	public LoginForm() {
		errors = new HashMap<>();
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
		nick = request.getParameter("nick") != null ? request.getParameter("nick") : "";
		password = request.getParameter("password") != null ? request.getParameter("password") : "";
	}
	
	public void fillFromBlogUser(BlogUser user) {
		nick = user.getNick() != null ? user.getNick() : "";
		password = user.getPasswordHash() != null ? user.getPasswordHash() : "";
	}
	
	public void fillGivenBlogUser(BlogUser user) throws NoSuchAlgorithmException, IOException {
		user.setNick(nick);
		user.setPasswordHash(Util.messageDigest(password));
	}
	
	public void validate() {
		errors.clear();
		if (nick.isBlank()) {
			errors.put("nick", "Nickname is required!");
		}
		if (password.isBlank()) {
			errors.put("password", "Password is required!");
		}
	}
	
}
