package com.smartgxt.ui.client.windowmanager;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.AccessStack;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.event.RegisterEvent;
import com.sencha.gxt.widget.core.client.event.RegisterEvent.RegisterHandler;
import com.sencha.gxt.widget.core.client.event.UnregisterEvent;
import com.sencha.gxt.widget.core.client.event.UnregisterEvent.UnregisterHandler;

//import com.extjs.gxt.ui.client.event.DragEvent;
//import com.extjs.gxt.ui.client.event.Events;
//import com.extjs.gxt.ui.client.event.Listener;
//import com.extjs.gxt.ui.client.event.WindowManagerEvent;
//import com.extjs.gxt.ui.client.widget.Window;

public class WindowManager extends
		com.sencha.gxt.widget.core.client.WindowManager {

	private static WindowManager instance;

	/**
	 * Returns the singleton instance.
	 * 
	 * @return the window manager
	 */
	public static WindowManager get() {
		if (instance == null)
			instance = new WindowManager();
		return instance;
	}

	private List<Window> windows;
	private AccessStack<Window> accessList;
	private Widget front;
	private List<Widget> widgets;
	private SimpleEventBus eventBus;

	/**
	 * Creates a window manager.
	 */
	public WindowManager() {
		// System.out.println("WindowManager.WindowManager()");
		accessList = new AccessStack<Window>();
		widgets = new ArrayList<Widget>();
		windows = new ArrayList<Window>();
	}

	/**
	 * Activates the next window in the access stack.
	 * 
	 * @param widget
	 *            the reference window
	 * @return true if the next window exists
	 */
	public boolean activateNext(Widget widget) {
		System.out.println("WindowManager.activateNext not implemented");
		return false;
		// int count = widgets.size();
		// if (count > 1) {
		// int idx = widgets.indexOf(widget);
		// if (idx == count - 1) {
		// return false;
		// }
		// setActiveWin(widgets.get(++idx));
		// return true;
		// }
		// return false;
	}

	/**
	 * Activates the previous widget in the access stack.
	 * 
	 * @param widget
	 *            the reference window
	 * @return true if a previous window exists
	 */
	public boolean activatePrevious(Widget widget) {
		// System.out.println("WindowManager.activatePrevious not implemented");
		return false;
		// int count = widgets.size();
		// if (count > 1) {
		// int idx = widgets.indexOf(widget);
		// if (idx == 0) {
		// return false;
		// }
		// setActiveWin(widgets.get(--idx));
		// }
		// return false;
	}

	@Override
	public HandlerRegistration addRegisterHandler(
			RegisterHandler<Widget> handler) {
		return ensureHandlers().addHandler(RegisterEvent.getType(), handler);
	}

	@Override
	public HandlerRegistration addUnregisterHandler(
			UnregisterHandler<Widget> handler) {
		return ensureHandlers().addHandler(UnregisterEvent.getType(), handler);
	}

	/**
	 * Brings the specified widget to the front of any other active widgets.
	 * {@link Window} instances will automatically call this method when
	 * focused, all other types must call manually.
	 * 
	 * @param widget
	 *            the window
	 * @return true if the widget was brought to the front, else false if it was
	 *         already in front
	 */
	public boolean bringToFront(Widget widget) {
		Window window = (Window) widget;
		// if (!WindowManagerHelper.isDragging(window)) {
		if (widget != WindowManagerHelper.getFront()) {
			WindowManagerHelper.fixCiteria(window);
			WindowManagerHelper.orderWindowsBy(window, accessList.getStack(),
					false);
			return true;
		} else {
			window.focus();
		}
		// } else {
		// left = window.getAbsoluteLeft();
		// top = window.getAbsoluteTop();
		// // window.getDraggable().removeListener(Events.DragEnd, onDragEnd);
		// // window.getDraggable().addListener(Events.DragEnd, onDragEnd);
		// // window23.setPosition(left, top);
		// }
		return false;
		// if (widget != front) {
		// accessList.add(widget);
		// activateLast();
		// return true;
		// } else {
		// focus(widget);
		// }
		// return false;
	}

	/**
	 * Gets the currently-active widget in the group.
	 * 
	 * @return the active window
	 */
	public Widget getActive() {
		return WindowManagerHelper.getFront();
		// return WindowManagerHelper.getActive();
		// return front;
	}

	/**
	 * Returns the ordered widgets.
	 * 
	 * @return the widgets
	 */
	// public Stack<Widget> getStack() {
	// System.out.println("WindowManager.getStack()");
	// return ()accessList.getStack();
	// }

	/**
	 * Returns the visible widgets.
	 * 
	 * @return the widgets
	 */
	// public List<Widget> getWindows() {
	// System.out.println("WindowManager.getWindows()");
	// return widgets;
	// }

	/**
	 * Hides all widgets that are registered to this WindowManager.
	 */
	public void hideAll() {
		System.out.println("WindowManager.hideAll not implemented");
		// for (int i = accessList.size() - 1; i >= 0; --i) {
		// accessList.get(i).setVisible(false);
		// }
	}

	/**
	 * Registers the window with the WindowManager. {@link Window} instances
	 * will automatically register and unregister themselves. All other types
	 * must register / unregister manually.
	 * 
	 * @param widget
	 *            the window
	 */
	public void register(Widget widget) {
		widgets.add(widget);
		Window window = (Window) widget;
		if (!WindowManagerHelper.hasParent(window)) {
			accessList.getStack().push(window);
			windows.add(window);
		}
		WindowManagerHelper.showAll(window);
		ensureHandlers().fireEvent(new RegisterEvent<Widget>(window));
		// accessList.add(widget);
		// widgets.add(widget);
		// ensureHandlers().fireEvent(new RegisterEvent<Widget>(widget));
	}

	/**
	 * Sends the specified window to the back of other active widgets.
	 * 
	 * @param widget
	 *            the widget
	 */
	public void sendToBack(Widget widget) {
		System.out.println("WindowManager.sendToBack() non umplemented");
		// accessList.getStack().pop();
		// accessList.getStack().insertElementAt(widget, 0);
		// activateLast();
	}

	/**
	 * Unregisters the window with the WindowManager.
	 * 
	 * @param widget
	 *            the widget
	 */
	public void unregister(Window widget) {
		widgets.remove(widget);
		Window window = (Window) widget;
		if (WindowManagerHelper.front == window) {
			WindowManagerHelper.front = null;
		}

		if (!WindowManagerHelper.hasParent(window)) {
			accessList.remove(window);
			windows.remove(window);
		}
		// activateLast();
		WindowManagerHelper.hideAll(window);
		WindowManagerHelper.activateLast(accessList.getStack());
		ensureHandlers().fireEvent(new UnregisterEvent<Widget>(window));
		// if (front == widget) {
		// front = null;
		// }
		// accessList.remove(widget);
		// widgets.remove(widget);
		// activateLast();
		// ensureHandlers().fireEvent(new UnregisterEvent<Widget>(widget));
	}

	public SimpleEventBus ensureHandlers() {
		return eventBus == null ? eventBus = new SimpleEventBus() : eventBus;
	}

	/**
	 * Returns the visible widgets.
	 * 
	 * @return the widgets
	 */
	public List<Widget> getWindows() {
		return widgets;
	}
	//
	// private void activateLast() {
	// System.out.println("WindowManager.activateLast() non umplemented");
	// // for (int i = accessList.size() - 1; i >= 0; --i) {
	// // Window w = (Window) accessList.get(i);
	// // if (w.isVisible()) {
	// // setActiveWin(w);
	// // return;
	// // }
	// // }
	// // setActiveWin(null);
	// }

	// private void focus(Widget widget) {
	// System.out.println("WindowManager.focus() non umplemented");
	// // if (widget instanceof Component) {
	// // ((Component) widget).focus();
	// // } else {
	// // widget.getElement().focus();
	// // }
	// }
	//
	// private void setActiveWin(Widget window) {
	// System.out.println("WindowManager.setActiveWin() non umplemented");
	// // if (window != front) {
	// // if (front != null) {
	// // if (window instanceof Window) {
	// // ((Window) front).setActive(false);
	// // }
	// // }
	// // front = window;
	// // if (window != null) {
	// // if (window instanceof Window) {
	// // Window w = (Window) window;
	// // w.setActive(true);
	// // w.setZIndex(XDOM.getTopZIndex(10));
	// // } else {
	// // window.getElement().<XElement> cast()
	// // .setZIndex(XDOM.getTopZIndex(10));
	// // }
	// //
	// // focus(window);
	// // }
	// // }
	// }

}
