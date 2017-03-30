package com.smartgxt.shared.seeded;

import java.io.Serializable;

import com.extjs.gxt.ui.client.util.Util;

/**
 * @author Anton Alexeyev
 * 
 */
@SuppressWarnings("serial")
public class ConnectRequestData implements Serializable {

	private Integer statesVersion;
	private String statesUser;

	public ConnectRequestData() {
	}

	public ConnectRequestData(String statesVersion, String statesUser) {
		setStatesVersion(statesVersion);
		setStatesUser(statesUser);
	}

	public void setStatesVersion(String version) {
		statesVersion = Util.parseInt(version, 0);
	}

	public Integer getStatesVersion() {
		return statesVersion;
	}

	public void setStatesUser(String user) {
		statesUser = user;
	}

	public String getStatesUser() {
		return statesUser;
	}

}
