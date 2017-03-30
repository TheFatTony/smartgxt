package com.smartgxt.core.server.push;

import com.smartgxt.core.shared.events.push.PushEventType;
import com.smartgxt.core.shared.events.push.ServerPushData;

public class PushEvent {

	private PushEventType type;

	private ServerPushData data;

	public PushEvent() {
	}

	public PushEvent(PushEventType type, ServerPushData data) {
		setType(type);
		setData(data);
	}

	public PushEventType getType() {
		return type;
	}

	public void setType(PushEventType type) {
		this.type = type;
	}

	public ServerPushData getData() {
		return data;
	}

	public void setData(ServerPushData data) {
		this.data = data;
	}

}
