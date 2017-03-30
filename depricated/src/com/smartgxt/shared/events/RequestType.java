package com.smartgxt.shared.events;

import com.extjs.gxt.ui.client.event.EventType;

/**
 * @author Anton Alexeyev
 * 
 */
@SuppressWarnings("serial")
public class RequestType extends EventType {

	private String call;
	private String version;
	private String sessionId;

	public RequestType() {
		super();
	}

	public RequestType(int eventCode, String call) {
		super(eventCode);
		this.call = call;
	}

	public String getCall() {
		return call;
	}

	public void setCall(String call) {
		this.call = call;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RequestType)
			if (((RequestType) obj).getEventCode() == getEventCode())
				return true;

		return false;
	}

	@Override
	public String toString() {
		return call;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

}
