package com.smartgxt.client.ui.widgets.menus;

import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.google.gwt.user.client.ui.AbstractImagePrototype;

/**
 * @author Anton Alexeyev
 * 
 */
public class MenuItem extends com.extjs.gxt.ui.client.widget.menu.MenuItem {

	public MenuItem() {
	}

	public MenuItem(String text) {
		super(text);
	}

	public MenuItem(String text, AbstractImagePrototype icon) {
		super(text, icon);
	}

	public MenuItem(String text, SelectionListener<? extends MenuEvent> listener) {
		super(text, listener);
	}

	public MenuItem(String text, AbstractImagePrototype icon,
			SelectionListener<? extends MenuEvent> listener) {
		super(text, icon, listener);
	}

}
