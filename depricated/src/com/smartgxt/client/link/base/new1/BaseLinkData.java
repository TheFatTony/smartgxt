package com.smartgxt.client.link.base.new1;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.util.DelayedTask;

public abstract class BaseLinkData<P, C> extends BaseLink<P, C> implements
		IBaseLinkData<Object, Object> {

	private C child;
	private P parent;

	@SuppressWarnings("unchecked")
	@Override
	public void setChild(Object child) {
		this.child = (C) child;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setParent(Object parent) {
		this.parent = (P) parent;
	}

	public C getChild() {
		return child;
	}

	public P getParent() {
		return parent;
	}

	@Override
	public void execute() {
		if (!isLocked()) {
			if (getExecuteTask() == null) {
				setExecuteTask(new DelayedTask(new Listener<BaseEvent>() {

					@Override
					public void handleEvent(BaseEvent be) {
						onExecute();
					}

				}));
			}
			getExecuteTask().delay(getDelay());
		}
	}

	@Override
	public boolean close() {
		if (isLocked())
			return false;

		parent = null;
		child = null;

		return true;
	}

	public Object getValueParent(){
		return null;
	}
	
	public Object getValueChild(){
		return null;
	}
	
}
