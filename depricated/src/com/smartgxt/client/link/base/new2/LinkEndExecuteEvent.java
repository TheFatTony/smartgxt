package com.smartgxt.client.link.base.new2;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.EventType;

public class LinkEndExecuteEvent extends BaseExecuteEvent {

	public LinkEndExecuteEvent(EventType type) {
		super(type);
	}

	public LinkEndExecuteEvent(EventType type, Object link, Object parent,
			Object child) {
		super(type, link, parent, child);
	}

}
