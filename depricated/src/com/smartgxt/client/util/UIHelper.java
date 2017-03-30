package com.smartgxt.client.util;

import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.tips.ToolTipConfig;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Anton Alexeyev
 * 
 */
public class UIHelper {

	private static ToolTipConfig ttc;

	public static Window getParentWindow(Widget component) {
		if (component instanceof Window)
			return (Window) component;

		Window wnd;
		Widget w = component.getParent();

		assert w != null : "Must be called after component attached to it's parent";
		if (w instanceof Window)
			wnd = (Window) w;
		else
			wnd = getParentWindow(w);

		assert wnd != null : "Must be called after components tree attached to it's Window";
		return wnd;
	}


	public static Window getParentWindowSilent(Widget component) {
		if (component instanceof Window)
			return (Window) component;

		Window wnd;
		Widget w = component.getParent();

		if (w != null) {
			if (w instanceof Window)
				wnd = (Window) w;
			else
				wnd = getParentWindowSilent(w);
		} else
			return null;

		return wnd;
	}

	public static String getComponentText(Widget component) {
		String text = null;
		if (component instanceof Button)
			text = ((Button) component).getText();
		return text;
	}

	public static ToolTipConfig newToolTipConfig() {
		if (ttc == null) {
			ttc = new ToolTipConfig();
			ttc.setTrackMouse(true);
		}
		return ttc;
	}

	public static void mask(Component component) {
		component.mask("Загрузка...", "x-mask-loading");
	}

	public static void unmask(Component component) {
		component.unmask();
	}

}
