package com.smartgxt.core.client.rpc;

import com.smartgxt.core.shared.events.RequestType;

public class MemoryKey<R> {

	private RequestType requestType;
	private R loadConfig;

	public MemoryKey() {
	}

	public MemoryKey(RequestType requestType, R loadConfig) {
		setLoadConfig(loadConfig);
		setRequestType(requestType);
	}

	public R getLoadConfig() {
		return loadConfig;
	}

	public void setLoadConfig(R loadConfig) {
		this.loadConfig = loadConfig;
	}

	public RequestType getRequestType() {
		return requestType;
	}

	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}

}
