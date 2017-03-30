package com.smartgxt.client.link.base;

import com.extjs.gxt.ui.client.event.EventType;

public class LinkEndExecuteEvent extends BaseExecuteEvent {

	public LinkEndExecuteEvent(EventType type) {
		super(type);
	}

	public LinkEndExecuteEvent(EventType type, Object link, Object linkData) {
		super(type, link, linkData);
	}

}
