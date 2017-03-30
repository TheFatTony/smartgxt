package com.smartgxt.core.client;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anton Alexeyev
 * 
 */
public class Session {

	private static String login;
	private static String sesionId;
	private static List<String> roles;

	public static void setLogin(String arg0) {
		login = arg0;
	}

	public static String getLogin() {
		return login;
	}

	public static String getSesionId() {
		return sesionId;
	}

	public static void setSesionId(String sesionId) {
		Session.sesionId = sesionId;
	}

	public static List<String> getRoles() {

		if (roles == null) {
			roles = new ArrayList<String>();
		}

		return roles;
	}
}
