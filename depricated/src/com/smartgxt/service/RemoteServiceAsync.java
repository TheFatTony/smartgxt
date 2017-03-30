/**
 * 
 */
package com.smartgxt.service;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgxt.shared.events.RequestType;

/**
 * @author Anton Alexeyev
 * 
 */
public interface RemoteServiceAsync {

	void doRpcCall(RequestType type, Serializable request,
			AsyncCallback<Serializable> callback) throws Throwable;

}
