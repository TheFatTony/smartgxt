package com.smartgxt.core.shared.data.seeded;

import java.io.Serializable;

/**
 * @author Anton Alexeyev
 * 
 */
@SuppressWarnings("serial")
public class ConnectResponseData implements Serializable {

	private String serverId;
	private String sessionId;

	public ConnectResponseData() {
	}

	public ConnectResponseData(String statesVersion, String statesUser) {
		setServerId(serverId);
		setSessionId(sessionId);
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

}
