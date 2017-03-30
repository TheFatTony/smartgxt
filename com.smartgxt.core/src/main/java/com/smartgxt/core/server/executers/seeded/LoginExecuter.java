package com.smartgxt.core.server.executers.seeded;

import java.util.HashMap;
import java.util.Map;

import com.smartgxt.core.server.executers.RpcRequestExecuter;
import com.smartgxt.core.server.sessions.GwtSession;
import com.smartgxt.core.shared.data.seeded.LoginRequestData;
import com.smartgxt.core.shared.data.seeded.LoginResponseData;

/**
 * @author Anton Alexeyev
 * 
 */
public class LoginExecuter extends
		RpcRequestExecuter<GwtSession, LoginRequestData, LoginResponseData> {

	public LoginExecuter() {
		setSystem(true);
	}

	@Override
	public void execute() throws Exception {
		LoginRequestData data = getRequest();

		GwtSession session = getSession();

		session.loggedIn(data.getLogin());

		// TODO save sates somewhere
		HashMap<String, String> states = new HashMap<String, String>();

		Map<String, Object> locale = data.getLocale();
		if (locale != null)
			session.setLanguage((String) locale.get("id"));

		LoginResponseData resData = new LoginResponseData();
		resData.setStates(states);
		resData.setLogin(data.getLogin());

		setResponse(resData);
	}

}
