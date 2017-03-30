package com.smartgxt.client.ui.widgets.desktop.dashboard;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.menu.CheckMenuItem;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.smartgxt.client.messages.Localization;

/**
 * @author Anton Alexeyev
 * 
 */
public class ConfigureDashboardMenuItem extends MenuItem {

	private Menu menu;
	private Dashboard dashboard;

	public ConfigureDashboardMenuItem() {
		menu = new Menu();
		setText(Localization.get().dashboard());
		setSubMenu(menu);
	}

	@Override
	protected void beforeRender() {
		super.beforeRender();
		MenuItem menuItem = new MenuItem();
		menuItem.setText(Localization.get().dashboard_Columns());
		menuItem.setSubMenu(createColumnsSubMenu());
		menu.add(menuItem);

		menuItem = new MenuItem();
		menuItem.setText(Localization.get().dashboard_Widgets());
		menuItem.setSubMenu(createPanelsSubMenu());
		menu.add(menuItem);
	}

	protected Menu createPanelsSubMenu() {
		Menu items = new Menu();
		items.addListener(Events.Show, new Listener<MenuEvent>() {
			@Override
			public void handleEvent(MenuEvent be) {
				for (Component c : be.getMenu().getItems()) {
					CheckMenuItem mi = (CheckMenuItem) c;
					InformationWidget panel = mi.getData("sgxt.panel");
					mi.setChecked(panel.isVisible());
				}
			}
		});
		for (final InformationWidget panel : dashboard.getPanels()) {
			CheckMenuItem item1 = new CheckMenuItem();
			item1.setText(panel.getHeading());
			item1.setData("sgxt.panel", panel);
			item1.addSelectionListener(new SelectionListener<MenuEvent>() {
				@Override
				public void componentSelected(MenuEvent ce) {
					if (panel.isVisible())
						panel.hide();
					else
						panel.show();
				}
			});
			items.add(item1);
		}

		return items;
	}

	protected Menu createColumnsSubMenu() {
		final MenuItem item1 = new MenuItem();
		final MenuItem item2 = new MenuItem();
		Menu items = new Menu();
		menu.addListener(Events.Show, new Listener<MenuEvent>() {
			@Override
			public void handleEvent(MenuEvent be) {
				disableEnableItem(item2);
			}
		});

		item1.setText(Localization.get().dashboard_WidgetsAdd());
		item1.addSelectionListener(new SelectionListener<MenuEvent>() {
			@Override
			public void componentSelected(MenuEvent ce) {
				dashboard.addColumn();
				dashboard.defaultSizeColumns();
			}
		});

		items.add(item1);

		item2.setText(Localization.get().dashboard_WidgetsRemove());
		item2.addSelectionListener(new SelectionListener<MenuEvent>() {
			@Override
			public void componentSelected(MenuEvent ce) {
				dashboard.removeColumn();
				dashboard.defaultSizeColumns();
			}
		});
		items.add(item2);

		return items;
	}

	public void disableEnableItem(MenuItem item) {
		if (dashboard.getItemCount() == 1)
			item.disable();
		else
			item.enable();
	}

	public void setDashboard(Dashboard dashboard) {
		this.dashboard = dashboard;
	}

	public Dashboard getDashboard() {
		return dashboard;
	}

}
