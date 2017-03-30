package com.smartgxt.client.ui.widgets.desktop.dashboard;

import com.extjs.gxt.ui.client.event.BoxComponentEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.smartgxt.client.ui.widgets.custom.Portlet;

/**
 * @author Anton Alexeyev
 * 
 */
public class InformationWidget extends Portlet {

	public InformationWidget() {
		setCollapsible(true);
		setAnimCollapse(true);
		setClosable(true);
		setResizable(true);
		extendState();
	}

	private void extendState() {
		addListener(Events.Hide, new Listener<ComponentEvent>() {
			@Override
			public void handleEvent(ComponentEvent be) {
				getState().put("hide", true);
				saveState();
			}
		});

		addListener(Events.Show, new Listener<ComponentEvent>() {
			@Override
			public void handleEvent(ComponentEvent be) {
				getState().put("hide", false);
				saveState();
			}
		});

		addListener(Events.StateRestore, new Listener<ComponentEvent>() {
			@Override
			public void handleEvent(ComponentEvent be) {
				final Object height = getState().get("height");
				if (height != null) {
					// Scheduler.get().scheduleDeferred(new ScheduledCommand() {
					// @Override
					// public void execute() {
					setHeight((Integer) height);
					// }
					// });
				}

				Object collapsed = getState().get("collapsed");
				if (collapsed != null) {
					boolean b = (Boolean) collapsed;
					if (b)
						collapse();
					else
						expand();
				}

				Object hide = getState().get("hide");
				if (hide != null) {
					boolean b1 = (Boolean) hide;
					if (b1)
						hide();
					else
						show();
				}
			}
		});
		addListener(Events.Resize, new Listener<BoxComponentEvent>() {
			@Override
			public void handleEvent(BoxComponentEvent be) {
				if (be.getHeight() > 0) {
					getState().put("height", be.getHeight());
					saveState();
				}
			}
		});

		addListener(Events.Collapse, new Listener<ComponentEvent>() {

			@Override
			public void handleEvent(ComponentEvent be) {
				getState().put("collapsed", true);
				saveState();
			}
		});

		addListener(Events.Expand, new Listener<ComponentEvent>() {

			@Override
			public void handleEvent(ComponentEvent be) {
				getState().put("collapsed", false);
				saveState();
			}
		});
	}

}
