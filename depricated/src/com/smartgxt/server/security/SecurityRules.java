package com.smartgxt.server.security;

import java.util.ArrayList;
import java.util.List;

import com.smartgxt.server.base.executers.RpcRequestExecuter;

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
}
