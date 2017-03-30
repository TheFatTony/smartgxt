package com.smartgxt.client.ui.widgets.grids.plugins;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.ComponentPlugin;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.menu.CheckMenuItem;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;

/**
 * @author Anton Alexeyev
 * 
 */
public class GridForceFitPlugin implements ComponentPlugin {

	protected Grid<?> grid;
	protected CheckMenuItem autoFillItem;

	// TODO restore original sizes
	// TODO add Width adjusting

	public GridForceFitPlugin() {
		autoFillItem = new CheckMenuItem("Auto fill columns");
		autoFillItem.addSelectionListener(onMenuSelection);
	}

	private SelectionListener<MenuEvent> onMenuSelection = new SelectionListener<MenuEvent>() {
		@Override
		public void componentSelected(MenuEvent ce) {
			boolean b = autoFillItem.isChecked();
			grid.getState().put("isAutoFill", b);
			grid.saveState();
			setAutoFill(b);
		}
	};

	public void setAutoFill(final boolean isAutoFill) {
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				grid.getView().setForceFit(isAutoFill);
				grid.getView().layout();
			}
		});

	}

	@Override
	public void init(Component component) {
		assert component instanceof Grid<?> : "GridForceFitPlugin can only be used with a Grid.";
		this.grid = (Grid<?>) component;
		grid.removeListener(Events.HeaderContextMenu, onHeaderContextMenu);
		grid.addListener(Events.HeaderContextMenu, onHeaderContextMenu);
		// grid.addListener(Events.StateRestore, onStateRestore);
	}

	// private Listener<ComponentEvent> onStateRestore = new
	// Listener<ComponentEvent>() {
	// @Override
	// public void handleEvent(ComponentEvent be) {
	// System.out.println("state restore");
	// Object o = grid.getState().get("isAutoFill");
	// if (o != null) {
	// boolean b = (Boolean) o;
	// setAutoFill(b);
	// isAutoFill.setChecked(b);
	// }
	// }
	// };

	private Listener<GridEvent<?>> onHeaderContextMenu = new Listener<GridEvent<?>>() {
		public void handleEvent(GridEvent<?> be) {
			Menu headerContextMenu = be.getMenu();
			headerContextMenu.add(autoFillItem);
		}
	};

	public CheckMenuItem getAutoFillItem() {
		return autoFillItem;
	}

}
