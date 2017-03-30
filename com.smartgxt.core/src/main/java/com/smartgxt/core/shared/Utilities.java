package com.smartgxt.core.shared;

public class Utilities {

	/**
	 * @author Anton Alexeyev
	 * 
	 */
	public Utilities() {
	}

	public static String getStackTrace(Throwable e) {
		StringBuilder sb = new StringBuilder();

		for (StackTraceElement element : e.getStackTrace()) {
			sb.append(element.toString());
			sb.append("\r\n");
		}
		return sb.toString();
	}

	public static String getMessage(Throwable e) {
		String s = e.getMessage();
		if (s == null)
			s = e.toString();
		return s;
	}

	public Object nvl(Object obj1, Object obj2) {
		if (obj1 == null)
			return obj2;
		return obj1;
	}
}
