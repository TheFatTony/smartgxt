package com.smartgxt.client.ui.widgets.desktop;

import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.util.Theme;
import com.extjs.gxt.ui.client.util.ThemeManager;
import com.extjs.gxt.ui.client.widget.menu.CheckMenuItem;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.smartgxt.client.core.SmartGXT;
import com.smartgxt.client.messages.Localization;
import com.smartgxt.client.ui.widgets.menus.MenuItem;

/**
 * @author Anton Alexeyev
 * 
 */
public class ThemeSelectorMenuItem extends MenuItem {

	private Theme last;
	private Menu menu;

	public ThemeSelectorMenuItem() {
		menu = new Menu();
		setText(Localization.get().themeSelector());
		setSubMenu(menu);

	}

	@Override
	protected void beforeRender() {
		super.beforeRender();
		String theme = SmartGXT.getThemeId();
		if (theme == null) {
			theme = Theme.BLUE.getId();
		}
		for (Theme l : ThemeManager.getThemes()) {
			CheckMenuItem menuItem = new CheckMenuItem();
			menuItem.setGroup("ThemeSelectorMenuItemRadio");
			menuItem.setText(l.getName());
			menuItem.setData("sgxt.theme", l);
			if (l.getId().equals(theme)) {
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
			Theme c = ce.getItem().getData("sgxt.theme");
			if (c != last) {
				last = c;
				SmartGXT.switchTheme(c);
			}
		}
	};
}
