package com.smartgxt.client;

import com.extjs.gxt.themes.client.Slate;
import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.util.ThemeManager;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.Window.ClosingHandler;
import com.smartgxt.client.core.SmartGXT;

/**
 * Use this class as Google Web Toolkit EntryPoint in your application.
 * 
 * @author Anton Alexeyev
 */

public abstract class EntryPointPrototype implements CloseHandler<Window>,
		ClosingHandler, com.google.gwt.core.client.EntryPoint {

	private static String title;

	public EntryPointPrototype() {
		ThemeManager.register(Slate.SLATE);
		Window.addCloseHandler(this);
		Window.addWindowClosingHandler(this);

		SmartGXT.init();
		GXT.hideLoadingPanel("loading");
	}

	@Override
	public final void onModuleLoad() {

	}

	public static void setTitle(String text) {
		title = text;
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

}
