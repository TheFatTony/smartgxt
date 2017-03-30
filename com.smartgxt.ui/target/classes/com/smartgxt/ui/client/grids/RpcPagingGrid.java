package com.smartgxt.ui.client.grids;

import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.smartgxt.core.shared.events.RequestType;
import com.smartgxt.ui.client.data.ModelWithKeyProvider;
import com.smartgxt.ui.client.data.RpcPagingLoader;
import com.smartgxt.ui.shared.KeyBean;

public class RpcPagingGrid<M extends KeyBean> extends PagingGrid<M> {

	private RpcPagingLoader<M> gridLoader;

	public RpcPagingGrid(String call, ColumnModel<M> cm, int pageSize) {
		super(new ListStore<M>(new ModelWithKeyProvider<M>()), cm, pageSize);
		newGridLoader(call, getStore());
	}

	public RpcPagingGrid(RequestType type, ColumnModel<M> cm, int pageSize) {
		super(new ListStore<M>(new ModelWithKeyProvider<M>()), cm, pageSize);
		newGridLoader(type, getStore());
	}

	public RpcPagingGrid(String call, int pageSize) {
		super(new ListStore<M>(new ModelWithKeyProvider<M>()),
				new ModifiableColumnModel<M>(), pageSize);
		newGridLoader(call, getStore());
	}

	public RpcPagingGrid(RequestType type, int pageSize) {
		super(new ListStore<M>(new ModelWithKeyProvider<M>()),
				new ModifiableColumnModel<M>(), pageSize);
		newGridLoader(type, getStore());
	}

	private void newGridLoader(RequestType type, ListStore<M> store) {
		gridLoader = new RpcPagingLoader<M>(type);
		gridLoader.setRemoteSort(true);
		gridLoader
				.addLoadHandler(new LoadResultListStoreBinding<FilterPagingLoadConfig, M, PagingLoadResult<M>>(
						store));
		setLoader(gridLoader);
	}

	private void newGridLoader(String call, ListStore<M> store) {
		gridLoader = new RpcPagingLoader<M>(call);
		gridLoader.setRemoteSort(true);
		gridLoader
				.addLoadHandler(new LoadResultListStoreBinding<FilterPagingLoadConfig, M, PagingLoadResult<M>>(
						store));
		setLoader(gridLoader);
	}

	public RpcPagingGrid(ListStore<M> store, int pageSize) {
		super(store, pageSize);
	}

	@Override
	public RpcPagingLoader<M> getLoader() {
		return (RpcPagingLoader<M>) super.getLoader();
	}

}
