package com.smartgxt.client.push;

import com.extjs.gxt.ui.client.event.BaseObservable;
import com.extjs.gxt.ui.client.event.EventType;

/**
 * @author Anton Alexeyev
 * 
 */
public class ServerPush extends BaseObservable {

	public static final EventType PushEvent = new EventType();
	private static ServerPush instance;
	private static PoolingRequest request;

	public ServerPush() {
	}

	public void addPushEvent(PushEventListener listener) {
		addListener(PushEvent, listener);
	}

	public static ServerPush get() {
		assert request != null : "request must be setted befor call get()!!";
		if (instance == null) {
			instance = new ServerPush();
		}
		return instance;
	}

	public static void init(PoolingRequest request) {
		assert instance == null : "init must be called befor get()!!";
		ServerPush.request = request;
	}

	public static PoolingRequest getRequest() {
		return request;
	}

}
