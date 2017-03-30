package com.smartgxt.server.base.executers.scheduled;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Anton Alexeyev
 * 
 */
public class ScheduledTask {

	private Timer timer;
	private TimerTask task;
	private long delay;

	public ScheduledTask(TimerTask task) {
		newTimer();
		newTask(task);
		// setRepeating(true);
		setDelay(60 * 1000);
	}

	public ScheduledTask(long delay, long period, TimerTask task) {
		newTimer();
		newTask(task);
		// this.setRepeating(repeating);
		this.setDelay(delay);
		getTimer().scheduleAtFixedRate(getTask(), delay, period);
	}

	public ScheduledTask(long delay, TimerTask task) {
		newTimer();
		newTask(task);
		// setRepeating(false);
		setDelay(delay);
		getTimer().schedule(getTask(), getDelay());
	}

	// @Override
	// public void execute() throws Throwable {
	//
	// }

	private void newTask(TimerTask task) {
		this.task = task;
	}

	private void newTimer() {
		timer = new Timer();

	}

	public TimerTask getTask() {
		return task;
	}

	// public void setRepeating(boolean repeating) {
	// this.repeating = repeating;
	// }
	//
	// public boolean isRepeating() {
	// return repeating;
	// }

	public void setDelay(long delay) {
		this.delay = delay;
	}

	public long getDelay() {
		return delay;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public Timer getTimer() {
		return timer;
	}

}
