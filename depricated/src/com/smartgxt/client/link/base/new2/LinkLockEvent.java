package com.smartgxt.client.link.base.new2;

import com.extjs.gxt.ui.client.event.EventType;

public class LinkLockEvent extends BaseExecuteEvent {

	public LinkLockEvent(EventType type) {
		super(type);
	}

	public LinkLockEvent(EventType type, Object link, Object parent,
			Object child) {
		super(type, link, parent, child);
	}

}
