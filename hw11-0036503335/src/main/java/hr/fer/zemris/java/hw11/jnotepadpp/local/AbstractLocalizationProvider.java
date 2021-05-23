package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractLocalizationProvider implements ILocalizationProvider {
	
	private Set<ILocalizationListener> observers;
	
	public AbstractLocalizationProvider() {
		observers = new HashSet<>();
	}

	@Override
	public void addLocalizationLisener(ILocalizationListener listener) {
		observers.add(listener);
	}

	@Override
	public void removeLocalizationLisener(ILocalizationListener listener) {
		observers.remove(listener);
	}

	public void fire() {
		observers.forEach(observer -> observer.localizationChanged());
	}
	
}
