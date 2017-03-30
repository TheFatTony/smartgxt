package com.smartgxt.client.ui.widgets.grids.filters;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.ComponentPlugin;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.SeparatorMenuItem;

/**
 * @author Anton Alexeyev
 * 
 */
public class AbstractColumnsFilters implements ComponentPlugin {

	protected Grid<?> grid;

	// GridFilters

	@Override
	public void init(Component component) {
		assert component instanceof Grid<?> : "GridFilters can only be used with a Grid.";
		this.grid = (Grid<?>) component;
		grid.addListener(Events.HeaderContextMenu, onHeaderContextMenu);
	}

	private Listener<GridEvent<?>> onHeaderContextMenu = new Listener<GridEvent<?>>() {
		public void handleEvent(GridEvent<?> be) {
			Menu headerContextMenu = be.getMenu();
			headerContextMenu.add(new SeparatorMenuItem());

		}
	};
}
