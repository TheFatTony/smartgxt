package com.smartgxt.client.ui.widgets.grids.plugins;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.ComponentPlugin;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Event;

/**
 * @author Anton Alexeyev
 * 
 */
public class GridHeaderRigthClickPlugin implements ComponentPlugin {

	private com.extjs.gxt.ui.client.widget.grid.Grid<?> grid;
	private int x;
	private int y;
	private boolean disableGridMenu = false;
	private int clickedCol;
	private Menu contextMenu;

	public GridHeaderRigthClickPlugin() {
	}

	@Override
	public void init(Component component) {
		assert component instanceof Grid<?> : "GridForceFitPlugin can only be used with a Grid.";
		this.grid = (Grid<?>) component;
		initListeners();
	}

	public void initListeners() {
		grid.addListener(Events.HeaderContextMenu, onHeaderContextMenu);
		grid.addListener(Events.HeaderMouseDown, onHeaderMouseDown);

		grid.addListener(Events.ViewReady, onViewReady);
	}

	private Listener<GridEvent<?>> onHeaderContextMenu = new Listener<GridEvent<?>>() {
		@Override
		public void handleEvent(final GridEvent<?> be) {
			Scheduler.get().scheduleDeferred(new Command() {
				@Override
				public void execute() {
					be.getMenu().setPosition(x, y);
				}
			});
		}
	};

	private Listener<GridEvent<?>> onHeaderMouseDown = new Listener<GridEvent<?>>() {
		@Override
		public void handleEvent(GridEvent<?> be) {
			x = be.getClientX();
			y = be.getClientY();
			if (be.getEvent().getButton() == Event.BUTTON_RIGHT) {
				clickedCol = be.getColIndex();
				if (contextMenu != null) {
					disableGridMenu = true;
				} else {
					// TODO lags without context menu
					grid.getView().getHeader().showColumnMenu(clickedCol);
				}
			}
		}
	};

	private Listener<GridEvent<?>> onViewReady = new Listener<GridEvent<?>>() {
		@Override
		public void handleEvent(GridEvent<?> be) {
			contextMenu = grid.getContextMenu();
			if (contextMenu != null) {
				contextMenu.removeListener(Events.BeforeShow, onMenuBeforeShow);
				contextMenu.addListener(Events.BeforeShow, onMenuBeforeShow);
			}
		}
	};

	private Listener<MenuEvent> onMenuBeforeShow = new Listener<MenuEvent>() {
		@Override
		public void handleEvent(MenuEvent be) {
			if (disableGridMenu) {
				disableGridMenu = false;
				be.setCancelled(true);
				grid.getView().getHeader().showColumnMenu(clickedCol);
			}
		}
	};

}
