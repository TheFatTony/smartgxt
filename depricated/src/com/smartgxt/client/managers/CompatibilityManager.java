package com.smartgxt.client.managers;

import com.extjs.gxt.ui.client.event.BaseObservable;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.ComponentManagerEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.util.Point;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.ComponentManager;
import com.extjs.gxt.ui.client.widget.Document;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.custom.Portlet;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;

/**
 * 
 * Makes Application looks like a regular desktop application. Disables browser
 * shortkeys. Disables browser context menu and elements Drag&Drop. But this
 * actions still fires over selected text, and input fields.
 * 
 * @author Anton Alexeyev
 * 
 */
public class CompatibilityManager extends BaseObservable {

	private static CompatibilityManager instance;
	private static Point mousePosition;
	private static int mouseButton;

	public CompatibilityManager() {
		disableBrowserContextMenu();

		Document.get().addListener(Events.OnKeyDown, preventDefaultKeysListner);
		Document.get().addListener(Events.OnMouseWheel, onMouseWheel);
		Document.get().addListener(Events.OnMouseMove, onMouseMove);
		Document.get().addListener(Events.OnMouseDown, OnMouseDown);

		ComponentManager.get()
				.addListener(Events.Register, onRegisterComponent);

	}

	public static CompatibilityManager get() {
		if (instance == null)
			instance = new CompatibilityManager();
		return instance;
	}

	public static Point getMousePosition() {
		return mousePosition;
	}

	public static int getMouseButton() {
		return mouseButton;
	}

	private Listener<ComponentEvent> onMouseMove = new Listener<ComponentEvent>() {
		@Override
		public void handleEvent(ComponentEvent be) {
			mousePosition = new Point(be.getClientX(), be.getClientY());
		}
	};

	private Listener<ComponentEvent> OnMouseDown = new Listener<ComponentEvent>() {
		@Override
		public void handleEvent(ComponentEvent be) {
			mouseButton = be.getEvent().getButton();
		}
	};

	private Listener<ComponentEvent> onMouseWheel = new Listener<ComponentEvent>() {
		@Override
		public void handleEvent(ComponentEvent be) {
			if (be.isControlKey())
				be.preventDefault();
		}
	};

	private Listener<ComponentManagerEvent> onRegisterComponent = new Listener<ComponentManagerEvent>() {
		@Override
		public void handleEvent(ComponentManagerEvent be) {
			// TODO event happening twice
			final Component c = be.getComponent();
			if (c != null) {
				if (c.isDisableTextSelection() || (c instanceof Menu))
					disableBrowserDragAndDrop(c.getElement());
				if ((c instanceof Window) || (c instanceof Portlet)
						|| (c instanceof Viewport)) {
					c.sinkEvents(Event.ONCONTEXTMENU);
				}
				c.removeListener(Events.BrowserEvent, onBrowserEvent);
				c.addListener(Events.BrowserEvent, onBrowserEvent);

			}
		}
	};

	private static Listener<ComponentEvent> onBrowserEvent = new Listener<ComponentEvent>() {
		@Override
		public void handleEvent(ComponentEvent be) {
			if ((be.getEvent() != null)
					&& (DOM.eventGetType(be.getEvent()) == Event.ONCONTEXTMENU)) {
				disableEnableContextMenu(be);
			}
		}
	};

	private Listener<ComponentEvent> preventDefaultKeysListner = new Listener<ComponentEvent>() {
		public void handleEvent(ComponentEvent ce) {
			if ((ce.getKeyCode() >= 112) && (ce.getKeyCode() <= 123)) {
				ce.preventDefault();
			}
			// Disable all Control combinations except text manipulations
			else if ((ce.isControlKey() && !((ce.getKeyCode() == 90)
					|| (ce.getKeyCode() == 88) || (ce.getKeyCode() == 67)
					|| (ce.getKeyCode() == 86) || (ce.getKeyCode() == 89)
					|| (ce.getKeyCode() == 65) || (ce.getKeyCode() == 37) || (ce
					.getKeyCode() == 39))) && ((ce.getKeyCode() != -1))) {
				ce.preventDefault();
			}
			// Disable all Control+Shift combinations except text manipulations
			else if ((ce.isControlKey() && ce.isShiftKey())
					&& !((ce.getKeyCode() == 37) || (ce.getKeyCode() == 39))
					&& (ce.getKeyCode() != -1)) {
				ce.preventDefault();
			}
			// Disable all Control+Alt combinations
			else if ((ce.isControlKey() && ce.isAltKey())
					&& (ce.getKeyCode() != -1)) {
				ce.preventDefault();
			} else if ((ce.isAltKey()) && ((ce.getKeyCode() != -1))) {
				ce.preventDefault();
			}
		}
	};

	public static void disableEnableContextMenu(ComponentEvent event) {
		// TODO event happening twice
		if ((event.getTarget().getClassName().indexOf("x-box-item") != -1)
				|| (event.getTarget().getClassName().indexOf("x-form-text") != -1)
				|| (event.getTarget().getClassName().indexOf("x-form-field") != -1)
				|| (event.getTarget().getClassName()
						.indexOf("x-form-empty-field") != -1)
				|| (getSelectedText().length() > 0)) {
			// event.preventDefault();
			event.setCancelled(true);
			// event.stopEvent();
			// event.cancelBubble();
			enableBrowserContextMenu();

		} else {
			disableBrowserContextMenu();
		}
	}

	public static native void disableBrowserContextMenu() /*-{
		$doc.oncontextmenu = function() {
			return false;
		};
	}-*/;

	// enable browser context menu
	public static native void enableBrowserContextMenu() /*-{
		$doc.oncontextmenu = function() {
			return true;
		};
	}-*/;

	public static native void disableBrowserDragAndDrop(Element el) /*-{
		el.ondragstart = function() {
			return false;
		};
	}-*/;

	// TODO monitor active element only
	public static native String getSelectedText() 
	/*-{
			var txt = '';
			if ($wnd.getSelection) {
				txt = $wnd.getSelection();
			} else if ($doc.getSelection)
													    {
													        txt = $doc.getSelection();
													            }
													    else if ($doc.selection)
													    {
													        txt = $doc.selection.createRange().text;
													            }
													    else return;
													return txt.toString();
	}-*/;

	public static native void setTitle(String title) /*-{
		$doc.title = title;
	}-*/;

}
