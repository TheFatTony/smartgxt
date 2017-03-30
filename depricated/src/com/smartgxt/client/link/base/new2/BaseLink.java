package com.smartgxt.client.link.base.new2;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.BaseObservable;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.util.DelayedTask;

/**
 * @author Anton Alexeyev, Igor Kuchmenko
 * 
 */
public abstract class BaseLink extends BaseObservable implements IsBaseLink {

	public static final EventType onExecute = new EventType();
	public static final EventType onEndExecute = new EventType();
	public static final EventType onUnLock = new EventType();
	public static final EventType onLock = new EventType();

	private Object parent;
	private Object child;

	private boolean locked = false;

	protected DelayedTask executeTask;
	protected int delay = 0;

	public BaseLink(Object parent, Object child) {
		setChild(child);
		setParent(parent);
	}

	public void addOnExecuteListner(
			Listener<LinkExecuteEvent> linkExecuteListener) {
		addListener(onExecute, linkExecuteListener);
	}

	final public void execute() {
		if (!isLocked()) {
			if (executeTask == null) {
				executeTask = new DelayedTask(new Listener<BaseEvent>() {
					@Override
					public void handleEvent(BaseEvent be) {
						if (fireEvent(onLock, new LinkUnlockEvent(onLock,
								BaseLink.this, getParent(), getChild())))
							lock();

						if (fireEvent(onExecute, new LinkExecuteEvent(
								onExecute, this, getParent(), getChild())))
							onExecute();
					}
				});
			}
			executeTask.delay(delay);
		}
	}

	protected abstract void onExecute();

	public void lock() {
		setLocked(true);
	}

	public void unLock() {
		setLocked(false);
	}

	public void endExecute() {
		if (fireEvent(onUnLock, new LinkExecuteEvent(onUnLock, this,
				getParent(), getChild())))
			unLock();
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	final public boolean isLocked() {
		return locked;
	}

	private void setLocked(boolean locked) {
		this.locked = locked;
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
