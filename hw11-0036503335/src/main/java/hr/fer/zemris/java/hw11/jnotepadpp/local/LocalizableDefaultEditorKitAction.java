package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.ActionEvent;

import javax.swing.Action;

public class LocalizableDefaultEditorKitAction extends LocalizableAbstractAction {

	private static final long serialVersionUID = -2974429404191556509L;
	private Action action;

	public LocalizableDefaultEditorKitAction(String nameKey, String descriptionKey, ILocalizationProvider lp, Action action) {
		super(nameKey, descriptionKey, lp);
		this.action = action;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		action.actionPerformed(e);
	}

}
