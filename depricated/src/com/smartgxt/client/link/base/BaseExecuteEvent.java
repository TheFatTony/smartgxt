package com.smartgxt.client.link.base;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.EventType;

public class BaseExecuteEvent extends BaseEvent {

	private Object linkData;
	private Object link;

	public BaseExecuteEvent(EventType type) {
		super(type);
	}

	public BaseExecuteEvent(EventType type, Object link, Object linkData) {
		super(type);
		setLinkData(linkData);
		setLink(link);
	}

	@SuppressWarnings("unchecked")
	public <X> X getLink() {
		return (X) link;
	}

	public <X> X setLink(X link) {
		this.link = link;
		return link;
	}

	@SuppressWarnings("unchecked")
	public <X> X getLinkData() {
		return (X) linkData;
	}

	public <X> X setLinkData(X linkData) {
		this.linkData = linkData;
		return linkData;
	}

}
