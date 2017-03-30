package com.smartgxt.incubator.client.shortcuts;

import org.apache.regexp.recompile;

import com.google.gwt.user.client.Command;
import com.sencha.gxt.core.shared.FastMap;

/**
 * @author Anton Alexeyev
 * 
 */

public class Shortcut {

	private static FastMap<Integer> keys = new FastMap<Integer>();

	private boolean isValid = false;

	private boolean isShift = false;
	private boolean isCtrl = false;
	private boolean isAlt = false;
	private int keyCode = -1;
	private Command command;
	private String shortcut;

	public Shortcut(String shortcut) {
		this.shortcut = shortcut;
		parseString(shortcut);
	}

	public Shortcut(String shortcut, Command command) {
		this.shortcut = shortcut;
		parseString(shortcut);
		this.command = command;
	}

	public Shortcut(boolean isShift, boolean isCtrl, boolean isAlt, int keyCode) {
		this.isShift = isShift;
		this.isCtrl = isCtrl;
		this.isAlt = isAlt;
		this.keyCode = keyCode;
	}

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}

	private void parseString(String shortcut) {
		String baseStr = shortcut + "+";
		int beginIndex = -1;
		int endIndex = baseStr.indexOf("+");

		// assert endIndex != -1 :
		// "Invalid shortcut delimiter, use + symbol only";

		while (endIndex != -1) {
			String str = baseStr.substring(beginIndex + 1, endIndex);
			beginIndex = endIndex;
			endIndex = baseStr.indexOf("+", beginIndex + 1);
			pushString(str.toLowerCase());
		}
	}

	public boolean isShift() {
		// validate();
		return isShift;
	}

	public void setShift(boolean value) {
		isShift = value;
	}

	public boolean isAlt() {
		// validate();
		return isAlt;
	}

	public void setAlt(boolean value) {
		isAlt = value;
	}

	public boolean isCtrl() {
		// validate();
		return isCtrl;
	}

	public void setCtrl(boolean value) {
		isCtrl = value;
	}

	public int getKeyCode() {
		return keyCode;
	}

	protected void validate() {
		if (!isValid)
			if (keyCode != -1) {
				isValid = true;
			} else {
				isValid = false;
				assert false : "Mast be setted at least one key";
			}
	}

	public void setKey(String key) {
		int k = -1;
		String lKey = key.toLowerCase();
		if (getKeys().containsKey(lKey))
			k = getKeys().get(lKey);
		else
			k = (int) key.charAt(0);
		keyCode = k;
	}

	// public void setKey(int keyCode) {
	// this.keyCode = keyCode;
	// }

	public void pushString(String str) {
		assert str != null : "Modifier or key can not be null";

		if ((str.intern() == "ctrl") || (str.intern() == "shift")
				|| (str.intern() == "alt"))
			addModifier(str);
		else
			setKey(str);
	}

	public void addModifier(String modifier) {
		String s = modifier.toLowerCase();
		if (s.intern() == "ctrl")
			isCtrl = true;
		else if (s.intern() == "shift")
			isShift = true;
		else if (s.intern() == "alt")
			isAlt = true;
		// else
		// assert false :
		// "Modifier mast be ctrl, shift or alt only, Case independent";
	}

	public static FastMap<Integer> getKeys() {
		if (keys.size() == 0) {
			keys.put("enter", 13);
			keys.put("f1", 0x70);
			keys.put("f2", 0x71);
			keys.put("f3", 0x72);
			keys.put("f4", 0x73);
			keys.put("f5", 0x74);
			keys.put("f6", 0x75);
			keys.put("f7", 0x76);
			keys.put("f8", 0x77);
			keys.put("f9", 0x78);
			keys.put("f10", 0x79);
			keys.put("f11", 0x7A);
			keys.put("f12", 0x7B);
		}
		return keys;
	}

	@Override
	public String toString() {
		if (shortcut == null) {
			StringBuffer s = new StringBuffer();
			if (isAlt())
				s.append("Alt+");
			if (isCtrl())
				s.append("Ctrl+");
			if (isShift())
				s.append("Shift+");

			s.append((char) getKeyCode());

			return s.toString();
		} else
			return shortcut;
	}
}
