package com.smartgxt.core.server.executers.seeded;

import java.util.HashMap;

import com.smartgxt.core.server.executers.RpcRequestExecuter;
import com.smartgxt.core.server.sessions.GwtSession;
import com.smartgxt.core.server.sessions.SessionManager;

/**
 * @author Anton Alexeyev
 * 
 */
public class DisconnectExecuter extends
		RpcRequestExecuter<GwtSession, HashMap<String, String>, String> {

	public DisconnectExecuter() {
		setSystem(true);
	}

	@Override
	public void execute() throws Exception {
		HashMap<String, String> states = getRequest();
		// TODO save state somewhere
		SessionManager.get().destroySession();
		setResponse(new String("Disconected"));
	}

}
