package com.smartgxt.core.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.smartgxt.core.client.prototypes.AsyncCommand;
import com.smartgxt.core.client.prototypes.Prototype;
import com.smartgxt.core.client.utils.HtmlHelper;

/**
 * @author Anton Alexeyev
 * 
 */
public class SmartGXT {

	public static String loadingPanelId = "loading";

	private static boolean isInited = false;
	private static boolean isValid = true;

	private static boolean isOnline = false;
	private static String version;

	public SmartGXT() {
	}

	public static void init() {
		if (isInited) {
			return;
		}
		isInited = true;

		if (isValid) {
			setVersion(HtmlHelper.getMetaValue("application-version"));
			System.out.println("version = " + getVersion());
			assert getVersion() != null : "set \"application-version\" meta tag at module HTML";
		}
		GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			@Override
			public void onUncaughtException(Throwable e) {
				// MessageBox.alert(Localization.get().error_dialog_header(),
				// e.getMessage(), null);
				e.printStackTrace();
			}
		});
	}

	@SuppressWarnings("unchecked")
	public static <T> T create(Class<?> classLiteral, Object... args) {
		return (T) ((Prototype) GWT.create(Prototype.class)).newInstance(
				classLiteral, args);
	}

	public static void create(Class<?> classLiteral, AsyncCommand command,
			Object... args) {
		((Prototype) GWT.create(Prototype.class)).newInstance(classLiteral,
				command, args);
	}
	
	
	@SuppressWarnings("unchecked")
	public static <T> T create(String classLiteral, Object... args) {
		return (T) ((Prototype) GWT.create(Prototype.class)).newInstance(
				classLiteral, args);
	}

	public static void create(String classLiteral, AsyncCommand command,
			Object... args) {
		((Prototype) GWT.create(Prototype.class)).newInstance(classLiteral,
				command, args);
	}

	public static boolean isOnline() {
		return isOnline;
	}

	public static void setOnline(boolean isOnline) {
		SmartGXT.isOnline = isOnline;
	}

	public static String getVersion() {
		return version;
	}

	public static void setVersion(String version) {
		SmartGXT.version = version;
	}

}
