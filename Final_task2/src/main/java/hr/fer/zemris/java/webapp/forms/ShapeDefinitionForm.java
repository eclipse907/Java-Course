package hr.fer.zemris.java.webapp.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class ShapeDefinitionForm {

	private String text;
	private Map<String, String> errors;
	
	public ShapeDefinitionForm() {
		errors = new HashMap<>();
		text = "";
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
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
	
	public void setError(String name, String value) {
		errors.put(name, value);
	}
	
	public void fillFromHttpRequest(HttpServletRequest request) {
		text = request.getParameter("text") != null ? request.getParameter("text") : "";
	}
	
	public void validate() {
		errors.remove("text");
		if (text.isBlank()) {
			errors.put("text", "Text is required!");
		}
	}
	
}
