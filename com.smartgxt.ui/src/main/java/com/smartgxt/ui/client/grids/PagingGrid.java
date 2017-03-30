package com.smartgxt.ui.client.grids;

import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.toolbar.PagingToolBar;

public class PagingGrid<M> extends BaseGrid<M> {

	protected PagingToolBar toolBar;

	public PagingGrid(ListStore<M> store, ColumnModel<M> cm, int pageSize) {
		super(store, cm, new GridView<M>());
		initGUI(pageSize);
	}

	public PagingGrid(ListStore<M> store, int pageSize) {
		super(store, new GridView<M>());
		initGUI(pageSize);
	}

	private void initGUI(int pageSize) {
		toolBar = new PagingToolBar(pageSize);
		toolBar.getElement().getStyle().setProperty("borderBottom", "none");

		con.add(toolBar, new VerticalLayoutData(1, -1));
	}
	
	@SuppressWarnings("unchecked")
	public PagingLoader<? extends PagingLoadConfig, ?> getLoader() {
		return (PagingLoader<? extends PagingLoadConfig, ?>) super.getLoader();
	}
	
	public void setLoader(PagingLoader<? extends PagingLoadConfig, ?> loader) {
		super.setLoader(loader);
		toolBar.bind(loader);
	}

}
