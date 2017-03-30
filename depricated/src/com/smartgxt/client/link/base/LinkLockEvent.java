package com.smartgxt.client.link.base;

import com.extjs.gxt.ui.client.event.EventType;

public class LinkLockEvent extends BaseExecuteEvent {

	public LinkLockEvent(EventType type) {
		super(type);
	}

	public LinkLockEvent(EventType type, Object link, Object linkData) {
		super(type, link, linkData);
	}

}
