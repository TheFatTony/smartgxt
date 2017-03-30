package com.smartgxt.ui.client.desktop.ui.taskbar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public class TaskBarStyle {

	private static Bundle instance = GWT.create(Bundle.class);
	private static boolean injected;

	static interface Bundle extends ClientBundle {

		@Source("TaskBar.css")
		Styles styles();
	}

	public TaskBarStyle() {

	}

	public static Styles get() {
		if (!injected) {
			StyleInjector.inject(instance.styles().getText(), true);
			injected = true;
		}
		return instance.styles();
	}

	public interface Styles extends CssResource {

		String leftAlignButton();

	}

}
