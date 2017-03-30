package com.smartgxt.core.shared.events.push;

import java.io.Serializable;


/**
 * @author Anton Alexeyev
 * 
 */
@SuppressWarnings("serial")
public class ServerPushData implements Serializable {

	private PushEventType type;
	private Serializable data;

	public ServerPushData() {

	}

	public ServerPushData(PushEventType type, Serializable data) {
		this.type = type;
		this.data = data;
	}

	public void setType(PushEventType type) {
		this.type = type;
	}

	public PushEventType getType() {
		return type;
	}

	public void setData(Serializable data) {
		this.data = data;
	}

	public Object getData() {
		return data;
	}

}
