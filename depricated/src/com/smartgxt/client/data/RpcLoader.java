package com.smartgxt.client.data;

import com.smartgxt.shared.events.RequestType;

/**
 * @author Anton Alexeyev
 * 
 */
public class RpcLoader<D> extends com.extjs.gxt.ui.client.data.BaseLoader<D> {

	public RpcLoader(String call) {
		super(new RpcProxy<D>(call) {
		});
		new LoaderErrorMessage(this);
	}

	public RpcLoader(RequestType type) {
		super(new RpcProxy<D>(type) {
		});
		new LoaderErrorMessage(this);
	}

}
