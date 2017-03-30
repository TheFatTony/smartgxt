package com.smartgxt.incubator.client.shortcuts;

/**
 * @author Anton Alexeyev
 * 
 */

public class ShortcutComparator {
	private static ShortcutComparator instance = new ShortcutComparator();

	public boolean compare(Shortcut class1, Shortcut class2) {
		// if ((class1.getKeyCode() != 16) || (class2.getKeyCode() != 16)
		// || (class1.getKeyCode() != 17) || (class2.getKeyCode() != 17)
		// || (class1.getKeyCode() != 18) || (class2.getKeyCode() != 18))
		if ((class1.isAlt() == class2.isAlt())
				&& (class1.isShift() == class2.isShift())
				&& (class1.isCtrl() == class2.isCtrl())
				&& (class1.getKeyCode() == class2.getKeyCode())) {
			return true;
		}
		return false;
	}

	public static ShortcutComparator get() {
		return instance;
	}

}
