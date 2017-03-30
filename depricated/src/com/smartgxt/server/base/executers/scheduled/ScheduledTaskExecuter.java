package com.smartgxt.server.base.executers.scheduled;

import java.util.TimerTask;

import com.smartgxt.server.base.executers.RpcRequestExecuter;

/**
 * @author Anton Alexeyev
 * 
 */
public abstract class ScheduledTaskExecuter extends RpcRequestExecuter {

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
