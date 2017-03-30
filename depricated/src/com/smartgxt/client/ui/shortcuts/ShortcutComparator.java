package com.smartgxt.client.ui.shortcuts;

/**
 * @author Anton Alexeyev
 * 
 */

public class ShortcutComparator {
	private static ShortcutComparator instance = new ShortcutComparator();

	public boolean compare(Shortcut class1, Shortcut class2) {
		if ((class1.isAlt() == class2.isAlt())
				&& (class1.isShift() == class2.isShift())
				&& (class1.isCtrl() == class2.isCtrl())
				&& (class1.getKeyCode() != -1) && (class2.getKeyCode() != -1)
				&& (class1.getKeyCode() == class2.getKeyCode())) {
			return true;
		} else {
			return false;
		}
	}

	public static ShortcutComparator get() {
		return instance;
	}

}
