package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.AbstractAction;
import javax.swing.Action;

public abstract class LocalizableAbstractAction extends AbstractAction {

	private static final long serialVersionUID = -6223739930054879918L;
	private String nameKey;
	private String descriptionKey;

	public LocalizableAbstractAction(String nameKey, String descriptionKey, ILocalizationProvider lp) {
		this.nameKey = nameKey;
		this.descriptionKey = descriptionKey;
		putValue(Action.NAME, lp.getString(nameKey));
		putValue(Action.SHORT_DESCRIPTION, lp.getString(descriptionKey));
		lp.addLocalizationLisener(() -> {
			putValue(Action.NAME, lp.getString(this.nameKey));
			putValue(Action.SHORT_DESCRIPTION, lp.getString(this.descriptionKey));
		});
	}
}
