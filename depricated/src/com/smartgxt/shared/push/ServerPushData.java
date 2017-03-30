package com.smartgxt.shared.push;

import java.io.Serializable;

import com.extjs.gxt.ui.client.event.EventType;

/**
 * @author Anton Alexeyev
 * 
 */
@SuppressWarnings("serial")
public class ServerPushData implements Serializable {

	private EventType type;
	private Serializable data;

	public ServerPushData() {

	}

	public ServerPushData(EventType type, Serializable data) {
		this.type = type;
		this.data = data;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public EventType getType() {
		return type;
	}

	public void setData(Serializable data) {
		this.data = data;
	}

	public Object getData() {
		return data;
	}

}
