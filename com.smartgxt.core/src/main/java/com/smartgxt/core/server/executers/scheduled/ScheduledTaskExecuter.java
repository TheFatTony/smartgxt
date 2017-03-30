package com.smartgxt.core.server.executers.scheduled;

import java.io.Serializable;
import java.util.TimerTask;

import com.smartgxt.core.server.executers.RpcRequestExecuter;
import com.smartgxt.core.server.sessions.GwtSession;

/**
 * @author Anton Alexeyev
 * 
 */
public abstract class ScheduledTaskExecuter<T extends GwtSession, Q extends Serializable, S extends Serializable>
		extends RpcRequestExecuter<T, Q, S> {

	ScheduledTask task;

	public ScheduledTaskExecuter() {
		task = new ScheduledTask(newTimerTask());
	}

	public ScheduledTaskExecuter(long delay, long period) {
		task = new ScheduledTask(delay, period, newTimerTask());
	}

	public ScheduledTaskExecuter(long delay) {
		task = new ScheduledTask(delay, newTimerTask());
	}

	private TimerTask newTimerTask() {
		return new TimerTask() {
			@Override
			public void run() {
				try {
					ScheduledTaskExecuter.this.execute();
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// if (isRepeating())
				// getTimer().schedule(getTask(), getDelay());
			}

		};
	}

	public ScheduledTask getTask() {
		return task;
	}
}
