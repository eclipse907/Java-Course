package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.JToolBar;

public class LocalizableJToolBar extends JToolBar {

	private static final long serialVersionUID = -2674776114660356128L;
	private String nameKey;
	
	public LocalizableJToolBar(String nameKey, ILocalizationProvider lp) {
		super(lp.getString(nameKey));
		this.nameKey = nameKey;
		lp.addLocalizationLisener(() -> {
			setName(lp.getString(this.nameKey));
		});
	}

}
