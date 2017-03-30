package com.smartgxt.client.notifications;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Anton Alexeyev
 * 
 */
public class WebkitNotificationImpl {
	public static final int PERMISSION_ALLOWED = 0;
	public static final int PERMISSION_NOT_ALLOWED = 1;
	public static final int PERMISSION_DENIED = 2;
	private JavaScriptObject jsObject;
	private Command command;

	protected WebkitNotificationImpl() {

	}

	public native static int checkPermission() /*-{
		return $wnd.webkitNotifications.checkPermission();
	}-*/;

	public static void requestPermission(AsyncCallback<Void> callback) {
		requestPermissionImpl(callback);
	}

	private native static void requestPermissionImpl(
			AsyncCallback<Void> callback) /*-{
		var _this = this;
		$wnd.webkitNotifications
				.requestPermission($entry(function() {
					_this.@com.smartgxt.client.notifications.WebkitNotificationImpl::callbackRequestPermission(Lcom/google/gwt/user/client/rpc/AsyncCallback;)(callback);
				}));
	}-*/;

	private void callbackRequestPermission(AsyncCallback<Void> callback) {
		if (callback != null) {
			callback.onSuccess(null);
		}
	}

	public void display(String imageUrl, String title, String text,
			int hideDelay, Command command) {
		this.jsObject = null;

		if (imageUrl == null)
			this.jsObject = this.createJSNotification("", title, text);
		else
			this.jsObject = this.createJSNotification(imageUrl, title, text);

		this.command = command;
		show(String.valueOf(hideDelay));
	}

	public void createNotification(String contentUrl) {
		this.jsObject = null;
		this.jsObject = this.createHtmlNotification(contentUrl);
	}

	private native JavaScriptObject createJSNotification(String iconUrl,
			String title, String body) /*-{
		return $wnd.webkitNotifications
				.createNotification(iconUrl, title, body);
	}-*/;

	private native JavaScriptObject createHtmlNotification(String contentUrl) /*-{
		return $wnd.webkitNotifications.createHTMLNotification(contentUrl);
	}-*/;

	public void execute() {
		if (command != null)
			command.execute();
	}

	public native void show(String hideDelay) /*-{
		var obj = this.@com.smartgxt.client.notifications.WebkitNotificationImpl::jsObject;
		var _this = this;
		obj.onclick = function(event) {
			obj.cancel();
			$wnd.focus();
			_this.@com.smartgxt.client.notifications.WebkitNotificationImpl::execute()();
		};
		obj.show();
		if (hideDelay != 0)
			setTimeout(function() {
				obj.cancel();
			}, hideDelay);
	}-*/;
}
