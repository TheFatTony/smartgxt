package com.smartgxt.client.ui.widgets.desktop;

import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.menu.CheckMenuItem;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.smartgxt.client.core.SmartGXT;
import com.smartgxt.client.managers.Locale;
import com.smartgxt.client.managers.LocaleManager;
import com.smartgxt.client.messages.Localization;
import com.smartgxt.client.ui.widgets.menus.MenuItem;

/**
 * @author Anton Alexeyev
 * 
 */
public class LocaleSelectorMenuItem extends MenuItem {

	private Locale last;
	private Menu menu;

	public LocaleSelectorMenuItem() {
		menu = new Menu();
		setText(Localization.get().localeSelector());
		setSubMenu(menu);

	}

	@Override
	protected void beforeRender() {
		super.beforeRender();
		String locale = SmartGXT.getLocaleId();
		for (Locale l : LocaleManager.getLocales()) {
			CheckMenuItem menuItem = new CheckMenuItem();
			menuItem.setGroup("LocaleSelectorMenuItemRadio");
			menuItem.setText(l.getName());
			menuItem.setData("sgxt.locale", l);
			if (l.getId().equals(locale)) {
				menuItem.setChecked(true);
				last = l;
			}
			menuItem.addSelectionListener(onItemSelected);
			menu.add(menuItem);
		}
	}

	SelectionListener<MenuEvent> onItemSelected = new SelectionListener<MenuEvent>() {
		@Override
		public void componentSelected(MenuEvent ce) {
			Locale c = ce.getItem().getData("sgxt.locale");
			if (c != last) {
				last = c;
				SmartGXT.switchLocale(c);
			}
		}
	};
}
