package com.smartgxt.ui.client.grids;

import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.LiveToolItem;

public class LiveGrid<M> extends BaseGrid<M> {

	protected LiveToolItem toolBar;

	public LiveGrid(ListStore<M> store, ColumnModel<M> cm) {
		super(store, cm, new LiveGridView<M>());
		initGUI();
	}

	public LiveGrid(ListStore<M> store) {
		super(store, new LiveGridView<M>());
		initGUI();
	}

	private void initGUI() {
		toolBar = new LiveToolItem(grid);
		toolBar.getElement().getStyle().setProperty("borderBottom", "none");

		con.add(toolBar, new VerticalLayoutData(1, -1));
	}

	@SuppressWarnings("unchecked")
	public PagingLoader<? extends PagingLoadConfig, ?> getLoader() {
		return (PagingLoader<? extends PagingLoadConfig, ?>) super.getLoader();
	}

	public void setLoader(PagingLoader<? extends PagingLoadConfig, ?> loader) {
		super.setLoader(loader);
	}

	/**
	 * Returns the grid's view.
	 * 
	 * @return the grid view
	 */
	public LiveGridView<M> getView() {
		return (LiveGridView<M>) grid.getView();
	}
}
