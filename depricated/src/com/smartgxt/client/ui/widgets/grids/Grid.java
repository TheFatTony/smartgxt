package com.smartgxt.client.ui.widgets.grids;

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.grid.BufferView;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.smartgxt.client.ui.widgets.grids.plugins.GridForceFitPlugin;
import com.smartgxt.client.ui.widgets.grids.plugins.GridHeaderRigthClickPlugin;
import com.smartgxt.client.ui.widgets.grids.plugins.GridPrettyLookPlugin;
import com.smartgxt.client.ui.widgets.grids.plugins.GridRefreshOnColumnResizePlugin;
import com.smartgxt.client.ui.widgets.grids.plugins.GridStateExtendTweaker;

/**
 * @author Anton Alexeyev
 * 
 */
public class Grid extends com.extjs.gxt.ui.client.widget.grid.Grid<ModelData> {

	private BufferView view;
	private boolean autoLoad = false;

	public Grid(ListStore<ModelData> store, ColumnModel cm) {
		super(store, cm);
		view = new BufferView();
		setView(view);
		addListener(Events.ViewReady, onViewReady);

		new GridStateExtendTweaker(this);

		addPlugin(new GridPrettyLookPlugin());
		addPlugin(new GridForceFitPlugin());
		addPlugin(new GridHeaderRigthClickPlugin());
		addPlugin(new GridRefreshOnColumnResizePlugin());
	}

	private Listener<GridEvent<?>> onViewReady = new Listener<GridEvent<?>>() {
		@Override
		public void handleEvent(GridEvent<?> be) {
			if (isAutoLoad())
				getStore().getLoader().load();
		}
	};

	public BufferView getBufferView() {
		return view;
	}

	/**
	 * True to enabled buffered functionality (defaults to true).
	 * 
	 * @param bufferEnabled
	 *            true to buffer, otherwise false
	 */
	public void setBufferEnabled(boolean bufferEnabled) {
		getBufferView().setBufferEnabled(false);
	}

	public void setAutoLoad(boolean autoload) {
		this.autoLoad = autoload;
	}

	public boolean isAutoLoad() {
		return autoLoad;
	}

}
