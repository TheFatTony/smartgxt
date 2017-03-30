package com.smartgxt.client.core;

import com.smartgxt.client.security.SecurityRules;

/**
 * @author Anton Alexeyev
 * 
 */
public class Application {

	private static SecurityRules securityRules = new SecurityRules();

	public Application() {
	}

	public static SecurityRules getSecurityRules() {
		return securityRules;
	}

}
