package com.smartgxt.server.base.executers.seeded;

import java.util.HashMap;
import java.util.Map;

import com.smartgxt.server.base.executers.RpcRequestExecuter;
import com.smartgxt.server.base.sessions.GwtSession;
import com.smartgxt.shared.seeded.LoginRequestData;
import com.smartgxt.shared.seeded.LoginResponseData;

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

		System.out.println("server statesUser "
				+ session.getAttribute("sgxt.statesUser"));
		System.out.println("server statesVersion "
				+ session.getAttribute("sgxt.statesVersion"));

		// TODO save sates somewhere
		HashMap<String, String> states = new HashMap<String, String>();

		Map<String, Object> locale = data.getLocale();
		session.setLanguage((String) locale.get("id"));

		LoginResponseData resData = new LoginResponseData();
		resData.setStates(states);
		resData.setLogin(data.getLogin());

		setResponse(resData);
	}

}
