package com.smartgxt.server.base.executers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.smartgxt.server.RemoteGwtServlet;
import com.smartgxt.server.base.configs.Configuration;
import com.smartgxt.server.base.sessions.GwtSession;
import com.smartgxt.server.base.sessions.SessionManager;
import com.smartgxt.server.messages.Localization;
import com.smartgxt.server.security.SecurityRules;
import com.smartgxt.shared.events.ReflectionRequestType;
import com.smartgxt.shared.events.RequestType;
import com.smartgxt.shared.exceptions.AccessDeniedException;
import com.smartgxt.shared.exceptions.NoSessionException;
import com.smartgxt.shared.exceptions.NoSuchCallException;

/**
 * @author Anton Alexeyev
 * 
 */
public class RunManager {

	private static RunManager instance;

	private static Map<Integer, Class<? extends BaseRequestExecuter<?, ?, ?>>> executors;
	private static Map<String, Class<?>> classes;

	static {
		executors = new HashMap<Integer, Class<? extends BaseRequestExecuter<?, ?, ?>>>();
		classes = new HashMap<String, Class<?>>();
	}

	public RunManager() {
	}

	public static Object processRequest(RequestType type, Object request)
			throws Throwable {
		return processRequest(type, request, null);
	}

	public static Object processRequest(RequestType type, Object request,
			Object response) throws Throwable {

		String sessionId = type.getSessionId();
		RemoteGwtServlet.get().setSessionId(sessionId);
		// TODO localization
		if (!Configuration.getAppVersion().equals(type.getVersion()))
			throw new Exception("Invalid version, refresh application please");

		GwtSession session = SessionManager.get().getSession(sessionId);

		String user = "anonymous";
		if (session != null) {
			user = session.getUserName();
		}

		Date startDate = new Date();
		// TODO users calls log
		System.out.println("user's " + user + "(" + sessionId + ")" + " call "
				+ type.toString() + " started at " + startDate);

		BaseRequestExecuter<?, ?, ?> r;
		r = newExecutor(session, type);

		r.setSessionObject(session);
		r.setRequestObject(request);
		if (response != null)
			r.setResponseObject(response);

		try {
			execute(session, r);
		} catch (Throwable e) {
			throw e;
		} finally {
			Date endDate = new Date();
			long interval = (endDate.getTime() - startDate.getTime()) / 1000;
			System.out.println("user's " + user + "(" + sessionId + ")"
					+ " call " + type.toString() + " ended at " + endDate
					+ " elapsed " + interval + " seconds");
		}

		return r.getResponse();
	}

	public static void execute(GwtSession session,
			BaseRequestExecuter<?, ?, ?> runnable) throws Throwable {
		if (runnable.isCanceled())
			return;

		if ((!runnable.isSystem())
				&& ((session == null) || (!session.isLoggedIn()))) {
			throw new NoSessionException(Localization.get()
					.exceptions_NoSession());
		}

		if (session != null)
			session.pushExecuter(runnable);

		if (!runnable.isSystem()) {
			if ((Configuration.getMaxThreadsWait() == -1)
					|| (session.getThreads().size() < Configuration
							.getMaxThreadsWait())) {
				session.pushThread(Thread.currentThread());
				runnable.run();
				session.popThread(Thread.currentThread());
				Throwable e = runnable.getEcxeption();
				if (e != null)
					throw e;
			} else {
				if (session != null)
					session.popExecuter(runnable);
				Exception e = new Exception("Too many calls. Try again later.");
				throw e;
			}

		} else {
			runnable.run();
			Throwable e = runnable.getEcxeption();
			if (e != null)
				throw e;
			if (session != null)
				session.popExecuter(runnable);
		}

	}

	public static void addExecutor(RequestType type,
			Class<? extends BaseRequestExecuter<?, ?, ?>> executer) {
		executors.put(type.getEventCode(), executer);
	}

	public static Class<? extends BaseRequestExecuter<?, ?, ?>> getExecutor(
			RequestType type) {
		return executors.get(type.getEventCode());
	}

	public static void setClasses(Map<String, Class<?>> classes) {
		RunManager.classes = classes;
	}

	public static Map<String, Class<?>> getClasses() {
		return classes;
	}

	public static RunManager get() {
		if (instance == null)
			instance = new RunManager();
		return instance;
	}

	@SuppressWarnings("unchecked")
	public static BaseRequestExecuter<?, ?, ?> newExecutor(GwtSession session,
			RequestType type) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException,
			AccessDeniedException, NoSuchCallException {
		BaseRequestExecuter<?, ?, ?> r = null;
		if (type instanceof ReflectionRequestType) {
			String name = ((ReflectionRequestType) type).getClassName();
			checkAccess(session, name);
			r = createExecuter(name);
		} else {
			Class<BaseRequestExecuter<?, ?, ?>> c = (Class<BaseRequestExecuter<?, ?, ?>>) getExecutor(type);
			if (c == null)
				throw new NoSuchCallException(Localization.get()
						.exceptions_NoSuchCall());
			checkAccess(session, c);
			r = c.newInstance();
		}
		return r;
	}

	@SuppressWarnings("rawtypes")
	private static BaseRequestExecuter<?, ?, ?> createExecuter(String name)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, NoSuchCallException {
		BaseRequestExecuter<?, ?, ?> executer = null;
		Class<?> cs = getClasses().get(name);
		if (cs == null) {
			cs = Class.forName(name);
			if (cs == null)
				throw new NoSuchCallException(Localization.get()
						.exceptions_NoSuchCall());
			getClasses().put(name, cs);
		}
		executer = (RpcRequestExecuter) cs.newInstance();

		return executer;
	}

	public static void checkAccess(GwtSession session, Class<?> class_)
			throws AccessDeniedException {
		checkAccess(session, class_.getCanonicalName());
	}

	public static void checkAccess(GwtSession session, String className)
			throws AccessDeniedException {
		if (session == null) {
			if (!SecurityRules.isClassServerOmited(className)) {
				AccessDeniedException e = new AccessDeniedException(
						Localization.get().exceptions_NoSession());
				throw e;
			}
		} else {
			if (!session.getSecurityRules().isClassServerAllowed(className)) {
				AccessDeniedException e = new AccessDeniedException(
						Localization.get().exceptions_AccessDenied());
				throw e;
			}
		}
	}

}
