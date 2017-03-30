package com.smartgxt.ui.client.desktop.ui.taskbar;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.ButtonCell.IconAlign;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.ToggleButton;
import com.sencha.gxt.widget.core.client.container.Container;
import com.sencha.gxt.widget.core.client.event.ActivateEvent;
import com.sencha.gxt.widget.core.client.event.ActivateEvent.ActivateHandler;
import com.sencha.gxt.widget.core.client.event.MaximizeEvent;
import com.sencha.gxt.widget.core.client.event.MaximizeEvent.MaximizeHandler;
import com.sencha.gxt.widget.core.client.event.MinimizeEvent;
import com.sencha.gxt.widget.core.client.event.MinimizeEvent.MinimizeHandler;
import com.sencha.gxt.widget.core.client.event.RegisterEvent;
import com.sencha.gxt.widget.core.client.event.RegisterEvent.RegisterHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.event.UnregisterEvent;
import com.sencha.gxt.widget.core.client.event.UnregisterEvent.UnregisterHandler;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;
import com.smartgxt.ui.client.windowmanager.WindowManager;
import com.smartgxt.ui.client.windows.IsWindow;

public class TaskBar extends ToolBar {

	private final static String csMinimizing = "sgxt.ToolBar.minimize";
	private final static String csToolButton = "sgxt.ToolBar.toolButton";
	private final static String csToolWindow = "sgxt.ToolBar.window";

	private final static String csMaximized = "sgxt.ToolBar.maximized";

	private List<Window> windows;
	private ToggleGroup group;

	private Container fitContainer;

	public TaskBar(Container fitContainer) {
		initGUI();
		setFitContainer(fitContainer);
	}

	public TaskBar(ToolBarAppearance appearance, Container fitContainer) {
		super(appearance);
		initGUI();
		setFitContainer(fitContainer);
	}

	protected void initGUI() {
		setMinButtonWidth(150);
		setHorizontalSpacing(2);
		// setSpacing(0);
		setBorders(true);
		windows = new ArrayList<Window>();
		group = new ToggleGroup();
		WindowManager.get().addRegisterHandler(registerHandler);
		WindowManager.get().addUnregisterHandler(unregisterHandler);
	}

	private UnregisterHandler<Widget> unregisterHandler = new UnregisterHandler<Widget>() {
		@Override
		public void onUnregister(UnregisterEvent<Widget> event) {
			final Window window = (Window) event.getItem();
			if ((window != null) && (window instanceof IsTaskBarWindow)) {
				if (window.getData(csMinimizing) == null) {
					HandlerRegistration minimizeHandlerRegistration = getData("sgxt.Toolbar.minimizeHandlerRegistration");
					HandlerRegistration activateHandlerRegistration = getData("sgxt.Toolbar.activateHandlerRegistration");
					HandlerRegistration maximizeHandlerRegistration = getData("sgxt.Toolbar.maximizeHandlerRegistration");

					if (minimizeHandlerRegistration != null)
						minimizeHandlerRegistration.removeHandler();
					if (activateHandlerRegistration != null)
						activateHandlerRegistration.removeHandler();
					if (maximizeHandlerRegistration != null)
						maximizeHandlerRegistration.removeHandler();

					close(window);
				}
			}

		}
	};

