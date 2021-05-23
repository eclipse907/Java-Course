package hr.fer.zemris.java.hw11.jnotepadpp.local;

public interface ILocalizationProvider {

	void addLocalizationLisener(ILocalizationListener listener);
	void removeLocalizationLisener(ILocalizationListener listener);
	String getCurrentLanguage();
	String getString(String keyword);
	
}
