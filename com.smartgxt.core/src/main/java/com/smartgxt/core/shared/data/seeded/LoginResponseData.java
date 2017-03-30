package com.smartgxt.core.shared.data.seeded;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author Anton Alexeyev
 * 
 */
@SuppressWarnings("serial")
public class LoginResponseData implements Serializable {

	private HashMap<String, String> states;
	private String login;

	public LoginResponseData() {
	}

	public HashMap<String, String> getStates() {
		return states;
	}

	public void setStates(HashMap<String, String> states) {
		this.states = states;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

}