	private RegisterHandler<Widget> registerHandler = new RegisterHandler<Widget>() {

		@Override
		public void onRegister(RegisterEvent<Widget> event) {
			final Window window = (Window) event.getItem();
			if (window instanceof IsTaskBarWindow) {
				if (windows.indexOf(window) == -1) {
					windows.add(window);
					final ToggleButton button = new ToggleButton(
							window.getText());
					// button.getElement().setAttribute("text-align", "left");
					button.setIconAlign(IconAlign.LEFT);
					button.setContextMenu(createMenu(window));
					window.setData(csToolButton, button);
					button.setData(csToolWindow, window);
					button.addSelectHandler(selectHandler);

					add(button);
					group.add(button);
					doLayout();
					button.setValue(true, true);

					HandlerRegistration minimizeHandlerRegistration = window
							.addMinimizeHandler(minimizeHandler);
					HandlerRegistration activateHandlerRegistration = window
							.addActivateHandler(activateHandler);
					HandlerRegistration maximizeHandlerRegistration = window
							.addMaximizeHandler(maximizeHandler);

					window.setData("sgxt.Toolbar.minimizeHandlerRegistration",
							minimizeHandlerRegistration);
					window.setData("sgxt.Toolbar.activateHandlerRegistration",
							activateHandlerRegistration);
					window.setData("sgxt.Toolbar.maximizeHandlerRegistration",
							maximizeHandlerRegistration);
				}
			}
		}
	};

	private MaximizeHandler maximizeHandler = new MaximizeHandler() {

		@Override
		public void onMaximize(final MaximizeEvent event) {
			System.out.println("TaskBar.maximizeHandler()");
			Window window = (Window) event.getSource();
			maximize(window);
		}
	};

	private void maximize(Window window) {
		window.setPosition(getFitContainer().getAbsoluteLeft(),
				getFitContainer().getAbsoluteTop());
		window.setWidth(getFitContainer().getOffsetWidth());
		window.setHeight(getFitContainer().getOffsetHeight()+8);
	}

	private MinimizeHandler minimizeHandler = new MinimizeHandler() {
		@Override
		public void onMinimize(MinimizeEvent event) {
			Window window = (Window) event.getSource();
			minimize(window);
		}
	};

	private ActivateHandler<Window> activateHandler = new ActivateHandler<Window>() {

		@Override
		public void onActivate(ActivateEvent<Window> event) {
			getTaskButton((Window) event.getSource()).setValue(true, true);
		}
	};

	private SelectHandler selectHandler = new SelectHandler() {
		@Override
		public void onSelect(SelectEvent event) {
			ToggleButton button = (ToggleButton) event.getSource();
			Window window = button.getData(csToolWindow);

			if (window.isVisible()
					&& !WindowManager.get().getActive().equals(window)) {
				bringToFront(window);
			} else if (window.isVisible()
					&& WindowManager.get().getActive().equals(window)) {
				if (window.isMaximizable())
					minimize(window);
				else
					button.setValue(true, false);

			} else {
				restore(window);
			}
		}
	};

	private ToggleButton getTaskButton(Window window) {
		return window.getData(csToolButton);
	}

	protected void minimize(Window window) {
		window.setData(csMinimizing, true);
		if (window.isMaximized())
			window.setData(csMaximized, true);

		getTaskButton(window).setValue(false, true);
		window.hide();
	}

	protected void bringToFront(Window window) {
		getTaskButton(window).setValue(true, true);
		WindowManager.get().bringToFront(window);
	}

	protected void restore(Window window) {
		getTaskButton(window).setValue(true, true);
		window.setData(csMinimizing, null);
		window.show();

		if (window.getData(csMaximized) != null) {
			maximize(window);
			window.setData(csMaximized, null);
		}
	}

	protected void close(Window window) {
		ToggleButton button = getTaskButton(window);
		window.setData(csToolButton, null);
		button.setData(csToolWindow, null);

		button.removeFromParent();
		doLayout();

		windows.remove(window);
		button = null;
		window.hide();
		if (window instanceof IsWindow)
			((IsWindow) window).close();

	}

	protected Menu createMenu(final Window window) {
		Menu menu = new Menu();
		MenuItem closeMenuItem = new MenuItem("Закрыть");
		closeMenuItem.addSelectionHandler(new SelectionHandler<Item>() {

			@Override
			public void onSelection(SelectionEvent<Item> event) {
				// close(window);
			}
		});
		menu.add(closeMenuItem);
		return menu;

	}

	public Container getFitContainer() {
		return fitContainer;
	}

	public void setFitContainer(Container fitContainer) {
		this.fitContainer = fitContainer;
	}

}
