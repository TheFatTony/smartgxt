package com.smartgxt.client.link.base.new2;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.EventType;

public class BaseExecuteEvent extends BaseEvent {

	private Object child;
	private Object parent;
	private Object link;

	public BaseExecuteEvent(EventType type) {
		super(type);
	}

	public BaseExecuteEvent(EventType type, Object link, Object parent,
			Object child) {
		super(type);
		setParent(parent);
		setChild(child);
	}

	@SuppressWarnings("unchecked")
	public <X> X getChild() {
		return (X) this.child;
	}

	public <X> X setChild(X child) {
		this.child = child;
		return child;
	}

	@SuppressWarnings("unchecked")
	public <X> X getParent() {
		return (X) parent;
	}

	public <X> X setParent(X parent) {
		this.parent = parent;
		return parent;
	}

	@SuppressWarnings("unchecked")
	public <X> X getLink() {
		return (X) link;
	}

	public <X> X setLink(X link) {
		this.link = link;
		return link;
	}

}
