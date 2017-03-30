package com.smartgxt.core.server.push;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.smartgxt.core.server.executers.RunManager;
import com.smartgxt.core.server.security.SecurityRules;
import com.smartgxt.core.server.sessions.GwtSession;
import com.smartgxt.core.shared.events.RpcEvents;
import com.smartgxt.core.shared.events.push.PushEventType;
import com.smartgxt.core.shared.events.push.ServerPushData;

public class ServerPush {

	private List<ServerPushData> serverPushEvents;

	{
		RunManager.addExecutor(RpcEvents.LongPoll, LongPollExecuter.class);
		RunManager.addExecutor(RpcEvents.ShortPoll, ShortPollExecuter.class);
	}

	public ServerPush() {
		serverPushEvents = Collections
				.synchronizedList(new ArrayList<ServerPushData>());
	}

	public static void create(GwtSession session) {
		session.setAttribute("sgxt.PushEvents", new ServerPush());
		SecurityRules.getSecurityRules(session).addAllowedClass(
				LongPollExecuter.class);
		SecurityRules.getSecurityRules(session).addAllowedClass(
				ShortPollExecuter.class);
	}

	public static ServerPush get(GwtSession session) {
		return (ServerPush) session.getAttribute("sgxt.PushEvents");
	}

	public void pushEvent(PushEventType type, Serializable data) {
		serverPushEvents.add(new ServerPushData(type, data));
	}

	public synchronized ArrayList<ServerPushData> getCurrentPushEvents() {
		ArrayList<ServerPushData> values = new ArrayList<ServerPushData>();
		// ServerPushData value = null;
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

}
