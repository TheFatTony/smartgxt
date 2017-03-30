package com.smartgxt.core.server.security;

import java.util.ArrayList;
import java.util.List;

import com.smartgxt.core.server.executers.RpcRequestExecuter;
import com.smartgxt.core.server.messages.Localization;
import com.smartgxt.core.server.sessions.GwtSession;
import com.smartgxt.core.shared.exceptions.AccessDeniedException;

/**
 * @author Anton Alexeyev
 * 
 */
public class SecurityRules {

	private static List<String> omitedServerClasses = new ArrayList<String>();
	private static List<String> omitedClientClasses = new ArrayList<String>();
	private List<String> allowedClasses;

	public SecurityRules() {
		allowedClasses = new ArrayList<String>();
	}

	public static List<String> getOmitedServerClasses() {
		return omitedServerClasses;
	}

	public static void addOmitedServerClass(Class<?> class_) {
		assert class_.isAssignableFrom(RpcRequestExecuter.class) : "Class must be instance of RequestExecuter";
		omitedServerClasses.add(class_.getCanonicalName());
	}

	public static void addOmitedClientClass(String class_) {
		omitedClientClasses.add(class_);
	}

	public List<String> getAllowedClasses() {
		return allowedClasses;
	}

	public void addAllowedClass(Class<?> class_) {
		assert class_.isAssignableFrom(RpcRequestExecuter.class) : "Class must be instance of RequestExecuter";
		allowedClasses.add(class_.getCanonicalName());
	}

	public static boolean isClassServerOmited(Class<?> class_) {
		String checkClass = class_.getCanonicalName();
		return isClassServerOmited(checkClass);
	}

	public static boolean isClassServerOmited(String className) {
		for (String s : omitedServerClasses)
			if (s.equals(className))
				return true;

		return false;
	}

	public static SecurityRules getSecurityRules(GwtSession session) {
		SecurityRules results = getSecurityRulesFromSession(session);
		if (results == null) {
			setSecurityRulesToSession(session, new SecurityRules());
			results = getSecurityRulesFromSession(session);
		}
		return results;

	}

	private static SecurityRules getSecurityRulesFromSession(GwtSession session) {
		return (SecurityRules) session.getAttribute("sgxt.SecurityRules");
	}

	private static void setSecurityRulesToSession(GwtSession session,
			SecurityRules securityRules) {
		session.setAttribute("sgxt.SecurityRules", securityRules);
	}

	public boolean isClassServerAllowed(String className) {
		for (String s : omitedServerClasses) {
			if (s.equals(className))
				return true;
		}

		for (String s : allowedClasses)
			if (s.equals(className))
				return true;

		return false;
	}

	public boolean isClassServerAllowed(Class<?> class_) {
		String checkClass = class_.getCanonicalName();
		return isClassServerAllowed(checkClass);
	}

	public static void checkAccess(GwtSession session, Class<?> class_)
			throws AccessDeniedException {
		checkAccess(session, class_.getCanonicalName());
	}

	public static void checkAccess(GwtSession session, String className)
			throws AccessDeniedException {
		// TODO restore
		if (session == null) {
			if (!SecurityRules.isClassServerOmited(className)) {
				AccessDeniedException e = new AccessDeniedException(
						Localization.get().exceptions_NoSession());
				throw e;
			}
		} else {
			if (!SecurityRules.getSecurityRules(session).isClassServerAllowed(
					className)) {
				AccessDeniedException e = new AccessDeniedException(
						Localization.get().exceptions_AccessDenied());
				throw e;
			}
		}
	}

}
