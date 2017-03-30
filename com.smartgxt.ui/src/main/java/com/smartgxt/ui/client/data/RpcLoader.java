package com.smartgxt.ui.client.data;

import com.sencha.gxt.data.shared.loader.Loader;
import com.smartgxt.core.shared.events.RequestType;

//import com.smartgxt.core.shared.events.RequestType;

/**
 * @author Anton Alexeyev
 * 
 */
public class RpcLoader<C, M> extends Loader<C, M> {
	public RpcLoader(String call) {
		super(new RpcProxy<C, M>(call) {
		});
		new LoaderErrorMessage(this);
	}

	public RpcLoader(RequestType type) {
		super(new RpcProxy<C, M>(type) {
		});
		new LoaderErrorMessage(this);
	}

}
