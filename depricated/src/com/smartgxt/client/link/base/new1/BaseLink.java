package com.smartgxt.client.link.base.new1;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.BaseObservable;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.util.DelayedTask;

public abstract class BaseLink<P, C> extends BaseObservable implements
		IBaseLink<Object, Object> {

	private boolean locked;
	private DelayedTask executeTask;
	private int delay = 0;

	@Override
	public void execute() {
		if (!isLocked()) {
			if (executeTask == null) {
				executeTask = new DelayedTask(new Listener<BaseEvent>() {
					@Override
					public void handleEvent(BaseEvent be) {
						onExecute();
					}
				});
			}
			executeTask.delay(delay);
		}
	}

	@Override
	public boolean close() {
		if (isLocked())
			return false;

		return true;
	}

	@Override
	public final boolean isLocked() {
		return this.locked;
	}

	protected final void setLocked(boolean locked) {
		this.locked = locked;
	}

	@Override
	public DelayedTask getExecuteTask() {
		return executeTask;
	}

	@Override
	public void setExecuteTask(DelayedTask executeTask) {
		this.executeTask = executeTask;
	}

	@Override
	public int getDelay() {
		return delay;
	}

	@Override
	public void setDelay(int delay) {
		this.delay = delay;
	}

}
