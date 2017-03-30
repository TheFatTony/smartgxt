package com.smartgxt.ui.client.windowmanager;

import java.util.Collections;
import java.util.Comparator;
import java.util.Stack;

import com.google.gwt.event.shared.HandlerRegistration;
import com.sencha.gxt.core.client.dom.XDOM;
import com.sencha.gxt.widget.core.client.ModalPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.event.BeforeHideEvent;
import com.sencha.gxt.widget.core.client.event.BeforeHideEvent.BeforeHideHandler;
import com.sencha.gxt.widget.core.client.event.BeforeShowEvent;
import com.sencha.gxt.widget.core.client.event.BeforeShowEvent.BeforeShowHandler;

public class WindowManagerHelper {

	private final static String csParent = "sgxt.WindowManagerHelper.parent";
	private final static String csChilds = "sgxt.WindowManagerHelper.childs";
	private final static String csOrderCiteria = "sgxt.WindowManagerHelper.orderCiteria";
	private final static String csHideCiteria = "sgxt.WindowManagerHelper.orderCiteria";

	private final static String csModal = "sgxt.WindowManagerHelper.modal";
	private final static String csShowModalHandler = "sgxt.WindowManagerHelper.showHandlerRegistration";
	private final static String csHideModalHandler = "sgxt.WindowManagerHelper.showHandlerRegistration";

	public static Window front;
	private static Window active;

	private static Comparator<Window> orderComparator = new Comparator<Window>() {
		public int compare(Window w1, Window w2) {
			Long d1 = (Long) w1.getData(csOrderCiteria);
			Long d2 = (Long) w2.getData(csOrderCiteria);
			return d1 == null || d1 < d2 ? -1 : 1;
		}
	};

	private static Comparator<Window> hideComparator = new Comparator<Window>() {
		public int compare(Window w1, Window w2) {
			Long d1 = (Long) w1.getData(csHideCiteria);
			Long d2 = (Long) w2.getData(csHideCiteria);
			return d1 == null || d1 < d2 ? -1 : 1;
		}
	};

	private static BeforeShowHandler onBeforeShowModalHandler = new BeforeShowHandler() {

		@Override
		public void onBeforeShow(BeforeShowEvent event) {
			Window window = (Window) event.getSource();
			Window parentWindow = getParent(window);
			if (parentWindow != null) {
				if (parentWindow.getData("sgxt.masked") == null) {
					parentWindow.getBody().mask(null);
					parentWindow.setData("sgxt.masked", true);
				}
			} else {
				ModalPanel modalPanel = window.getData("sgxt.modalPanel");
				if (modalPanel != null) {
					ModalPanel.push(modalPanel);
					modalPanel = null;
					window.setData("sgxt.modalPanel", null);
				}
			}

		}
	};

	private static BeforeHideHandler onBeforeHideModalHandler = new BeforeHideHandler() {

		@Override
		public void onBeforeHide(BeforeHideEvent event) {
			Window window = (Window) event.getSource();
			Window parentWindow = getParent(window);
			if (parentWindow != null) {
				if (parentWindow.getData("sgxt.masked") != null) {
					parentWindow.getBody().unmask();
					parentWindow.setData("sgxt.masked", null);
				}
			} else {
				ModalPanel modalPanel = ModalPanel.pop();
				modalPanel.setBlink(false);
				modalPanel.show(window);
				window.setData("sgxt.modalPanel", modalPanel);
			}
		}
	};

	public static void replaceWindowManager() {
		replaceWindowManager(WindowManager.get());
	}

	private static native void replaceWindowManager(
			com.sencha.gxt.widget.core.client.WindowManager manager)
	/*-{
	@com.sencha.gxt.widget.core.client.WindowManager::instance = manager;
	}-*/;

	public static void setWindowManager(Window window) {
		setWindowManager(window, WindowManager.get());
	}

	private static native void setWindowManager(Window window,
			com.sencha.gxt.widget.core.client.WindowManager windowManager)
	/*-{
	window.@com.sencha.gxt.widget.core.client.Window::manager =
	windowManager;
	}-*/;

	private static Stack<Window> getChilds(Window window) {
		Stack<Window> childs = window.getData(csChilds);
		return childs;
	}

	// public static native boolean isDragging(Window window) /*-{
	// return window.@com.extjs.gxt.ui.client.widget.Window::dragging;
	// }-*/;

	public static boolean hasChilds(Window window) {
		Stack<Window> childs = getChilds(window);
		if ((childs == null) || (childs.size() == 0))
			return false;
		return true;
	}

	public static boolean hasParent(Window window) {
		Window parent = getParent(window);
		return (parent != null);
	}

	public static void setParent(Window child, Window parent) {
		Stack<Window> childs = parent.getData(csChilds);
		if (childs == null) {
			childs = new Stack<Window>();
			parent.setData(csOrderCiteria, System.currentTimeMillis());
			parent.setData(csChilds, childs);
		}
		child.setData(csOrderCiteria, System.currentTimeMillis());
		child.setData(csParent, parent);
		getChilds(parent).add(child);
	}

