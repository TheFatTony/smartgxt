package com.smartgxt.ui.client.data;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgxt.core.client.rpc.RequestProcessor;
import com.smartgxt.core.shared.events.RequestType;

//import com.google.gwt.user.client.rpc.AsyncCallback;
//import com.smartgxt.core.client.rpc.RequestProcessor;
//import com.smartgxt.core.shared.events.RequestType;

public class RpcProxy<C, D> extends
		com.sencha.gxt.data.client.loader.RpcProxy<C, D> {

	private RequestProcessor<C, D> processor;

	public RpcProxy(String call) {
		processor = new RequestProcessor<C, D>(call);
	}

	public RpcProxy(RequestType type) {
		processor = new RequestProcessor<C, D>(type);
	}

	@Override
	public void load(C loadConfig, AsyncCallback<D> callback) {
		processor.execute(loadConfig, callback);

	}

}
