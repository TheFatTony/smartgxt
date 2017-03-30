package com.smartgxt.client.link.base;

public class BaseLinkInfo implements IsBaseLinkInfo {

	private Object parent;
	private Object child;

	public BaseLinkInfo(Object parent, Object child) {
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

}
