package com.smartgxt.client.ui.widgets.desktop;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.WindowEvent;
import com.extjs.gxt.ui.client.event.WindowListener;
import com.extjs.gxt.ui.client.event.WindowManagerEvent;
import com.extjs.gxt.ui.client.util.Format;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.WindowManager;
import com.extjs.gxt.ui.client.widget.button.ToggleButton;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.smartgxt.client.managers.IsMainWindow;
import com.smartgxt.client.ui.widgets.toolbars.ToolBar;

/**
 * @author Anton Alexeyev
 * 
 */
public class TaskBar extends ToolBar {

	protected List<Window> windows;
	protected List<ToggleButton> buttons;
	protected WindowListener listener;
	protected Window activeWindow;
	private LayoutContainer workArea;

	public TaskBar() {
		super();
		setSpacing(3);
		setHeight(27);
		setMinButtonWidth(170);
		setLayout(new TaskBarLayout());

		// setBorders(true);
		// addStyleName("ux-apppanel");

		initListeners();

		windows = new ArrayList<Window>();
		buttons = new ArrayList<ToggleButton>();

		WindowManager.get().addListener(Events.Register, onRegister);
	}

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

	private Listener<WindowManagerEvent> onRegister = new Listener<WindowManagerEvent>() {
		@Override
		public void handleEvent(WindowManagerEvent be) {
			final Window wnd = be.getWindow();
			if (wnd.getData("sgxt.registred") == null) {
				if (wnd instanceof IsMainWindow) {
					addWindow(wnd);
					wnd.setData("sgxt.registred", true);
				}
				if (getWorkArea() != null) {
					wnd.setMonitorWindowResize(true);
					wnd.setContainer(getWorkArea().getElement());
					wnd.getDraggable().setContainer(getWorkArea());
				}
			}
			// wnd.getResizable().addListener(Events.ResizeEnd,
			// new Listener<ResizeEvent>() {
			//
			// @Override
			// public void handleEvent(ResizeEvent be) {
			// wnd.getX
			//
			// }
			// });
			wnd.addListener(Events.Resize, new Listener<WindowEvent>() {

				@Override
				public void handleEvent(WindowEvent be) {
				}
			});
		}
	};

	private void onShow(final Window window) {
		ToggleButton btn = window.getData("sgxt.taskButton");
		window.setData("sgxt.minimize", null);
		if (btn != null && getButtons().contains(btn)) {
			return;
		}
		addTaskButton(window);
	}

	private void markActive(Window window) {
		if (activeWindow != null && activeWindow != window) {
			markInactive(activeWindow);
		}
		ToggleButton btn = ((ToggleButton) window.getData("sgxt.taskButton"));
		if (!btn.isPressed())
			btn.toggle();
		window.getHeader().removeStyleName("ux-window-inactive");
		activeWindow = window;
		window.setData("sgxt.minimize", null);
	}

	private void markInactive(Window window) {
		if (window == activeWindow) {
			window.getHeader().addStyleName("ux-window-inactive");
			activeWindow = null;
		}
		ToggleButton btn = ((ToggleButton) window.getData("sgxt.taskButton"));
		if (btn.isPressed())
			btn.toggle();
	}

	public void minimizeWindow(Window window) {
		window.setData("sgxt.minimize", true);
		window.hide();
	}

	protected void onHide(Window window) {
		if (window.getData("sgxt.minimize") != null) {
			markInactive(window);
			return;
		}
		if (activeWindow == window) {
			activeWindow = null;
		}
		removeTaskButton((ToggleButton) window.getData("sgxt.taskButton"));
		window = null;
	}

	public void addWindow(final Window window) {
		if (windows.add(window)) {
			window.addWindowListener(listener);
		}
	}

	public void removeTaskButton(ToggleButton button) {
		Window wnd = button.getData("sgxt.window");
		wnd.setData("sgxt.taskButton", null);
		getButtons().remove(button);
		remove(button);
		windows.remove(wnd);
	}

	public ToggleButton addTaskButton(Window win) {
		ToggleButton button = new ToggleButton();
		button.addStyleName("ux-button-alignleft");
		button.setData("sgxt.window", win);
		win.setData("sgxt.taskButton", button);
		button.setWidth(getMinButtonWidth());
		button.setToggleGroup("taskBarToggleGroup");
		button.setText(Format.ellipse(win.getHeading(), 26));
		button.setContextMenu(createMenu(win));
		button.addSelectionListener(onButtonClick);

		getButtons().add(button);
		add(button);
		// layout();
		return button;
	}

	public Menu createMenu(final Window win) {
		Menu menu = new Menu();
		MenuItem menuItem = new MenuItem();
		menuItem.setText("Закрыть");
		menuItem.addSelectionListener(new SelectionListener<MenuEvent>() {

			@Override
			public void componentSelected(MenuEvent ce) {
				win.hide();
			}
		});
		menu.add(menuItem);
		return menu;
	}

	private SelectionListener<ButtonEvent> onButtonClick = new SelectionListener<ButtonEvent>() {

		@Override
		public void componentSelected(ButtonEvent ce) {
			Window win = (Window) ce.getButton().getData("sgxt.window");
			if (win.getData("sgxt.minimized") != null || !win.isVisible()) {
				win.show();
			} else if (win == activeWindow) {
				win.minimize();
			} else {
				win.fireEvent(Events.Activate);
				win.toFront();
			}
		}
	};

	private List<ToggleButton> getButtons() {
		return buttons;
	}

	public void setWorkArea(LayoutContainer workArea) {
		this.workArea = workArea;
	}

	public LayoutContainer getWorkArea() {
		return workArea;
	}

}
