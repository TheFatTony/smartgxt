package com.smartgxt.incubator.client.shortcuts;

import java.util.HashMap;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Component;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;

public class ShortcutsManager {
	private final static String csShortcuts = "sgxt.ShortcutsManager.shortcuts";
	private static ShortcutsManager instance = new ShortcutsManager();
	private HandlerRegistration nativePreviewHandlerRegistration;

	private HashMap<Shortcut, Component> shortcuts;

	public ShortcutsManager() {
		shortcuts = new HashMap<Shortcut, Component>();
		nativePreviewHandlerRegistration = Event
				.addNativePreviewHandler(nativePreviewHandler);
	}

	private NativePreviewHandler nativePreviewHandler = new NativePreviewHandler() {
		public void onPreviewNativeEvent(NativePreviewEvent preview) {
			NativeEvent event = preview.getNativeEvent();
			if (event.getType().equalsIgnoreCase("keypress")) {
				int keycode = event.getCharCode();
				if (keycode == 0)
					keycode = event.getKeyCode();
				
				boolean ctrl = event.getCtrlKey();
				boolean shift = event.getShiftKey();
				boolean alt = event.getAltKey();

				System.out.println("Shortcut getCharCode = "+ event.getCharCode());
				System.out.println("Shortcut keyCode = "+ event.getKeyCode());
				
				Shortcut shortcut = new Shortcut(shift, ctrl, alt, keycode);

				for (Shortcut s : shortcuts.keySet()) {
					if (ShortcutComparator.get().compare(s, shortcut))
						shortcutSelected(shortcuts.get(s));
				}
			}
		}
	};

	public void shortcutSelected(Component component) {
		if (component instanceof TextButton) {
			((TextButton) component).fireEvent(new SelectEvent());
		}

	}

	public ShortcutRegistration addShortcut(Component component, String shortcut) {
		return addShortcut(component, new Shortcut(shortcut));
	}

	public ShortcutRegistration addShortcut(Component component,
			Shortcut shortcut) {
		String hint = null;
		if (component.getToolTip() != null)
			hint = component.getToolTip().getToolTipConfig().getTitleHtml();

		if (hint != null)
			component.setToolTip(hint + " (" + shortcut.toString() + ")");
		else
			component.setToolTip(getComponentText(component) + " ("
					+ shortcut.toString() + ")");

		shortcuts.put(shortcut, component);

		return new ShortcutRegistration(shortcut);

	}

	public void removeShortCut(Shortcut shortcut) {
		shortcuts.remove(shortcut);
	}

	public static String getComponentText(Widget component) {
		String text = null;
		if (component instanceof TextButton)
			text = ((TextButton) component).getText();
		return text;
	}

	public static ShortcutsManager get() {
		return instance;
	}

}
