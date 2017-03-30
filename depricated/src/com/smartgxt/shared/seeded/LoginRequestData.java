package com.smartgxt.shared.seeded;

import java.io.Serializable;
import java.util.Map;

import com.smartgxt.shared.encryption.Crypter;

/**
 * @author Anton Alexeyev
 * 
 */
@SuppressWarnings("serial")
public class LoginRequestData implements Serializable {

	private String login;
	private String password;
	private Map<String, Object> locale;

	public LoginRequestData() {
	}

	public LoginRequestData(String login, String password,
			Map<String, Object> locale) {
		setLogin(login);
		setPassword(password);
		setLocale(locale);
	}

	public void setLogin(String login) {
		this.login = (String) Crypter.get().encrypt(login.toString());
	}

	public String getLogin() {
		return (String) Crypter.get().decrypt(login.toString());
	}

	public void setPassword(String password) {
		this.password = (String) Crypter.get().encrypt(password.toString());
	}

	public String getPassword() {
		return (String) Crypter.get().decrypt(password.toString());
	}

	public Map<String, Object> getLocale() {
		return locale;
	}

	public void setLocale(Map<String, Object> locale) {
		this.locale = locale;
	}

}
