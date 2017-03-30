package com.smartgxt.client.link.base;

import com.extjs.gxt.ui.client.event.EventType;

public class LinkUnlockEvent extends BaseExecuteEvent {

	public LinkUnlockEvent(EventType type) {
		super(type);
	}

	public LinkUnlockEvent(EventType type, Object link, Object linkData) {
		super(type, link, linkData);
	}

}
