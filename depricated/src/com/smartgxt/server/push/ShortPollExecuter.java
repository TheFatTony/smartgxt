package com.smartgxt.server.push;

import java.util.ArrayList;

import com.extjs.gxt.ui.client.data.BaseModel;
import com.smartgxt.server.base.executers.RpcRequestExecuter;
import com.smartgxt.server.base.sessions.GwtSession;
import com.smartgxt.shared.push.ServerPushData;

/**
 * @author Anton Alexeyev
 * 
 */
public class ShortPollExecuter extends
		RpcRequestExecuter<GwtSession, BaseModel, ArrayList<ServerPushData>> {

	public ShortPollExecuter() {
		setSystem(true);
	}

	@Override
	public void execute() throws Throwable {
		ArrayList<ServerPushData> list = getSession().getCurrentPushEvents();
		setResponse(list);
	}
}
