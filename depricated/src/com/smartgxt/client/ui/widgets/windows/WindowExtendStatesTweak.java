package com.smartgxt.client.ui.widgets.windows;

import java.util.Map;

import com.extjs.gxt.ui.client.event.BoxComponentEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.WindowEvent;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgxt.client.util.UIHelper;

/**
 * @author Anton Alexeyev
 * 
 */
public class WindowExtendStatesTweak {

	private com.extjs.gxt.ui.client.widget.Window window;

	public WindowExtendStatesTweak(com.extjs.gxt.ui.client.widget.Window window) {
		setWindow(window);

	}

	public void setWindow(final com.extjs.gxt.ui.client.widget.Window window) {
		this.window = window;
		window.setOnEsc(false);
		window.addListener(Events.StateRestore, new Listener<ComponentEvent>() {
			@Override
			public void handleEvent(ComponentEvent be) {
				Map<String, Object> state = window.getState();
				Boolean b = (Boolean) state.get("maximized");
				if ((b == null) || (!b)) {
					window.setSize((Integer) state.get("width"),
							(Integer) state.get("height"));
					if ((state.get("left") != null)
							&& (state.get("top") != null))
						window.setPosition((Integer) state.get("left"),
								(Integer) state.get("top"));
				} else
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {

						@Override
						public void execute() {
							window.maximize();
						}
					});
			}
		});
		window.addListener(Events.Resize, new Listener<BoxComponentEvent>() {
			@Override
			public void handleEvent(BoxComponentEvent be) {
				window.getState().put("width", be.getWidth());
				window.getState().put("height", be.getHeight());
				window.saveState();
			}
		});

		window.addListener(Events.Maximize, new Listener<WindowEvent>() {
			@Override
			public void handleEvent(WindowEvent we) {
				window.getState().put("maximized", true);
				window.saveState();
			}
		});

		window.addListener(Events.Restore, new Listener<WindowEvent>() {
			@Override
			public void handleEvent(WindowEvent we) {
				window.getState().put("maximized", false);
				window.saveState();
			}
		});

		window.addListener(Events.Move, new Listener<BoxComponentEvent>() {
			@Override
			public void handleEvent(BoxComponentEvent be) {
				// TODO possible bug with Resized browser window
				window.getState().put("left", be.getX());
				window.getState().put("top", be.getY());
				window.saveState();
			}
		});

		window.getHeader().addListener(Events.OnDoubleClick,
				new Listener<ComponentEvent>() {
					public void handleEvent(ComponentEvent be) {
						com.extjs.gxt.ui.client.widget.Window window = UIHelper
								.getParentWindow(be.getComponent());
						if (window.isMaximizable())
							if (window.isMaximized()) {
								window.restore();
							} else {
								window.maximize();
							}
					}
				});
	}

	public com.extjs.gxt.ui.client.widget.Window getWindow() {
		return window;
	}

}
