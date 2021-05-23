package hr.fer.zemris.java.hw11.jnotepadpp.local;

public class LocalizationProviderBridge extends AbstractLocalizationProvider {
	
	private ILocalizationProvider provider;
	private ILocalizationListener listener;
	private boolean connected;
	private String savedLanguage;
	
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		this.provider = provider;
		listener = () -> fire();
		savedLanguage = provider.getCurrentLanguage();
	}
	
	public void connect() {
		if (connected) {
			return;
		}
		connected = true;
		provider.addLocalizationLisener(listener);
		if (!savedLanguage.equals(getCurrentLanguage())) {
			savedLanguage = getCurrentLanguage();
			fire();
		}
	}
	
	public void disconnect() {
		connected = false;
		provider.removeLocalizationLisener(listener);
		savedLanguage = getCurrentLanguage();
	}

	@Override
	public String getString(String keyword) {
		return provider.getString(keyword);
	}

	@Override
	public String getCurrentLanguage() {
		return provider.getCurrentLanguage();
	}

}
