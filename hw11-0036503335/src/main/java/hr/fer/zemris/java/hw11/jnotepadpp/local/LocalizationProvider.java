package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationProvider extends AbstractLocalizationProvider {
	
	private String language;
	private ResourceBundle bundle;
	private static final LocalizationProvider provider = new LocalizationProvider();
	
	private LocalizationProvider() {
		setLanguage("en");
	}
	
	public static LocalizationProvider getInstance() {
		return provider;
	}
	
	public void setLanguage(String language) {
		if (language.equals(this.language)) {
			return;
		}
		this.language = language;
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.locale.prijevodi", locale);
		fire();
	}

	@Override
	public String getString(String keyword) {
		return bundle.getString(keyword);
	}

	@Override
	public String getCurrentLanguage() {
		return language;
	}

}
