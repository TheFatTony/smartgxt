package com.smartgxt.ui.client.data;

import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.smartgxt.core.shared.events.RequestType;

public class BaseRpcPagingLoader<C extends PagingLoadConfig, D extends PagingLoadResult<?>>
		extends PagingLoader<C, D> {

	public BaseRpcPagingLoader(RpcProxy<C, D> proxy) {
		super(proxy);
	}

	public BaseRpcPagingLoader(String call) {
		super(new RpcProxy<C, D>(call));
	}

	public BaseRpcPagingLoader(RequestType type) {
		super(new RpcProxy<C, D>(type));
	}

}
