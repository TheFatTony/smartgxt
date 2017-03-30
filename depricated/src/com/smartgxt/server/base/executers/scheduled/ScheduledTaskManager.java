package com.smartgxt.server.base.executers.scheduled;

import java.util.ArrayList;
import java.util.List;

import com.smartgxt.server.base.sessions.GwtSession;

/**
 * @author Anton Alexeyev
 * 
 */
public class ScheduledTaskManager {

	// private static ScheduledTaskManager instance;

	private static List<Class<? extends ScheduledTaskExecuter>> scheduledTasks = new ArrayList<Class<? extends ScheduledTaskExecuter>>();
	private List<ScheduledTaskExecuter> tasks;

	public ScheduledTaskManager(GwtSession session)
			throws InstantiationException, IllegalAccessException {
		tasks = new ArrayList<ScheduledTaskExecuter>();
		for (Class<? extends ScheduledTaskExecuter> c : scheduledTasks) {
			ScheduledTaskExecuter task = c.newInstance();
			task.setSession(session);
			tasks.add(task);
		}
	}

	public static void addUserTask(Class<? extends ScheduledTaskExecuter> task) {
		scheduledTasks.add(task);
	}

	public List<ScheduledTaskExecuter> getTasks() {
		return tasks;
	}

	public void cancelTasks() {
		for (ScheduledTaskExecuter task : tasks) {
			task.getTask().getTimer().cancel();
			task.getTask().getTask().cancel();
		}
	}

	// public static ScheduledTaskManager get() {
	// if (instance == null) {
	// instance = new ScheduledTaskManager();
	// }
	// return instance;
	// }

}
