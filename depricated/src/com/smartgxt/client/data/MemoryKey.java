package com.smartgxt.client.data;

import com.smartgxt.shared.events.RequestType;

/**
 * @author Anton Alexeyev
 * 
 */
public class MemoryKey {

	private RequestType requestType;
	private Object loadConfig;

	public MemoryKey() {
	}

	public MemoryKey(RequestType requestType, Object loadConfig) {
		setLoadConfig(loadConfig);
		setRequestType(requestType);
	}

	public Object getLoadConfig() {
		return loadConfig;
	}

	public void setLoadConfig(Object loadConfig) {
		this.loadConfig = loadConfig;
	}

	public RequestType getRequestType() {
		return requestType;
	}

	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}

}
