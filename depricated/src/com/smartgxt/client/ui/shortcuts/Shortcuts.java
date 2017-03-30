package com.smartgxt.client.ui.shortcuts;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anton Alexeyev
 * 
 */

public class Shortcuts {
	private List<Shortcut> shortcuts;

	public Shortcuts() {
		shortcuts = new ArrayList<Shortcut>();
	}

	public Shortcut getShortcut(Shortcut shortcut) {
		for (Shortcut s : shortcuts) {
			if (ShortcutComparator.get().compare(s, shortcut))
				return s;
		}
		return null;
	}

	public void addShortcut(Shortcut shortcut) {
		shortcuts.add(shortcut);
	}
}
