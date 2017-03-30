package com.smartgxt.core.client.security;

import java.util.List;

public class SecurityManager {

	static SecurityRuleApplier ruleApplier = new SecurityRuleApplier();
	

	public SecurityManager() {

	}

	public static void applySecurityRules(Object object,
			List<String> hiddenFor, List<String> disabledFor) {
		ruleApplier.applySecurityRules(object, hiddenFor, disabledFor);
	}
	
	public static SecurityRuleApplier getRuleApplier() {
		return ruleApplier;
	}

	public static void setRuleApplier(SecurityRuleApplier ruleApplier) {
		SecurityManager.ruleApplier = ruleApplier;
	}

}
