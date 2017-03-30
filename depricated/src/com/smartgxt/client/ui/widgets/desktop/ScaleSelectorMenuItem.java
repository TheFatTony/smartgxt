package com.smartgxt.client.ui.widgets.desktop;

import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.menu.CheckMenuItem;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.smartgxt.client.core.SmartGXT;
import com.smartgxt.client.managers.Scale;
import com.smartgxt.client.managers.ScaleManager;
import com.smartgxt.client.messages.Localization;
import com.smartgxt.client.ui.widgets.menus.MenuItem;

/**
 * @author Anton Alexeyev
 * 
 */
public class ScaleSelectorMenuItem extends MenuItem {

	private Scale last;
	private Menu menu;

	public ScaleSelectorMenuItem() {
		menu = new Menu();
		setText(Localization.get().scaleSelector());
		setSubMenu(menu);

	}

	@Override
	protected void beforeRender() {
		super.beforeRender();

		String scale = SmartGXT.getScaleId();
		if (scale == null) {
			scale = Scale.NORMAL.getId();
		}
		for (Scale l : ScaleManager.getScales()) {
			CheckMenuItem menuItem = new CheckMenuItem();
			menuItem.setGroup("ScaleSelectorMenuItemRadio");
			menuItem.setText(l.getName());
			menuItem.setData("sgxt.scale", l);
			if (l.getId().equals(scale)) {
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
			Scale c = ce.getItem().getData("sgxt.scale");
			if (c != last) {
				last = c;
				SmartGXT.switchScale(c);
			}
		}
	};
}
