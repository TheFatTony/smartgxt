package com.smartgxt.client.notifications;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Anton Alexeyev
 * 
 */
public class Notification {

	WebkitNotificationImpl impl = GWT.create(WebkitNotificationImpl.class);

	/**
	 * Detector for browser support of Desktop Notification.
	 */
	private static class NotificationSupportDetector {
		private final boolean isNotificationSupported = detectNotificationSupport();

		public boolean isNotificationSupported() {
			return this.isNotificationSupported;
		}

		private native boolean detectNotificationSupport() /*-{
			return typeof $wnd.webkitNotifications != "undefined";
		}-*/;
	}

	/**
	 * Detector for browser support of Desktop Notification.
	 */
	@SuppressWarnings("unused")
	private static class NotificationSupportDetectorNo extends
			NotificationSupportDetector {
		@Override
		public boolean isNotificationSupported() {
			return false;
		}
	}

	private static int permission = -1;

	private static NotificationSupportDetector supportDetectorImpl;

	// private static Notification notification;

	// TODO: Some variable

	// TODO: Some API function for event

	/**
	 * Check current status of notification is allowed or not
	 * 
	 * @return true if user allow to use notification
	 */
	public static boolean isNotificationAllowed() {
		checkPermission();
		return permission == WebkitNotificationImpl.PERMISSION_ALLOWED;
	}

	/**
	 * Check current status of notification is set or not
	 * 
	 * @return true if user doesn't set permission (never choose 'Allow' or
	 *         'Deny')
	 */
	public static boolean isNotificationNotAllowed() {
		checkPermission();
		return permission == WebkitNotificationImpl.PERMISSION_NOT_ALLOWED;
	}

	/**
	 * Check current status of notification is denied or not
	 * 
	 * @return true if user deny to use notification
	 */
	public static boolean isNotificationDenied() {
		checkPermission();
		return permission == WebkitNotificationImpl.PERMISSION_DENIED;
	}

	/**
	 * Get current status of notification permission
	 * 
	 * @return
	 */
	public static int checkPermission() {
		// if (permission == -1) {
		permission = WebkitNotificationImpl.checkPermission();
		// }
		return permission;
	}

	public static void requestPermission() {
		WebkitNotificationImpl.requestPermission(null);
	}

	public static void requestPermission(AsyncCallback<Void> callback) {
		WebkitNotificationImpl.requestPermission(callback);
	}

	public static boolean isSupported() {
		return getNotificationSupportDetector().isNotificationSupported();
	}

	private static NotificationSupportDetector getNotificationSupportDetector() {
		if (supportDetectorImpl == null) {
			supportDetectorImpl = GWT.create(NotificationSupportDetector.class);
		}
		return supportDetectorImpl;
	}

	// public static Notification createIfSupported(String contentUrl) {
	// if (isSupported()) {
	// if (notification == null) {
	// notification = new Notification(contentUrl);
	// }
	// return notification;
	// }
	// return null;
	// }

	public static void show(String icon, String title, String text,
			Command command) {
		if (isSupported() && isNotificationAllowed()) {
			Notification notification = new Notification(icon, title, text, 0,
					command);
			notification.show();
		}
	}

	public static void show(String title, String text, Command command) {
		if (isSupported() && isNotificationAllowed()) {
			Notification notification = new Notification(null, title, text, 0,
					command);
			notification.show();
		}
	}

	public static void show(String icon, String title, String text,
			int hideDelay, Command command) {
		if (isSupported() && isNotificationAllowed()) {
			Notification notification = new Notification(icon, title, text,
					hideDelay, command);
			notification.show();
		}
	}

	// private String contentUrl;
	private String icon;
	private String title;
	private String body;
	private Command command;
	private int hideDelay;

	// private Notification(String contentUrl) {
	// this.setContentUrl(contentUrl);
	// }

	private Notification(String icon, String title, String body, int hideDelay,
			Command command) {
		// this.setContentUrl(null);
		this.setIcon(icon);
		this.setTitle(title);
		this.setBody(body);
		this.setCommand(command);
		this.setHideDelay(hideDelay);
	}

	public void show() {
		impl.display(getIcon(), getTitle(), getBody(), getHideDelay(),
				getCommand());
	}

	// public void setContentUrl(String contentUrl) {
	// this.contentUrl = contentUrl;
	// }
	//
	// public String getContentUrl() {
	// return contentUrl;
	// }

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIcon() {
		return icon;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getBody() {
		return body;
	}

	public void setCommand(Command command) {
		this.command = command;
	}

	public Command getCommand() {
		return command;
	}

	public void setHideDelay(int hideDelay) {
		this.hideDelay = hideDelay;
	}

	public int getHideDelay() {
		return hideDelay;
	}
}
