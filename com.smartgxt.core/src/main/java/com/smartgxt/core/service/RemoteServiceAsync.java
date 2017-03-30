package com.smartgxt.core.service;

import java.io.Serializable;

import com.smartgxt.core.shared.events.RequestType;
import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * @author Anton Alexeyev
 * 
 */

public interface RemoteServiceAsync {

	void doRpcCall(RequestType type, Serializable request, AsyncCallback<Serializable> callback);

}
