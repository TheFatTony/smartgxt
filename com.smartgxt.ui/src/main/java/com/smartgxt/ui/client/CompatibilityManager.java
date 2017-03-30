package com.smartgxt.ui.client;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Component;
import com.sencha.gxt.widget.core.client.ComponentHelper;
import com.sencha.gxt.widget.core.client.WindowManager;
import com.sencha.gxt.widget.core.client.menu.Menu;

public class CompatibilityManager {
	private static HandlerRegistration nativePreviewHandlerRegistration;
	private static HandlerRegistration windowResizeHandlerRegistration;

	public static void enable() {
		nativePreviewHandlerRegistration = Event
				.addNativePreviewHandler(nativePreviewHandler);

		windowResizeHandlerRegistration = Window
				.addResizeHandler(windowResizeHandler);
	}

	public static void disable() {
		nativePreviewHandlerRegistration.removeHandler();
		windowResizeHandlerRegistration.removeHandler();
	}

	private static ResizeHandler windowResizeHandler = new ResizeHandler() {

		@Override
		public void onResize(ResizeEvent event) {
			for (Widget w : WindowManager.get().getWindows()) {
				if (w instanceof com.smartgxt.ui.client.windows.Window) {
					com.smartgxt.ui.client.windows.Window window = (com.smartgxt.ui.client.windows.Window) w;
					if (window.isCentered())
						window.center();
				}
			}
		}
	};
	
	private static NativePreviewHandler nativePreviewHandler = new NativePreviewHandler() {
		public void onPreviewNativeEvent(NativePreviewEvent preview) {
			NativeEvent event = preview.getNativeEvent();
			Element element = event.getEventTarget().cast();

			if ((event.getType().equalsIgnoreCase("click") || event.getType()
					.equalsIgnoreCase("dblclick"))
					&& (event.getButton() == Event.BUTTON_RIGHT)) {
				smartContextMenu(preview, element);
			} else if (event.getType().equalsIgnoreCase("keypress")) {
				disbleBrowserShortcuts(preview, element);
			} else if (event.getType().equalsIgnoreCase("DOMMouseScroll")) {
				if (event.getCtrlKey() || event.getShiftKey()) {
					event.preventDefault();
					// event.stopPropagation();
					// preview.cancel();
				}
			}
		}
	};

	public static Component getComponent(Element element) {
		Widget w = ComponentHelper.getWidgetWithElement(element);
		if (w instanceof Component) {
			return (Component) w;
		}
		return null;

	}

	public static void smartContextMenu(NativePreviewEvent preview,
			Element element) {
		NativeEvent event = preview.getNativeEvent();
		if (element.getTagName().equalsIgnoreCase("input")) {
		} else if (!getSelectedText().isEmpty()) {
			Component comp = getComponent(element);
			if ((comp != null) && (getComponentContextMenuDisabled(comp)))
				comp.unsinkEvents(Event.ONCONTEXTMENU);
		} else {
			Component comp = getComponent(element);
			if ((comp != null) && (getComponentContextMenuDisabled(comp))) {
				comp.sinkEvents(Event.ONCONTEXTMENU);
			} else {
				event.preventDefault();
				// event.stopPropagation();
				// preview.cancel();
			}
		}
	}

	public static void disbleBrowserShortcuts(NativePreviewEvent preview,
			Element element) {
		NativeEvent event = preview.getNativeEvent();
		int keycode = event.getKeyCode();
		boolean ctrl = event.getCtrlKey();
		boolean shift = event.getShiftKey();
		boolean alt = event.getAltKey();
		if ((keycode >= 112) && (keycode <= 123)) {
			event.preventDefault();
			event.stopPropagation();
			preview.cancel();
		}
		// Disable all Control combinations except text
		// manipulations
		else if ((ctrl && !((keycode == 90) || (keycode == 88)
				|| (keycode == 67) || (keycode == 86) || (keycode == 89)
				|| (keycode == 65) || (keycode == 37) || (keycode == 39)))
				&& ((keycode != -1))) {
			event.preventDefault();
			// event.stopPropagation();
			// preview.cancel();
		}
		// Disable all Control+Shift combinations except text
		// manipulations
		else if ((ctrl && shift) && !((keycode == 37) || (keycode == 39))
				&& (keycode != -1)) {
			event.preventDefault();
			// event.stopPropagation();
			// preview.cancel();
		}
		// Disable all Control+Alt combinations
		else if ((ctrl && alt) && (keycode != -1)) {
			event.preventDefault();
			event.stopPropagation();
		} else if ((alt) && ((keycode != -1))) {
			event.preventDefault();
			// event.stopPropagation();
			// preview.cancel();
		}
	}

	public static native Menu getComponentContextMenu(Component component)
	/*-{
			return component.@com.sencha.gxt.widget.core.client.Component::contextMenu;
	}-*/;

	public static native boolean getComponentContextMenuDisabled(
			Component component)
	/*-{
			return component.@com.sencha.gxt.widget.core.client.Component::disableContextMenu;
	}-*/;

	public static native String getSelectedText()
	/*-{	
	 		var txt = '';
			if ($wnd.getSelection) {
				txt = $wnd.getSelection();
			} else if ($doc.getSelection) {
				txt = $doc.getSelection();
			} else if ($doc.selection) {
				txt = $doc.selection.createRange().text; }
			else return;
			
			return txt.toString();
	}-*/;

}
