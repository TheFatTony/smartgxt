package com.smartgxt.server.base.sessions;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TimerTask;

import javax.swing.event.EventListenerList;

import com.smartgxt.server.RemoteGwtServlet;
import com.smartgxt.server.base.configs.Configuration;
import com.smartgxt.server.base.executers.scheduled.ScheduledTask;

/**
 * @author Anton Alexeyev
 * 
 */
public class SessionManager {

	protected EventListenerList listenerList = new EventListenerList();
	private static SessionManager instance;

	// private Map<HttpSession, GwtSession> sessions;
	private Map<String, GwtSession> sessionsById;

	protected ScheduledTask sessionsMonitor;

	public SessionManager() {
		// sessions = new HashMap<HttpSession, GwtSession>();
		sessionsById = new HashMap<String, GwtSession>();
		sessionsMonitor = newScheduledTask();
	}

	protected ScheduledTask newScheduledTask() {
		int delay = Configuration.getKillTaskInterval();
		return new ScheduledTask(delay, delay, new TimerTask() {

			@Override
			public void run() {
				// TODO concurency
				Collection<GwtSession> list = sessionsById.values();
				Iterator<GwtSession> iterator = list.iterator();
				while (iterator.hasNext()) {
					GwtSession session = iterator.next();
					long interval = ((new Date()).getTime() - session
							.getLastActivityDate().getTime()) / 1000 / 60;
					if (interval > Configuration.getSessionTimeout())
						destroySession(session);
				}
			}
		});
	}

	public void addConnectListener(ConnectListener listener) {
		listenerList.add(ConnectListener.class, listener);
	}

	public void removeConnectListener(ConnectListener listener) {
		listenerList.remove(ConnectListener.class, listener);
	}

	public void addLoginListener(LoginListener listener) {
		listenerList.add(LoginListener.class, listener);
	}

	public void removeLoginListener(LoginListener listener) {
		listenerList.remove(LoginListener.class, listener);
	}

	protected void fireMyEvent(ConnectEvent evt) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == ConnectListener.class) {
				((ConnectListener) listeners[i + 1]).handleEvent(evt);
			}
		}
	}

	protected void fireMyEvent(LoginEvent evt) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == LoginListener.class) {
				((LoginListener) listeners[i + 1]).handleEvent(evt);
			}
		}
	}

	public GwtSession getSession() {
		return sessionsById.get(RemoteGwtServlet.get().getSessionId());
	}

	public GwtSession getSession(String sessionId) {
		return sessionsById.get(sessionId);
	}

	public GwtSession regenerateSessionId() throws Exception {
		GwtSession sess = sessionsById.remove(RemoteGwtServlet.get()
				.getSessionId());
		sessionsById.put(sess.generateNewId(), sess);
		return sess;
	}

	public GwtSession createSession() throws Exception {

		GwtSession ses = null;

		// if (session.isNew()) {
		try {
			ses = RemoteGwtServlet.getConfiguration().getSessionClass()
					.newInstance();

			ConnectEvent event = new ConnectEvent(this);
			event.setSession(ses);
			fireMyEvent(event);

		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new Exception(e.getLocalizedMessage());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new Exception(e.getLocalizedMessage());
		}
		// HttpSession session = ses.getHttpSession();
		// sessions.put(session, ses);
		sessionsById.put(ses.getId(), ses);
		// } else {
		// destroySession(session);
		// ses = createSession();
		// }
		return ses;
	}

	public void destroySession() {
		// TODO destroySession
		// destroySession(
		// RemoteGwtServlet.get().getLocalRequest().getSession(false),
		// false);
		destroySession(RemoteGwtServlet.get().getSessionId());
	}

	public void destroySession(String sessionId) {
		destroySession(sessionsById.get(sessionId));
	}

	// TODO check that all threads are died
	public void destroySession(GwtSession session) {
		if (session != null) {
			sessionsById.remove(session.getId());
			// sessions.remove(session);
			session.invalidate();
			session = null;
		}
	}

	public static SessionManager get() {
		if (instance == null)
			instance = new SessionManager();
		return instance;
	}

}
