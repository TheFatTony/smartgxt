package com.smartgxt.client.link.window;

import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.TabPanelEvent;
import com.extjs.gxt.ui.client.event.WindowEvent;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.Window;

public class ConstrainigManager {

	
	public ConstrainigManager() {
		// TODO Auto-generated constructor stub
	}

	public void constrain(Component parent, Component child, boolean modal) {
		if (parent instanceof Window) {
			Window parentWindow = (Window) parent;
			parentWindow.addListener(Events.Activate,
					new Listener<WindowEvent>() {

						@Override
						public void handleEvent(WindowEvent be) {
							ConstrainingHelper.activate(be.getComponent());
						}
					});
			parentWindow.addListener(Events.Minimize,
					new Listener<WindowEvent>() {
						@Override
						public void handleEvent(WindowEvent be) {
							ConstrainingHelper.hide(be.getComponent());
						}
					});
			parentWindow.addListener(Events.Hide,
					new Listener<WindowEvent>() {
						@Override
						public void handleEvent(WindowEvent be) {
							ConstrainingHelper.close(be.getComponent());
						}
					});
		} else if (parent instanceof TabItem) {
			TabItem parentTabItem= (TabItem) parent;
			parentTabItem.addListener(Events.Show, new Listener<ComponentEvent>() {

				@Override
				public void handleEvent(ComponentEvent be) {
					ConstrainingHelper.activate(be.getComponent());
				}
			});
			
			parentTabItem.addListener(Events.Hide, new Listener<ComponentEvent>() {

				@Override
				public void handleEvent(ComponentEvent be) {
					ConstrainingHelper.hide(be.getComponent());
				}
			});
			
			parentTabItem.addListener(Events.Close, new Listener<TabPanelEvent>() {

				@Override
				public void handleEvent(TabPanelEvent be) {
					ConstrainingHelper.close(be.getComponent());
				}
			});
		}

	}

}
