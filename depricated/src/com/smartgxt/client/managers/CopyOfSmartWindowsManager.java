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
import com.google.gwt.user.client.Timer;
import com.smartgxt.client.util.UIHelper;

/**
 * Класс Менеджер окон автоматически определяет принадлежность окон их родителем
 * и управляет позиционированием. Временно поддерживает только модальные окна.
 * Для работы окна с данным клаcсом достаточно пометить его интерфейсом
 * IsWindow. Делать setModel не нужно
 * 
 * @author Anton Alexeyev
 */

public class CopyOfSmartWindowsManager {

	private final static String StyleInactive = "ux-window-inactive";
	private final static String Registred = "sgxt.registred";
	private final static String Minimized = "sgxt.minimized";
	private final static String Parent = "sgxt.parent";
	private final static String Childs = "sgxt.childs";
	private final static String Focused = "sgxt.focusedComponent";

	protected List<Window> windows;
	protected WindowListener listener;
	protected Window activeWindow;

	public CopyOfSmartWindowsManager() {
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
			// if (wnd.getData(Registred) == null) {
			// addWindow(wnd);
			// if ((activeWindow != null)
			// && (activeWindow instanceof IsManagedWindow)) {
			// System.out.println("detectd chiled for "
			// + activeWindow.getHeading() + " child is "
			// + wnd.getHeading());
			// attachChildToParent(activeWindow, wnd);
			// }
			// }
		}
	};

	private Listener<ComponentManagerEvent> onRegisterComponent = new Listener<ComponentManagerEvent>() {
		@Override
		public void handleEvent(ComponentManagerEvent be) {
			final Component c = be.getComponent();
			// if (c != null) {
			// final Window wnd = UIHelper.getParentWindowSilent(c);
			// if ((wnd != null) && (wnd instanceof IsManagedWindow)
			// && (c != wnd) && (wnd.getData(Registred) == null)) {
			// c.sinkEvents(Event.FOCUSEVENTS);
			// c.addListener(Events.BrowserEvent,
			// new Listener<ComponentEvent>() {
			// @Override
			// public void handleEvent(ComponentEvent be) {
			// if ((be.getEvent() != null)
			// && (DOM.eventGetType(be.getEvent()) == Event.ONFOCUS)) {
			// markActive(wnd);
			// wnd.setData(Focused, be.getComponent());
			// }
			// }
			// });
			// }
			// }
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

	private List<Window> getChildWindows(Window wnd, boolean create) {
		ArrayList<Window> childs = wnd.getData(Childs);
		if ((childs == null) && create) {
			childs = new ArrayList<Window>();
			wnd.setData(Childs, childs);
		}
		return childs;
	}

	private void attachChildToParent(Window parent, Window child) {
		getChildWindows(parent, true).add(child);
		setWindowParent(parent, child);
	}

	private void setWindowParent(Window parent, Window child) {
		child.setData(Parent, parent);
	}

	private Window getWindowParent(Window child) {
		return child.getData(Parent);
	}

	public void addWindow(final Window window) {
		if (windows.add(window)) {
			window.setData(Registred, true);
			window.addWindowListener(listener);
			window.sinkEvents(Event.FOCUSEVENTS);
			window.addListener(Events.BrowserEvent, onFocuseEvents);
		}
	}

	private Listener<ComponentEvent> onFocuseEvents = new Listener<ComponentEvent>() {
		@Override
		public void handleEvent(ComponentEvent be) {
			Window wibdow = windows.get(windows.size() - 1);
			if ((be.getEvent() != null)
					&& (DOM.eventGetType(be.getEvent()) == Event.ONBLUR)) {
				System.out
						.println("addWindow BrowserEvent ONBLUR call markInactive");
				markInactive(wibdow);
			} else if ((be.getEvent() != null)
					&& (DOM.eventGetType(be.getEvent()) == Event.ONFOCUS)) {
				System.out
						.println("addWindow BrowserEvent ONFOCUS call markActive");
				markActive(wibdow);
			}
		}
	};

	private void markActive(final Window window) {
		System.out.println("markActive = " + window.getHeading());
		if (activeWindow != null && activeWindow != window) {
			System.out.println("markActive markInactive");
			markInactive(activeWindow);
		}
		window.getHeader().removeStyleName(StyleInactive);
		activeWindow = window;
		window.setData(Minimized, null);

		List<Window> childs = getChildWindows(window, false);
		if (childs != null) {
			for (Window wnd : childs) {
				if (!wnd.isVisible()) {
					wnd.setData(Minimized, null);
					wnd.show();
				}
				wnd.fireEvent(Events.Activate);
				wnd.toFront();
			}
		}
	}

	private void markInactive(Window window) {
		System.out.println("markInactive = " + window.getHeading());
		if (window == activeWindow) {
			window.getHeader().addStyleName(StyleInactive);
			activeWindow = null;
		}
	}

	public void removeWindow(Window window) {
		if (windows.remove(window)) {
			window.removeWindowListener(listener);
			if (activeWindow == window) {
				activeWindow = null;
			}
		}
	}

	public void minimizeWindow(Window window) {
		window.collapse();
		window.setData(Minimized, true);
		// window.hide();
		// TODO fire event about Windows going to minimize
	}

	protected void onHide(Window window) {
		if (window.getData(Minimized) != null) {
			markInactive(window);
			return;
		}
		// if (activeWindow == window) {
		// activeWindow = null;
		// }
		removeWindow(window);
	}

	private void onShow(final Window window) {
		window.setData(Minimized, null);
	}

}
