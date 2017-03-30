package com.smartgxt.client.ui.widgets.grids.plugins;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.util.Point;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.ComponentPlugin;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.menu.CheckMenuItem;

/**
 * @author Anton Alexeyev
 * 
 */
public class GridRefreshOnColumnResizePlugin implements ComponentPlugin {

	protected Grid<?> grid;
	protected CheckMenuItem autoFillItem;

	// TODO restore original sizes
	// TODO add Width adjusting

	public GridRefreshOnColumnResizePlugin() {
	}

	@Override
	public void init(Component component) {
		assert component instanceof Grid<?> : "GridForceFitPlugin can only be used with a Grid.";
		this.grid = (Grid<?>) component;
		grid.addListener(Events.ColumnResize, onColumnResize);
	}

	private Listener<GridEvent<?>> onColumnResize = new Listener<GridEvent<?>>() {
		@Override
		public void handleEvent(GridEvent<?> be) {
			Point p = grid.getView().getScrollState();
			grid.getView().refresh(false);
			grid.getView().getScroller().scrollTo("top", p.y);
			grid.getView().getScroller().scrollTo("left", p.x);
		}
	};
}
