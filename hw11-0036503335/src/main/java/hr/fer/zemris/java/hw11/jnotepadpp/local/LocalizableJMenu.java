package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.JMenu;

public class LocalizableJMenu extends JMenu {

	private static final long serialVersionUID = 5847339331356310957L;
	private String nameKey;
	
	public LocalizableJMenu(String nameKey, ILocalizationProvider lp) {
		super(lp.getString(nameKey));
		this.nameKey = nameKey;
		lp.addLocalizationLisener(() -> {
			setText(lp.getString(this.nameKey));
		});
	}

}
