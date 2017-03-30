package com.smartgxt.server.base.executers.seeded;

import com.smartgxt.server.base.executers.RpcRequestExecuter;
import com.smartgxt.server.base.sessions.GwtSession;
import com.smartgxt.server.base.sessions.SessionManager;
import com.smartgxt.shared.seeded.ConnectRequestData;
import com.smartgxt.shared.seeded.ConnectResponseData;

/**
 * @author Anton Alexeyev
 * 
 */
public class ConnectExecuter extends
		RpcRequestExecuter<GwtSession, ConnectRequestData, ConnectResponseData> {

	public ConnectExecuter() {
		setSystem(true);
	}

	@Override
	public void execute() throws Exception {
		GwtSession sess = SessionManager.get().createSession();

		ConnectRequestData data = getRequest();
		sess.setAttribute("sgxt.statesVersion", data.getStatesVersion());
		sess.setAttribute("sgxt.statesUser", data.getStatesUser());
	}

}
