package com.smartgxt.client.link.base.new2;

import com.extjs.gxt.ui.client.event.EventType;

public class LinkUnlockEvent extends BaseExecuteEvent {

	public LinkUnlockEvent(EventType type) {
		super(type);
	}

	public LinkUnlockEvent(EventType type, Object link, Object parent,
			Object child) {
		super(type, link, parent, child);
	}

}
