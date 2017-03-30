package com.smartgxt.client.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.extjs.gxt.ui.client.util.Util;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RpcRequestBuilder;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.smartgxt.client.core.Session;
import com.smartgxt.client.core.SmartGXT;
import com.smartgxt.service.RemoteService;
import com.smartgxt.service.RemoteServiceAsync;
import com.smartgxt.shared.data.FileDefenition;
import com.smartgxt.shared.events.ReflectionRequestType;
import com.smartgxt.shared.events.RequestType;

/**
 * @author Anton Alexeyev
 * 
 */
public abstract class RpcProxy<D> extends
		com.extjs.gxt.ui.client.data.RpcProxy<D> {
	private String call;
	private RequestType type;

	private static RemoteServiceAsync service;
	private static ServiceDefTarget endPoint;
	private static RpcRequestBuilder requestBuilder;

	private boolean isCached = false;
	private CacheMechanisms cacheMechanisms = CacheMechanisms.MEMORY;
	protected static Map<MemoryKey, Object> inMemoryCache;

	static {
		inMemoryCache = new HashMap<MemoryKey, Object>();

		requestBuilder = new RpcRequestBuilder();
		service = (RemoteServiceAsync) GWT.create(RemoteService.class);
		endPoint = (ServiceDefTarget) service;
		endPoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "gwtservlet");
		endPoint.setRpcRequestBuilder(requestBuilder);
	}

	public RpcProxy() {
	}

	public RpcProxy(String call) {
		setCall(call);
	}

	public RpcProxy(RequestType type) {
		this.type = type;
	}

	public Object getFromMemory(RequestType requestType, Object loadConfig) {
		for (MemoryKey key : inMemoryCache.keySet()) {
			if ((key.getRequestType().getEventCode() == requestType
					.getEventCode())
					&& Util.equalWithNull(loadConfig, key.getLoadConfig()))
				return inMemoryCache.get(key);
		}
		return null;
	}

	public void addToMemory(MemoryKey key, Object data) {
		inMemoryCache.put(key, data);
	}

	@Override
	protected void load(Object loadConfig, final AsyncCallback<D> callback) {
		// if (isCached()) {
		// ModelData resp = getMemory(loadConfig);
		// if (resp != null) {
		// D data = extract(resp);
		// callback.onSuccess(data);
		// } else {
		// executeCall(type, (ModelData) loadConfig, callback);
		// }

		if (!isCached()) {
			executeCall(type, (Serializable) loadConfig, callback);
		} else {

			Object resp = getFromMemory(type, loadConfig);
			if (resp != null) {
				System.out.println("Retrive object from client cache");
				// D data = extract(resp);
				@SuppressWarnings("unchecked")
				D data = (D) resp;
				callback.onSuccess(data);
			} else {
				executeCall(type, (Serializable) loadConfig, callback);
			}
		}
	}

	public static void downloadFile(String url) {
		com.google.gwt.user.client.Window.open(GWT.getModuleBaseURL() + url,
				null, null);
	}

	public static void downloadFile(FileDefenition file) {
		com.google.gwt.user.client.Window.open(
				GWT.getModuleBaseURL() + "httpservlet?version="
						+ SmartGXT.getVersion() + "&jsessionid="
						+ Session.getSesionId() + "&file_id="
						+ file.getFileId(), null, null);
		// if (GXT.isChrome) {
		// System.out.println("WebKit");
		// com.google.gwt.user.client.Window.open("chrome://downloads/", null,
		// null);
		// }
	}

	private void executeCall(final RequestType type,
			final Serializable request, final AsyncCallback<D> callback) {
		if (SmartGXT.isOnline()) {
			type.setVersion(SmartGXT.getVersion());
			type.setSessionId(Session.getSesionId());

			try {
				getService().doRpcCall(type, request,
						new AsyncCallback<Serializable>() {

							@Override
							public void onFailure(Throwable caught) {
								onError(callback, caught);
							}

							@SuppressWarnings("unchecked")
							@Override
							public void onSuccess(Serializable result) {
								if (isCached())
									addToMemory(new MemoryKey(type, request),
											result);
								// D data = extract(result);
								RpcProxy.this.onSuccess(callback, (D) result);
							}
						});
			} catch (Throwable e) {
				onError(callback, e);
			}
		}

	}

	public static RemoteServiceAsync getService() {
		return service;
	}

	protected void onSuccess(AsyncCallback<D> callback, D data) {
		callback.onSuccess(data);
	}

	protected void onError(AsyncCallback<D> callback, Throwable caught) {
		// if (caught instanceof GwtServletException) {
		// GwtServletException e = (GwtServletException) caught;
		// if (e.getSeededException() != null)
		// callback.onFailure(e.getSeededException());
		// else
		// callback.onFailure(caught);
		// } else
		callback.onFailure(caught);
		// Listener<MessageBoxEvent> listener = null;
		// String error = caught.getMessage();
		// if (error == null)
		// error = caught.toString();
		// TODO localization
		// MessageBox.alert("Ошибка", error, listener);
	}

	public String getCall() {
		return call;
	}

	public void setCall(String call) {
		this.call = call;
		this.type = new ReflectionRequestType(call);
	}

	public RequestType getType() {
		return type;
	}

	public void setType(RequestType type) {
		this.type = type;
	}

	// abstract public D extract(Object data);

	public CacheMechanisms getCacheMechanisms() {
		return cacheMechanisms;
	}

	public void setCacheMechanisms(CacheMechanisms cacheMechanisms) {
		this.cacheMechanisms = cacheMechanisms;
	}

	public boolean isCached() {
		return isCached;
	}

	public void setCached(boolean isCached) {
		this.isCached = isCached;
	}

}
