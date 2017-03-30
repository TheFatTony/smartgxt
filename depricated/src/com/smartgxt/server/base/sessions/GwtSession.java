package com.smartgxt.server.base.sessions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.extjs.gxt.ui.client.event.EventType;
import com.smartgxt.server.base.configs.Configuration;
import com.smartgxt.server.base.executers.BaseRequestExecuter;
import com.smartgxt.server.base.executers.scheduled.ScheduledTaskManager;
import com.smartgxt.server.security.SecurityRules;
import com.smartgxt.server.streams.StreamDefinitions;
import com.smartgxt.shared.push.ServerPushData;

/**
 * @author Anton Alexeyev
 * 
 */
public class GwtSession {

	private SecurityRules securityRules;
	private String userName = "anonymous";
	private boolean isLoggedIn = false;
	// private HttpSession session;
	private String id;
	private List<Thread> activeThreads;
	private List<Thread> threads;
	// private List<Thread> systemThreads;

	private List<BaseRequestExecuter<?, ?, ?>> executers;

	// private List<СlientEvent> clientEvents;
	private StreamDefinitions userStreams;
	// TODO extract to config
	private String language = "en";
	private List<ServerPushData> serverPushEvents;

	private ScheduledTaskManager scheduledTasks;

	private Map<String, Object> attributes;

	private Date lastActivityDate;

	public GwtSession() {
		// this.session = RemoteGwtServlet.get().getLocalRequest()
		// .getSession(true);
		updateActivityDate();
		generateNewId();
		// TODO duplication check. If it is requered.

		securityRules = new SecurityRules();
		activeThreads = new ArrayList<Thread>();
		threads = new ArrayList<Thread>();
		// systemThreads = new ArrayList<Thread>();
		attributes = new HashMap<String, Object>();
		userStreams = new StreamDefinitions();

		executers = new ArrayList<BaseRequestExecuter<?, ?, ?>>();
		// serverPushEvents = Collections
		// .synchronizedList(new ArrayList<PushEvent>());
		serverPushEvents = new ArrayList<ServerPushData>();
		// resetClientEvents();

		System.out.println("created session with id = " + id);
	}

	// public static GwtSession getCurrent() {
	// return SessionManager.get().getSession();
	// }

	public void pushEvent(EventType type, Serializable data) {
		serverPushEvents.add(new ServerPushData(type, data));
	}

	public synchronized ArrayList<ServerPushData> getCurrentPushEvents() {
		ArrayList<ServerPushData> values = new ArrayList<ServerPushData>();
		// PushEvent value = null;
		// int i = 0;
		// if (serverPushEvents.size() > 0) {
		// while ((value = serverPushEvents.remove(i)) != null) {
		// synchronized (serverPushEvents) {
		// values.add(value);
		// }
		// }
		// }
		for (ServerPushData event : serverPushEvents)
			values.add(event);
		serverPushEvents.clear();

		return values;
	}

	public String getId() {
		return id;
		// return session.getId();
	}

	public void setId(String id) {
		this.id = id;
	}

	public String generateNewId() {
		setId(UUID.randomUUID().toString());
		return id;
	}

	private boolean hasSlots() {
		return (Configuration.getMaxActiveThreads() == -1)
				|| (activeThreads.size() < Configuration.getMaxActiveThreads());
	}

	public void pushExecuter(BaseRequestExecuter<?, ?, ?> executer) {
		updateActivityDate();
		executers.add(executer);
	}

	public void popExecuter(BaseRequestExecuter<?, ?, ?> executer) {
		updateActivityDate();
		executers.remove(executer);
	}

	@SuppressWarnings("static-access")
	public void pushThread(Thread thread) {
		threads.add(thread);
		while (!hasSlots())
			try {
				thread.sleep(Configuration.getThreadsWaitTime());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		activeThreads.add(thread);
	}

	public void popThread(Thread thread) {
		threads.remove(thread);
		activeThreads.remove(thread);
	}

	// public void pushSystemThread(Thread thread) {
	// systemThreads.add(thread);
	// }
	//
	// public void popSystemThread(Thread thread) {
	// systemThreads.remove(thread);
	// }

	public Object getAttribute(String arg0) {
		// return session.getAttribute(arg0);
		return attributes.get(arg0);
	}

	public void setAttribute(String arg0, Object arg1) {
		// session.setAttribute(arg0, arg1);
		attributes.put(arg0, arg1);
	}

	// public HttpSession getHttpSession() {
	// return session;
	// }

	public List<Thread> getActiveThreads() {
		return activeThreads;
	}

	public List<Thread> getThreads() {
		return threads;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void invalidate() {
		// resetClientEvents();
		getUserStreams().flush();

		if (scheduledTasks != null)
			scheduledTasks.cancelTasks();

		threads.clear();

		for (BaseRequestExecuter<?, ?, ?> executer : executers)
			executer.cancel();

		System.out.println("deleted session with id = " + id);

		// for (Thread t : threads) {
		// // if (!t.equals(Thread.currentThread()))
		// // t.interrupt();
		// System.out.println("Thread still working id = " + t.getId());
		// }
		// threads.clear();
		//
		// for (Thread t : systemThreads) {
		// // if (!t.equals(Thread.currentThread()))
		// // t.interrupt();
		// System.out.println("System Thread still working id = " + t.getId());
		// }
		// systemThreads.clear();
	}

	public StreamDefinitions getUserStreams() {
		return userStreams;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getLanguage() {
		return language;
	}

	public SecurityRules getSecurityRules() {
		return securityRules;
	}

	public void loggedIn(String userName) throws InstantiationException,
			IllegalAccessException {
		this.isLoggedIn = true;
		setUserName(userName);
		LoginEvent event = new LoginEvent(this);
		event.setSession(this);
		SessionManager.get().fireMyEvent(event);
		scheduledTasks = new ScheduledTaskManager(this);
	}

	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	// public List<Thread> getSystemThreads() {
	// return systemThreads;
	// }

	public ScheduledTaskManager getScheduledTasks() {
		return scheduledTasks;
	}

	public Date getLastActivityDate() {
		return lastActivityDate;
	}

	protected void updateActivityDate() {
		setLastActivityDate(new Date());
	}

	protected void setLastActivityDate(Date lastActivityDate) {
		this.lastActivityDate = lastActivityDate;
	}

}
