package com.smartgxt.client.data;

import com.extjs.gxt.ui.client.data.BaseFilterPagingLoadConfig;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.ModelReader;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.store.ListStore;
import com.smartgxt.shared.events.RequestType;

/**
 * @author Anton Alexeyev
 * 
 */
public class PaggingListStore extends ListStore<ModelData> {

	public PaggingListStore(String call) {
		super(new BasePagingLoader<PagingLoadResult<ModelData>>(
				new RpcProxy<PagingLoadResult<ModelData>>(call) {

				}, new ModelReader()) {
			@Override
			protected Object newLoadConfig() {
				return new BaseFilterPagingLoadConfig();
			}
		});
		new LoaderErrorMessage(getLoader());
		getLoader().setRemoteSort(true);

	}

	public PaggingListStore(RequestType type) {
		super(new BasePagingLoader<PagingLoadResult<ModelData>>(
				new RpcProxy<PagingLoadResult<ModelData>>(type) {

				}, new ModelReader()) {
			@Override
			protected Object newLoadConfig() {
				return new BaseFilterPagingLoadConfig();
			}
		});
		new LoaderErrorMessage(getLoader());
		getLoader().setRemoteSort(true);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BasePagingLoader<PagingLoadResult<ModelData>> getLoader() {
		return (BasePagingLoader<PagingLoadResult<ModelData>>) super
				.getLoader();
	}

}
