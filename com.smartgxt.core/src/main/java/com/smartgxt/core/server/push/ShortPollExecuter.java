package com.smartgxt.core.server.push;

import java.io.Serializable;
import java.util.ArrayList;

import com.smartgxt.core.server.executers.RpcRequestExecuter;
import com.smartgxt.core.server.sessions.GwtSession;
import com.smartgxt.core.shared.events.push.ServerPushData;

/**
 * @author Anton Alexeyev
 * 
 */
public class ShortPollExecuter extends
		RpcRequestExecuter<GwtSession, Serializable, ArrayList<ServerPushData>> {

	public ShortPollExecuter() {
		setSystem(true);
	}

	@Override
	public void execute() throws Throwable {
		ArrayList<ServerPushData> list = ServerPush.get(getSession())
				.getCurrentPushEvents();
		setResponse(list);
	}
}
