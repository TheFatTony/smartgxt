package com.smartgxt.core.shared.data.seeded;

import java.io.Serializable;

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
		if (version != null) {
			statesVersion = Integer.parseInt(version);
		}
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
