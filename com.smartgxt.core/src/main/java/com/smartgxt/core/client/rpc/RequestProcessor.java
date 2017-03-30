package com.smartgxt.core.client.rpc;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RpcRequestBuilder;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.smartgxt.core.client.Session;
import com.smartgxt.core.client.SmartGXT;
import com.smartgxt.core.client.messages.Localization;
import com.smartgxt.core.client.rpc.FailureEvent.FailureHandler;
import com.smartgxt.core.client.rpc.SuccessEvent.SuccessHandler;
import com.smartgxt.core.service.RemoteService;
import com.smartgxt.core.service.RemoteServiceAsync;
import com.smartgxt.core.shared.data.IsConverter;
import com.smartgxt.core.shared.events.ReflectionRequestType;
import com.smartgxt.core.shared.events.RequestType;
import com.smartgxt.core.shared.exceptions.ApplicationOfflineException;

//public class RequestProcessor<T extends RequestType, Q, S> {
public class RequestProcessor<Q, S> {

	private String call;
	private RequestType type;

	private IsConverter<Object, S> responseConverter;

	private boolean isCached = false;
	protected static Map<MemoryKey<?>, Object> inMemoryCache;

	private static RemoteServiceAsync service;
	private static ServiceDefTarget endPoint;
	private static RpcRequestBuilder requestBuilder;

	private boolean throwExceptions = false;

	private SimpleEventBus eventBus;

	static {
		inMemoryCache = new HashMap<MemoryKey<?>, Object>();

		requestBuilder = new RpcRequestBuilder();
		service = (RemoteServiceAsync) GWT.create(RemoteService.class);
		endPoint = (ServiceDefTarget) service;
		endPoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "gwtservlet");
		endPoint.setRpcRequestBuilder(requestBuilder);
	}

	public RequestProcessor(String call) {
		setCall(call);
	}

	public RequestProcessor(RequestType type) {
		setType(type);
	}

	public static String getHttpServletUrl() {
		return GWT.getModuleBaseURL() + "httpservlet?version="
				+ SmartGXT.getVersion() + "&jsessionid="
				+ Session.getSesionId();
	}

	public SimpleEventBus ensureHandlers() {
		return eventBus == null ? eventBus = new SimpleEventBus() : eventBus;
	}

	public HandlerRegistration addSuccessHandler(SuccessHandler<S> handler) {
		return ensureHandlers().addHandler(SuccessEvent.getType(), handler);
	}

	public HandlerRegistration addFailureHandler(
			FailureHandler<Throwable> handler) {
		return ensureHandlers().addHandler(FailureEvent.getType(), handler);
	}

	public void execute(Q request) {
		AsyncCallback<S> callback = null;
		execute(request, callback);
	}

	public void execute(Q request, final AsyncCallback<S> callback) {
		if (!isCached()) {
			callService(type, (Serializable) request, callback);
		} else {
			Object result = getFromMemory(type, request);
			if (result != null) {
				callback.onSuccess(convertResponse(result));
			} else {
				callService(type, (Serializable) request, callback);
			}
		}
	}

	private void callService(final RequestType type,
			final Serializable request, final AsyncCallback<S> callback) {
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

							@Override
							public void onSuccess(Serializable result) {
								if (isCached())
									addToMemory(type, request, result);
								RequestProcessor.this.onSuccess(callback,
										convertResponse(result));
							}
						});
			} catch (Throwable e) {
				onError(callback, e);
			}
		} else {
			onError(callback, new ApplicationOfflineException(Localization
					.get().application_offline_error()));
		}
	}

	public void onSuccess(AsyncCallback<S> callback, S data) {
		if (callback != null)
			callback.onSuccess(data);
		onSuccess(data);
	}

	public void onSuccess(S data) {
		ensureHandlers().fireEvent(new SuccessEvent<S>(data));
	}

	public void onError(AsyncCallback<S> callback, Throwable caught) {
		if (callback != null)
			callback.onFailure(caught);
		onError(caught);
		if (isThrowExceptions())
			throw new RuntimeException(caught);
	}

	public void onError(Throwable caught) {
		ensureHandlers().fireEvent(new FailureEvent<Throwable>(caught));
	}

	public Object getFromMemory(RequestType type, Q request) {
		return inMemoryCache.get(new MemoryKey(type, request));
	}

	public void addToMemory(RequestType type, Serializable request,
			Serializable data) {
		addToMemory(type, request, data);
	}

	public void addToMemory(RequestType type, Q request, S data) {
		inMemoryCache.put(new MemoryKey(type, request), data);
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

	public boolean isCached() {
		return isCached;
	}

	public void setCached(boolean isCached) {
		this.isCached = isCached;
	}

	public static RemoteServiceAsync getService() {
		return service;
	}

	public static void setService(RemoteServiceAsync service) {
		RequestProcessor.service = service;
	}

	@SuppressWarnings("unchecked")
	protected S convertResponse(Object value) {
		if (getResponseConverter() != null)
			return getResponseConverter().convert(value);
		return (S) value;
	}

	public IsConverter<Object, S> getResponseConverter() {
		return responseConverter;
	}

	public void setResponseConverter(IsConverter<Object, S> responseConverter) {
		this.responseConverter = responseConverter;
	}

	public boolean isThrowExceptions() {
		return throwExceptions;
	}

	public void setThrowExceptions(boolean throwExceptions) {
		this.throwExceptions = throwExceptions;
	}

}
