package com.smartgxt.ui.client.data;

import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.smartgxt.core.shared.events.RequestType;

public class RpcPagingLoader<D> extends
		BaseRpcPagingLoader<FilterPagingLoadConfig, PagingLoadResult<D>> {

	public RpcPagingLoader(RpcProxy<FilterPagingLoadConfig, PagingLoadResult<D>> proxy) {
		super(proxy);
	}

	public RpcPagingLoader(String call) {
		super(new RpcProxy<FilterPagingLoadConfig, PagingLoadResult<D>>(call));
	}

	public RpcPagingLoader(RequestType type) {
		super(new RpcProxy<FilterPagingLoadConfig, PagingLoadResult<D>>(type));
	}

}
