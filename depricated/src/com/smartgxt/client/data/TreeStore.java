package com.smartgxt.client.data;

import com.extjs.gxt.ui.client.data.TreeModel;

/**
 * @author Anton Alexeyev
 * 
 */
@Deprecated
public class TreeStore extends
		com.extjs.gxt.ui.client.store.TreeStore<TreeModel> {

	// private RequestManager requestManager;
	private String call;

	public TreeStore(String call) {
		super();
		// requestManager = RequestManager.newManager();
		initListeners();
		this.setCall(call);
	}

	private void initListeners() {
		// requestManager.addListener(RequestManager.onSuccessProcess,
		// new Listener<CallableEvent>() {
		// @Override
		// public void handleEvent(CallableEvent be) {
		// @SuppressWarnings("unchecked")
		// List<TreeModel> plr = (List<TreeModel>) be
		// .getResponse().get("query");
		// removeAll();
		// add(plr, true);
		// }
		// });
		// requestManager.addListener(RequestManager.onFailureProcess,
		// new Listener<CallableEvent>() {
		// @Override
		// public void handleEvent(CallableEvent be) {
		// // TODO normal error handling
		// }
		// });
	}

	public void load() {
		// RpcProxyRequestData data = new RpcProxyRequestData(getCall());
		// // if (getCall() != null) {
		// requestManager.processRequest(RpcEvents.Reflection, data);
		// // } else {
		// // requestManager.processRequest(type, data);
		// // }

	}

	public void setCall(String call) {
		this.call = call;
	}

	public String getCall() {
		return call;
	}
}