	public static void attachChildToParent(Window child, Window parent) {
		setParent(child, parent);
	}

	// public static void attachChildToParent(Window child, Window parent,
	// boolean monitorMove) {
	// setParent(child, parent);
	// child.setData("sgxt.monitorMove", monitorMove);
	//
	// }

	// public static boolean isMonitorMove(Window window) {
	// if (window.getData("sgxt.monitorMove") != null)
	// return Boolean.parseBoolean(window.getData("sgxt.monitorMove")
	// .toString());
	// return false;
	// }

	public static void setModal(Window window, boolean modal) {
		window.setData(csModal, modal);
		if (modal) {
			HandlerRegistration showHandlerRegistration = window
					.addBeforeShowHandler(onBeforeShowModalHandler);
			window.setData(csShowModalHandler, showHandlerRegistration);

			HandlerRegistration hideHandlerRegistration = window
					.addBeforeHideHandler(onBeforeHideModalHandler);
			window.setData(csHideModalHandler, hideHandlerRegistration);
		} else {
			HandlerRegistration showHandlerRegistration = window
					.getData(csShowModalHandler);

			HandlerRegistration hideHandlerRegistration = window
					.getData(csHideModalHandler);

			if (showHandlerRegistration != null)
				showHandlerRegistration.removeHandler();

			if (hideHandlerRegistration != null)
				hideHandlerRegistration.removeHandler();
		}
	}

	public static boolean isModal(Window window) {
		if (window.getData(csModal) != null)
			return Boolean.parseBoolean(window.getData(csModal).toString());
		return false;
	}

	private static Window getParent(Window child) {
		return child.getData(csParent);

	}

	public static void hideAll(Window window) {
		Stack<Window> stack = new Stack<Window>();

		makeChildsStack(stack, window);

		Collections.sort(stack, hideComparator);
		Collections.reverse(stack);

		for (int i = stack.size() - 1; i >= 0; --i) {
			Window w = (Window) stack.get(i);
			w.hide();
		}

//		if (window.getData("sgxt.ToolBar.minimize") != null)
			window.setData("restore", stack);
	}

	public static void showAll(Window window) {
		Stack<Window> stack = window.getData("restore");
		if (stack != null) {
			for (int i = stack.size() - 1; i >= 0; --i) {
				Window w = (Window) stack.get(i);
				w.show();
			}
			window.setData("restore", null);
		}
	}

	public static void makehideStack(Stack<Window> stack, Window window) {
		Stack<Window> childs = getChilds(window);
		if (childs != null)
			for (int i = childs.size() - 1; i >= 0; --i) {
				Window w = (Window) childs.get(i);
				w.setData(csHideCiteria, System.currentTimeMillis());
				stack.add(w);
				makehideStack(stack, w);
			}
	}

	public static void makeChildsStack(Stack<Window> stack, Window window) {
		Stack<Window> childs = getChilds(window);
		if (childs != null)
			for (int i = childs.size() - 1; i >= 0; --i) {
				Window w = (Window) childs.get(i);
				stack.add(w);
				makeChildsStack(stack, w);
			}
	}

	public static void activateLast(Stack<Window> accessList) {
		for (int i = accessList.size() - 1; i >= 0; --i) {
			Window w = (Window) accessList.get(i);
			if (w.isVisible()) {
				setActiveWin(w);
				return;
			}
		}
		setActiveWin(null);
	}

	private static void setActiveWin(Window window) {
		if (window != front) {
			if (front != null) {
				front.setActive(false);
			}
			front = window;
			if (window != null) {

				window.setActive(true);
				// window.focus();
			}
		}
	}

	public static void orderWindowsBy(Window window, Stack<Window> accessList,
			boolean reverse) {
		orderWindows(accessList, reverse);
	}

	public static void fixCiteria(Window window) {
		window.setData(csOrderCiteria, System.currentTimeMillis());
		Window parent = getParent(window);
		if (parent != null)
			fixCiteria(parent);
	}

	public static void orderWindows(Stack<Window> accessList, boolean reverse) {
		if ((accessList != null) && (accessList.size() > 0)) {
			Collections.sort(accessList, orderComparator);
			if (reverse) {
				Collections.reverse(accessList);
			}
			for (int i = 0; i < accessList.size(); i++) {
				Window w = (Window) accessList.get(i);
				if (w.isRendered()) {
					w.setZIndex(XDOM.getTopZIndex(10));
					orderWindowsBy(w, getChilds(w), reverse);
				}
			}
			activateLast(accessList);
		}
	}

	public static Window getFront() {
		// System.out.println("WindowManagerHelper.getFront()");
		return front;
	}

	public static Window getActive() {
		// System.out.println("WindowManagerHelper.getActive()");
		return active;
	}
}
