package com.smartgxt.core.client;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.Window.ClosingHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Use this class as Google Web Toolkit EntryPoint in your application.
 * 
 * @author Anton Alexeyev
 */

public abstract class EntryPointPrototype implements CloseHandler<Window>,
		ClosingHandler, com.google.gwt.core.client.EntryPoint {

	private static String title;
	private static boolean isValid = true;

	public EntryPointPrototype(final String title) {
		setTitle(title);

		Window.addCloseHandler(this);
		Window.addWindowClosingHandler(this);

		SmartGXT.init();

		

	}

	@Override
	public void onModuleLoad() {
		DOM.getElementById("sgxt-loading").removeFromParent();
	}

	public static void updateTitle(String text) {
		Document.get().setTitle(title + text);
	}

	public static void setTitle(String text) {
		EntryPointPrototype.title = text;
		Document.get().setTitle(text);
	}

	public static String getTitle() {
		return title;
	}

	@Override
	public void onWindowClosing(final ClosingEvent event) {
	}

	@Override
	public void onClose(CloseEvent<Window> event) {

	}

	public abstract void init();

	public static boolean isValid() {
		return isValid;
	}

	protected static void setValid(boolean isValid) {
		EntryPointPrototype.isValid = isValid;
	}

	public void add(IsWidget widget) {
		RootPanel.get().add(widget);
	}
}
