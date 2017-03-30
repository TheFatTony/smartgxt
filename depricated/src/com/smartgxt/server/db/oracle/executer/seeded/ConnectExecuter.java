package com.smartgxt.server.db.oracle.executer.seeded;

import com.smartgxt.server.base.sessions.GwtSession;
import com.smartgxt.server.base.sessions.SessionManager;
import com.smartgxt.server.db.oracle.OracleConfiguration;
import com.smartgxt.server.db.oracle.executer.OracleRequestExecuter;
import com.smartgxt.shared.seeded.ConnectRequestData;
import com.smartgxt.shared.seeded.ConnectResponseData;

/**
 * @author Anton Alexeyev
 * 
 */
public class ConnectExecuter extends
		OracleRequestExecuter<ConnectRequestData, ConnectResponseData> {

	public ConnectExecuter() {
		setSystem(true);
	}

	@Override
	public void execute() throws Throwable {
		GwtSession sess = SessionManager.get().createSession();

		ConnectRequestData data = getRequest();
		sess.setAttribute("sgxt.statesVersion", data.getStatesVersion());
		sess.setAttribute("sgxt.statesUser", data.getStatesUser());

		ConnectResponseData respData = new ConnectResponseData();
		respData.setServerId(OracleConfiguration.getUrlName());
		respData.setSessionId(sess.getId());
		setResponse(respData);

	}

}
