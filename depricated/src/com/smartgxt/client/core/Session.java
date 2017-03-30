package com.smartgxt.client.core;

/**
 * @author Anton Alexeyev
 * 
 */
public class Session {

	private static String login;
	private static String sesionId;

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
}
