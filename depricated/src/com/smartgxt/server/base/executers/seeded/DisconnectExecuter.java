package com.smartgxt.server.base.executers.seeded;

import java.util.HashMap;

import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.data.BaseModelData;
import com.smartgxt.server.base.executers.RpcRequestExecuter;
import com.smartgxt.server.base.sessions.GwtSession;
import com.smartgxt.server.base.sessions.SessionManager;

/**
 * @author Anton Alexeyev
 * 
 */
public class DisconnectExecuter extends
		RpcRequestExecuter<GwtSession, HashMap<String, String>, BaseModelData> {

	public DisconnectExecuter() {
		setSystem(true);
	}

	@Override
	public void execute() throws Exception {
		HashMap<String, String> states = getRequest();
		// TODO save state somewhere
		setResponse(new BaseModel());
		SessionManager.get().destroySession();
	}

}
