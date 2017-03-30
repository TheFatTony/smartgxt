/**
 * 
 */
package com.smartgxt.service;

import java.io.Serializable;

import com.smartgxt.shared.events.RequestType;

/**
 * @author Anton Alexeyev
 * 
 */

public interface RemoteService extends
		com.google.gwt.user.client.rpc.RemoteService {

	Serializable doRpcCall(RequestType type, Serializable request)
			throws Throwable;

}
