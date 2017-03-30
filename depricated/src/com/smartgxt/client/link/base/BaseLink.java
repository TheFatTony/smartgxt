package com.smartgxt.client.link.base;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.BaseObservable;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.util.DelayedTask;

/**
 * @author Anton Alexeyev, Igor Kuchmenko
 * 
 */
public abstract class BaseLink<D extends IsBaseLinkInfo> extends BaseObservable
		implements IsBaseLink<D> {

	public static final EventType onExecute = new EventType();
	public static final EventType onEndExecute = new EventType();
	public static final EventType onUnLock = new EventType();
	public static final EventType onLock = new EventType();

	private D linkData;

	private boolean executed;

	private boolean locked = false;

	protected DelayedTask executeTask;
	protected int delay = 0;

	public BaseLink(D linkData) {
		setLinkData(linkData);
	}

	public void addOnExecuteListner(
			Listener<LinkExecuteEvent> linkExecuteListener) {
		addListener(onExecute, linkExecuteListener);
	}

	final public boolean execute() {
		executed = false;
		if (!isLocked()) {
			if (delay != 0) {
				if (executeTask == null) {
					executeTask = new DelayedTask(new Listener<BaseEvent>() {
						@Override
						public void handleEvent(BaseEvent be) {
							execute_();
						}
					});
				}
				executeTask.delay(delay);
			} else
				execute_();
		}
		return executed;
	}

	private void execute_() {
		if (fireEvent(onLock, new LinkUnlockEvent(onLock, this, getLinkData())))
			lock();

		if (fireEvent(onExecute, new LinkExecuteEvent(onExecute, this,
				getLinkData()))) {
			onExecute();
		} else {
			executed = true;
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
				getLinkData())))
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

	public D getLinkData() {
		return linkData;
	}

	public void setLinkData(D linkData) {
		this.linkData = linkData;
	}
}
