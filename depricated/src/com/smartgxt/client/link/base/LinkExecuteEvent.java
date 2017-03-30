package com.smartgxt.client.link.base;

import com.extjs.gxt.ui.client.event.EventType;

public class LinkExecuteEvent extends BaseExecuteEvent {

	public LinkExecuteEvent(EventType type) {
		super(type);
	}

	public LinkExecuteEvent(EventType type, Object parent, Object child) {
		super(type, parent, child);
	}

}
