package com.smartgxt.client.ui.widgets.grids.plugins;

import java.util.HashMap;
import java.util.Map;

import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.data.LoadEvent;
import com.extjs.gxt.ui.client.data.Loader;
import com.extjs.gxt.ui.client.event.ColumnModelEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.widget.ComponentPlugin;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.tips.QuickTip;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Event;

/**
 * @author Anton Alexeyev
 * 
 */
@Deprecated
public class GridTweaker {

	private com.extjs.gxt.ui.client.widget.grid.Grid<?> grid;
	private int x;
	private int y;
	private boolean disableGridMenu = false;
	private int clickedCol;
	private Menu contextMenu;

	private int scrollX = 0;

	// TODO smart grid header tooltip

	public GridTweaker(com.extjs.gxt.ui.client.widget.grid.Grid<?> grid) {
		this.grid = grid;
		applyConfiguration();
		extendState();
		updateColumnListners(grid.getColumnModel());
		initListeners();
	}

	public void applyConfiguration() {
		// grid.setId("ds-grid");
		grid.addStyleName("ux-grid");
		grid.setStripeRows(true);
		grid.setColumnLines(true);
		grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		grid.setTrackMouseOver(false);
		grid.disableTextSelection(false);
		grid.setColumnReordering(true);
		@SuppressWarnings("unused")
		QuickTip qt = new QuickTip(grid);
	}

	private void extendState() {
		grid.addListener(Events.StateRestore, onStateRestore);
	}

	private Listener<ComponentEvent> onStateRestore = new Listener<ComponentEvent>() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleEvent(ComponentEvent be) {
			Map<String, Integer> columnReorderMap = (Map<String, Integer>) grid
					.getState().get("columnReorder");
			if (columnReorderMap != null) {
				Map<String, Integer> oldIndexes = new HashMap<String, Integer>();

				int k = 0;
				for (ColumnConfig cc : grid.getColumnModel().getColumns()) {
					oldIndexes.put(cc.getId(), k);
					k++;
				}

				for (String s : columnReorderMap.keySet()) {
					ColumnModel cm = grid.getColumnModel();
					int oldPos = oldIndexes.get(s);
					cm.moveColumn(oldPos, columnReorderMap.get(s));
				}
			}

			Object o = grid.getState().get("isAutoFill");
			if (o != null) {
				boolean b = (Boolean) o;
				for (ComponentPlugin p : grid.getPlugins())
					if (p instanceof GridForceFitPlugin) {
						GridForceFitPlugin pluging = ((GridForceFitPlugin) p);
						pluging.setAutoFill(b);
						pluging.getAutoFillItem().setChecked(b);
					}
			}
		}
	};

	private Listener<ColumnModelEvent> onColumnMove = new Listener<ColumnModelEvent>() {
		@Override
		public void handleEvent(ColumnModelEvent be) {
			Map<String, Object> state = grid.getState();
			Map<String, Integer> columnReorderMap = null;

			columnReorderMap = new HashMap<String, Integer>();

			int k = 0;
			for (ColumnConfig cc : be.getColumnModel().getColumns()) {
				columnReorderMap.put(cc.getId(), k);
				k++;
			}

			state.put("columnReorder", columnReorderMap);
			grid.saveState();
		}
	};

	public void initListeners() {
		grid.addListener(Events.HeaderContextMenu, onHeaderContextMenu);
		grid.addListener(Events.HeaderMouseDown, onHeaderMouseDown);
		grid.addListener(Events.Reconfigure, onReconfigure);

		// grid.addListener(Events.Attach, onAttach);
		grid.addListener(Events.ColumnResize, onColumnResize);
		grid.addListener(Events.ViewReady, onViewReady);
		if ((grid.getStore() != null) && (grid.getStore().getLoader() != null)) {
			grid.getStore().getLoader().addListener(Loader.Load, onLoad);
			grid.getStore().getLoader()
					.addListener(Loader.BeforeLoad, onBeforeLoad);
		}
	}

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

	private Listener<LoadEvent> onLoad = new Listener<LoadEvent>() {
		@Override
		public void handleEvent(LoadEvent be) {
			grid.getView().getBody().setScrollLeft(scrollX);
		}
	};

	private Listener<LoadEvent> onBeforeLoad = new Listener<LoadEvent>() {
		@Override
		public void handleEvent(LoadEvent be) {
			scrollX = grid.getView().getScrollState().x;
		}
	};

	private Listener<GridEvent<?>> onColumnResize = new Listener<GridEvent<?>>() {
		@Override
		public void handleEvent(GridEvent<?> be) {
			grid.getView().refresh(false);
		}
	};

	// private Listener<ComponentEvent> onAttach = new
	// Listener<ComponentEvent>() {
	// @Override
	// public void handleEvent(ComponentEvent be) {
	// }
	// };

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

	private Listener<GridEvent<?>> onReconfigure = new Listener<GridEvent<?>>() {
		@Override
		public void handleEvent(GridEvent<?> be) {
			updateColumnListners(grid.getColumnModel());
		}
	};

	private void updateColumnListners(ColumnModel cm) {
		if (grid.getColumnModel() != null) {
			grid.getColumnModel().removeListener(Events.ColumnMove,
					onColumnMove);
			grid.getColumnModel().addListener(Events.ColumnMove, onColumnMove);
		}
	}

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

}
