package com.smartgxt.client.managers;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.event.WindowEvent;
import com.extjs.gxt.ui.client.event.WindowListener;
import com.extjs.gxt.ui.client.widget.Window;
import com.google.gwt.editor.client.HasEditorErrors;
import com.google.gwt.user.client.ui.HasValue;

/**
 * Класс Менеджер окон автоматически определяет принадлежность окон их родителем
 * и управляет позиционированием. Временно поддерживает только модальные окна.
 * Для работы окна с данным клаcсом достаточно пометить его интерфейсом
 * IsWindow. Делать setModel не нужно
 * 
 * @author Anton Alexeyev
 */

public class WindowsManagerHelper {

	private final static String StyleInactive = "ux-window-inactive";
	private final static String Registred = "sgxt.registred";
	private final static String Minimized = "sgxt.minimized";
	private final static String Parent = "sgxt.parent";
	private final static String Childs = "sgxt.childs";

	protected static List<Window> windows;
	protected static WindowListener listener;
	protected static Window activeWindow;

	static {
		windows = new ArrayList<Window>();
		initListeners();
	}

	public WindowsManagerHelper() {

	}

	protected static void initListeners() {
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

	protected static void addWindow(final Window window) {
		if (window.getData(Registred) == null)
			if (windows.add(window)) {
				window.setData(Registred, true);
				window.addWindowListener(listener);
			}
	}

	private static void markActive(Window window) {
		if (activeWindow != null && activeWindow != window) {
			markInactive(activeWindow);
		}
		window.getHeader().removeStyleName(StyleInactive);
		activeWindow = window;
		window.setData(Minimized, null);
	}

	private static void markInactive(Window window) {
		System.out.println("markInactive");
		if (window == activeWindow) {
			window.getHeader().addStyleName(StyleInactive);
			activeWindow = null;
		}
	}

	protected static void onHide(Window window) {
		System.out.println("onHide");
		if (window.getData(Minimized) != null) {
			markInactive(window);
			return;
		}
		if (activeWindow == window) {
			activeWindow = null;
		}
		window = null;
	}

	protected static void minimizeWindow(Window window) {
		System.out.println("minimizeWindow");
		window.collapse();
		window.setData(Minimized, true);
		// window.hide();
		// TODO fire event about Windows going to minimize
	}

	protected static void onShow(final Window window) {
		System.out.println("onShow");
		window.setData(Minimized, null);
	}

	public static void showChild(Window window) {
		addWindow(window);

	}

}
