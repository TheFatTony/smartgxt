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
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.smartgxt.client.util.UIHelper;

/**
 * Managed Windows based on IsMainWindow and IsChildWindow interfaces.
 * 
 * @author Anton Alexeyev
 */

public class CopyOfWindowsManager {

	protected static List<Window> windows;
	protected static WindowListener listener;
	protected static Window activeWindow;

	public CopyOfWindowsManager() {
		windows = new ArrayList<Window>();
		initListeners();
		WindowManager.get().addListener(Events.Register, onRegister);
		ComponentManager.get()
				.addListener(Events.Register, onRegisterComponent);
	}

	private Listener<ComponentManagerEvent> onRegisterComponent = new Listener<ComponentManagerEvent>() {
		@Override
		public void handleEvent(ComponentManagerEvent be) {
			// TODO event happening twice
			final Component c = be.getComponent();
			if (c != null) {
				Window wnd = UIHelper.getParentWindowSilent(c);
				if ((wnd != null) && (wnd instanceof IsMainWindow)
						&& (c != wnd)
						&& (wnd.getData("sgxt.registred") == null)) {
					c.sinkEvents(Event.ONFOCUS);
					// TODO event fires two times
					c.addListener(Events.BrowserEvent, onBrowserEvent);
				}

			}
		}
	};

	private Listener<WindowManagerEvent> onRegister = new Listener<WindowManagerEvent>() {
		@Override
		public void handleEvent(WindowManagerEvent be) {
			final Window wnd = be.getWindow();

			if (wnd.getData("sgxt.registred") == null) {
				// wnd.addListener(Events.BrowserEvent, onBrowserEvent);
				// if (wnd instanceof IsMainWindow) {
				// addWindow(wnd);
				// wnd.setData("sgxt.registred", true);
				// } else if (wnd instanceof IsChildWindow) {
				// if (activeWindow != null) {
				// wnd.setMinimizable(false);
				// Window parent = activeWindow;
				// addChildToWindow(parent, wnd);
				// parent.addListener(Events.Activate, onActivateParent);
				// wnd.addListener(Events.BeforeHide, onChildBeforeHide);
				// wnd.addListener(Events.Activate, onChildActivate);
				// wnd.addListener(Events.Deactivate, onChildDeactivate);
				// wnd.setData("sgxt.registred", true);
				// }
				// }
			}
		}
	};

	private static Listener<ComponentEvent> onBrowserEvent = new Listener<ComponentEvent>() {
		@Override
		public void handleEvent(ComponentEvent be) {

			if ((be.getEvent() != null)
					&& (DOM.eventGetType(be.getEvent()) == Event.ONFOCUS)) {
				Window wnd = UIHelper.getParentWindowSilent(be.getComponent());
				System.out.println("ONFOCUS "
						+ be.getComponent().getClass().getName());
				wnd.setData("sgxt.focusedComponent", be.getComponent());
			}
		}
	};

	private Listener<WindowEvent> onChildBeforeHide = new Listener<WindowEvent>() {
		@Override
		public void handleEvent(WindowEvent be) {
			Window wnd = be.getWindow();
			if (wnd.getData("sgxt.minimize") == null) {
				getChildWindows(getWindowParent(wnd)).remove(wnd);
				setWindowParent(null, wnd);
			}
		}
	};

	private Listener<WindowEvent> onChildActivate = new Listener<WindowEvent>() {
		@Override
		public void handleEvent(WindowEvent be) {
			be.getWindow().getHeader().removeStyleName("ux-window-inactive");

		}
	};

	private Listener<WindowEvent> onChildDeactivate = new Listener<WindowEvent>() {
		@Override
		public void handleEvent(WindowEvent be) {
			be.getWindow().getHeader().addStyleName("ux-window-inactive");
		}
	};

	private List<Window> getChildWindows(Window wnd) {
		ArrayList<Window> childs = wnd.getData("sgxt.childs");
		if (childs == null) {
			childs = new ArrayList<Window>();
			wnd.setData("sgxt.childs", childs);
		}

		return childs;
	}

	public boolean windowHasChilds(Window wnd) {
		return getChildWindows(wnd).size() > 0 ? true : false;
	}

	private void addChildToWindow(Window parent, Window child) {
		getChildWindows(parent).add(child);
		setWindowParent(parent, child);
	}

	private void setWindowParent(Window parent, Window child) {
		child.setData("sgxt.parent", parent);
	}

	private Window getWindowParent(Window child) {
		return child.getData("sgxt.parent");
	}

	private Listener<WindowEvent> onActivateParent = new Listener<WindowEvent>() {
		@Override
		public void handleEvent(WindowEvent be) {
			final Window wnd = be.getWindow();

			for (final Window w : getChildWindows(wnd)) {
				if (!w.isVisible()) {
					w.setData("sgxt.minimize", null);
					w.show();
				}
				w.fireEvent(Events.Activate);
				w.toFront();
			}

			Timer timer = new Timer() {
				@Override
				public void run() {
					final Component comp = wnd.getData("sgxt.focusedComponent");
					if (comp != null) {
						comp.focus();
					}
				}
			};
			timer.schedule(250);
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
				markInactive(we.getWindow(), false);
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

	protected void onHide(Window window) {
		if (window.getData("sgxt.minimize") != null) {
			markInactive(window, true);
			return;
		}
		if (activeWindow == window) {
			activeWindow = null;
			List<Window> list = getChildWindows(window);
			for (final Window w : list) {
				// list.remove(w);
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						w.hide();

					}
				});
			}
		}
		window = null;
	}

	private void markActive(Window window) {
		if (activeWindow != null && activeWindow != window) {
			markInactive(activeWindow, true);
		}
		window.getHeader().removeStyleName("ux-window-inactive");
		activeWindow = window;
		window.setData("sgxt.minimize", null);
	}

	private void markInactive(Window window, boolean force) {
		if (force || ((window == activeWindow) && !windowHasChilds(window))) {
			window.getHeader().addStyleName("ux-window-inactive");
			activeWindow = null;
		}
	}

	private void onShow(final Window window) {
		window.setData("sgxt.minimize", null);
	}

	/**
	 * Minimizes the window.
	 * 
	 * @param window
	 *            the window to minimize
	 */
	public void minimizeWindow(Window window) {
		window.setData("sgxt.minimize", true);
		window.hide();
		for (Window w : getChildWindows(window)) {
			if (w.isVisible()) {
				w.setData("sgxt.minimize", true);
				w.hide();
			}
		}

	}

	/**
	 * Removes a window from the desktop.
	 * 
	 * @param window
	 *            the window to remove
	 */
	public void removeWindow(Window window) {
		if (windows.remove(window)) {
			window.removeWindowListener(listener);
			if (activeWindow == window) {
				activeWindow = null;
			}
		}
	}

	/**
	 * Adds a window to the desktop.
	 * 
	 * @param window
	 *            the window to add
	 */
	public static void addWindow(final Window window) {
		if (windows.add(window)) {
			window.addWindowListener(listener);
			// window.sinkEvents(Event.ONMOUSEDOWN);
		}
	}

}
