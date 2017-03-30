package com.smartgxt.client.managers;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.ComponentManagerEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.WindowEvent;
import com.extjs.gxt.ui.client.event.WindowListener;
import com.extjs.gxt.ui.client.event.WindowManagerEvent;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.ComponentManager;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.WindowManager;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.smartgxt.client.util.UIHelper;

/**
 * Класс Менеджер окон автоматически определяет принадлежность окон их родителем
 * и управляет позиционированием. Временно поддерживает только модальные окна.
 * Для работы окна с данным клаcсом достаточно пометить его интерфейсом
 * IsWindow. Делать setModel не нужно
 * 
 * @author Anton Alexeyev
 */

public class SmartWindowsManager {

	private final static String scStyleInactive = "ux-window-inactive";
	private final static String scRegistred = "sgxt.registred";
	private final static String scMinimized = "sgxt.minimized";
	private final static String scParent = "sgxt.parent";
	private final static String scChild = "sgxt.childs";

	protected static List<Window> windows;
	protected static WindowListener listener;
	protected static Window activeWindow;

	public SmartWindowsManager() {
		windows = new ArrayList<Window>();
		initListeners();
		WindowManager.get().addListener(Events.Register, onRegisterWindow);
		ComponentManager.get()
				.addListener(Events.Register, onRegisterComponent);
	}

	private Listener<WindowManagerEvent> onRegisterWindow = new Listener<WindowManagerEvent>() {
		@Override
		public void handleEvent(WindowManagerEvent be) {
			final Window wnd = be.getWindow();
			if (wnd.getData(scRegistred) == null) {
				addWindow(wnd);
				// if ((activeWindow != null)
				// && (activeWindow instanceof IsManagedWindow)
				// && !(wnd instanceof IsMainWindow)) {
				// attachWindowChild(activeWindow, wnd);
				// }
			}
		}
	};

	private Listener<ComponentManagerEvent> onRegisterComponent = new Listener<ComponentManagerEvent>() {
		@Override
		public void handleEvent(final ComponentManagerEvent be) {

			final Component c = be.getComponent();
			if (c != null) {
				final Window wnd = UIHelper.getParentWindowSilent(c);
				// if ((wnd != null) && (wnd instanceof IsManagedWindow)
				// && (c != wnd) && (wnd.getData(scRegistred) == null)) {
				// c.sinkEvents(Event.FOCUSEVENTS);
				// c.addListener(Events.BrowserEvent,
				// new Listener<ComponentEvent>() {
				// @Override
				// public void handleEvent(ComponentEvent be) {
				// if ((be.getEvent() != null)
				// && (DOM.eventGetType(be.getEvent()) == Event.ONFOCUS)) {
				// markActive(wnd);
				// } else if ((be.getEvent() != null)
				// && (DOM.eventGetType(be.getEvent()) == Event.ONBLUR)) {
				// markInactive(wnd);
				// }
				// }
				// });
				// }
			}

		}
	};

	protected void initListeners() {
		listener = new WindowListener() {
			@Override
			public void windowActivate(WindowEvent we) {
				markActive(we.getWindow());
			}

			@Override
			public void windowDeactivate(WindowEvent we) {
				markInactive(we.getWindow());
			}

			@Override
			public void windowHide(WindowEvent we) {
				onHide(we.getWindow());
			}

			@Override
			public void windowMinimize(WindowEvent we) {
				minimizeWindow(we.getWindow());
			}

			@Override
			public void windowShow(WindowEvent we) {
				onShow(we.getWindow());
			}
		};
	}

	public void addWindow(final Window window) {
		if (windows.add(window)) {
			window.setData(scRegistred, true);
			window.addWindowListener(listener);
			window.sinkEvents(Event.FOCUSEVENTS);
			window.addListener(Events.BrowserEvent,
					new Listener<ComponentEvent>() {
						@Override
						public void handleEvent(ComponentEvent be) {
							if ((be.getEvent() != null)
									&& (DOM.eventGetType(be.getEvent()) == Event.ONBLUR)) {
								markInactive(window);
							} else if ((be.getEvent() != null)
									&& (DOM.eventGetType(be.getEvent()) == Event.ONFOCUS)) {
								markActive(window);
							}
						}
					});
			window.getHeader().addListener(Events.OnClick,
					new Listener<ComponentEvent>() {
						public void handleEvent(ComponentEvent be) {
							window.focus();
						}
					});
		}
	}

	public void removeWindow(Window window) {

		if (windows.remove(window)) {
			window.removeWindowListener(listener);
			if (activeWindow == window) {
				activeWindow = null;
			}
			window = null;
		}
	}

	private void markActive(Window window) {
		System.out.println("markActive");
		if (activeWindow != null && activeWindow != window) {
			markInactive(activeWindow);
		}
		window.getHeader().removeStyleName(scStyleInactive);
		activeWindow = window;
		window.setData(scMinimized, null);

		Window wnd = getWindowChild(window);
		if (wnd != null) {
			if (!wnd.isVisible()) {
				wnd.setData(scMinimized, null);
				wnd.show();
			}
			wnd.fireEvent(Events.Activate);
			wnd.toFront();
		}
	}

	private void markInactive(Window window) {
		System.out.println("markInactive");
		if (window == activeWindow) {
			window.getHeader().addStyleName(scStyleInactive);
			activeWindow = null;
		}
	}

	protected void onHide(Window window) {
		if (window.getData(scMinimized) != null) {
			markInactive(window);
			return;
		}
		removeWindow(window);
	}

	public void minimizeWindow(Window window) {
		window.setData(scMinimized, true);
		// window.collapse();
		// window.hide();
		// TODO fire event about Windows going to minimize
	}

	private void onShow(final Window window) {
		window.setData(scMinimized, null);
	}

	private void attachWindowChild(Window parent, Window child) {
		setWindowChild(parent, child);
		setWindowParent(parent, child);
		parent.getBody().mask();
		parent.addListener(Events.BeforeHide, onParentBeforeHide);
		child.addListener(Events.BeforeHide, onChildBeforeHide);
	}

	private Listener<WindowEvent> onChildBeforeHide = new Listener<WindowEvent>() {
		@Override
		public void handleEvent(WindowEvent be) {
			Window wnd = be.getWindow();

			Window parent = getWindowParent(wnd);
			parent.getBody().unmask();
		}
	};

	private Listener<WindowEvent> onParentBeforeHide = new Listener<WindowEvent>() {
		@Override
		public void handleEvent(WindowEvent be) {
			Window wnd = be.getWindow();
			if (wnd.getData(scMinimized) == null) {
				removeChilds(wnd);
			}
		}
	};

	public void removeChilds(Window window) {

		Window child = getWindowChild(window);
		if (child != null) {
			removeChilds(child);
			Window parent = getWindowParent(child);
			parent.getBody().unmask();

			if (child.isVisible())
				child.hide();
			setWindowParent(null, child);
			setWindowChild(parent, null);

			window = null;

		}
	}

	private void setWindowChild(Window parent, Window child) {
		parent.setData(scChild, child);
	}

	private void setWindowParent(Window parent, Window child) {
		child.setData(scParent, parent);
	}

	private Window getWindowParent(Window child) {
		return child.getData(scParent);
	}

	private Window getWindowChild(Window parent) {
		return parent.getData(scChild);
	}
}
