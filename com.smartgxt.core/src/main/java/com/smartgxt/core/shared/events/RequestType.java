package com.smartgxt.core.shared.events;

import java.io.Serializable;

import com.google.gwt.user.client.Event;

/**
 * @author Anton Alexeyev
 * 
 */
@SuppressWarnings("serial")
public class RequestType implements Serializable {

	// private static int count = 0;
	// final String id;
	private int eventCode = -1;

	private String call;
	private String version;
	private String sessionId;

	/**
	 * Creates a new browser based event type.
	 * 
	 * @param eventCode
	 *            additional information about the event
	 */
	public RequestType() {
		super();
		// id = String.valueOf(count++);
	}

	/**
	 * Creates a new browser based event type.
	 * 
	 * @param eventCode
	 *            additional information about the event
	 */
	public RequestType(int eventCode, String call) {
		super();
		this.eventCode = eventCode;
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

	/**
	 * Returns the event code.
	 * 
	 * @return the event code
	 * @see Event
	 */
	public int getEventCode() {
		return eventCode;
	}

	/**
	 * Returns true if the event type represents a browser event type (GWT
	 * event).
	 * 
	 * @return true for browser event types
	 */
	public boolean isBrowserEvent() {
		return eventCode != -1;
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
