package com.smartgxt.client.link.base.new2;

import com.extjs.gxt.ui.client.event.EventType;

public class LinkExecuteEvent extends BaseExecuteEvent {

	public LinkExecuteEvent(EventType type) {
		super(type);
	}

	public LinkExecuteEvent(EventType type, Object link, Object parent,
			Object child) {
		super(type, link, parent, child);
	}

}
