package com.smartgxt.core.oracle.server.executor.seeded;


import com.smartgxt.core.oracle.server.OracleConfiguration;
import com.smartgxt.core.oracle.server.executor.OracleRequestExecuter;
import com.smartgxt.core.server.sessions.GwtSession;
import com.smartgxt.core.server.sessions.SessionManager;
import com.smartgxt.core.shared.data.seeded.ConnectRequestData;
import com.smartgxt.core.shared.data.seeded.ConnectResponseData;

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
