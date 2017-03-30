package com.smartgxt.client.ui.shortcuts;

import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.google.gwt.user.client.Command;
import com.smartgxt.client.util.UIHelper;

/**
 * 
 * Shortcuts support. Adds shortcut support to any GXT component. Component must
 * be attached to it's parent, before calling this class. Valid combinations
 * are: Supported keys [a-z], [0-9] on main keyboard, [F1-F12], with any
 * modifier like: Ctrl, Shift, Alt.
 * 
 * Usage:
 * 
 * <pre>
 * ...
 * ShortcutsManager.get().addLocalShortcut(b1, &quot;Ctrl+1&quot;);
 * ...
 * </pre>
 * 
 * Will add Listener for current window only.
 * 
 * <pre>
 * ...
 * ShortcutsManager.get().addGloablShortcut(b1, &quot;Ctrl+1&quot;);
 * ...
 * </pre>
 * 
 * Will add Listener for all Application. Feature next release.
 * 
 * @author Anton Alexeyev
 */

public class ShortcutsManager {

	private static ShortcutsManager instance = new ShortcutsManager();

	public ShortcutsManager() {
		// Document.get().addListener(Events.OnKeyUp, onKeyDownEvent);
		// Document.get().sinkEvents(Event.ONKEYPRESS);
	}

	public Component attachShortcuts(Component component, Component container,
			String shortcut) {
		String hint = null;
		if (component.getToolTip() != null)
			hint = component.getToolTip().getToolTipConfig().getText();
		if (hint != null)
			component.setToolTip(hint + " (" + shortcut + ")");
		else
			component.setToolTip(UIHelper.getComponentText(component) + " ("
					+ shortcut + ")");

		// Window parent = UIHelper.getParentWindow(component);

		if (container.getData("sgxt.shortcuts") == null) {
			container.setData("sgxt.shortcuts", new Shortcuts());
		}
		return container;

		// if (component.getData("sgxt.shortcuts") == null) {
		// component.setData("sgxt.shortcuts", new Shortcuts());
		// }
		//
		// return component;
	}

	public void addLocalShortcut(Component component, Component container,
			String shortcut) {
		attachShortcuts(component, container, shortcut);
		Shortcut sc = new Shortcut(shortcut, component);
		((Shortcuts) container.getData("sgxt.shortcuts")).addShortcut(sc);
		container.removeListener(Events.OnKeyPress, onKeyEvent);
		container.addListener(Events.OnKeyPress, onKeyEvent);
	}

	public void addLocalShortcut(Component component, Component container,
			String shortcut, Command command) {
		attachShortcuts(component, container, shortcut);
		((Shortcuts) container.getData("sgxt.shortcuts"))
				.addShortcut(new Shortcut(shortcut, component, command));
	}

	private Listener<ComponentEvent> onKeyEvent = new Listener<ComponentEvent>() {
		@Override
		public void handleEvent(ComponentEvent be) {
			// TODO replace with WindowManager
			// Window wnd = UIHelper.getParentWindow(be.getComponent());
			// Window wnd = Desktop.activeWindow;
			// Window wnd = WindowManager.get().getActive();
			Component wnd = be.getComponent();
			// MessageBox
			// .info("!!!", "activeWindow is null = " + (wnd == null), null);
			if (wnd != null) {
				Shortcuts shortcuts = wnd.getData("sgxt.shortcuts");
				if (shortcuts != null) {

					Shortcut shortcut = shortcuts.getShortcut(new Shortcut(be
							.getEvent().getShiftKey(), be.getEvent()
							.getCtrlKey(), be.getEvent().getAltKey(), be
							.getEvent().getCharCode()));

					if (shortcut == null)
						shortcut = shortcuts.getShortcut(new Shortcut(be
								.getEvent().getShiftKey(), be.getEvent()
								.getCtrlKey(), be.getEvent().getAltKey(), be
								.getKeyCode()));

					// KeyEvent.VK_F1
					if (shortcut != null) {
						Command command = shortcut.getCommand();
						if (command == null) {
							Component component = shortcut.getComponent();
							if ((component != null)
									&& (component instanceof Button)) {
								if (!((be.getKeyCode() == 13) && (component
										.getStyleName().indexOf("x-btn-focus") != -1)))
									component.fireEvent(Events.Select);
							}
						} else {
							command.execute();
						}
					}
				}
			}
		}
	};

	public static ShortcutsManager get() {
		return instance;
	}
}
