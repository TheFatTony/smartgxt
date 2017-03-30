package com.smartgxt.incubator.client.shortcuts;

public class ShortcutRegistration {

	private Shortcut shortcut;

	public ShortcutRegistration(Shortcut shortcut) {
		setShortcut(shortcut);
	}

	public Shortcut getShortcut() {
		return shortcut;
	}

	public void setShortcut(Shortcut shortcut) {
		this.shortcut = shortcut;
	}

	public void remove() {
		ShortcutsManager.get().removeShortCut(shortcut);
	}

}
