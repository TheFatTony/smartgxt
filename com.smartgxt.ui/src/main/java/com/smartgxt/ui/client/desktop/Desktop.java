package com.smartgxt.ui.client.desktop;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.smartgxt.ui.client.windows.Window;
import com.sencha.gxt.widget.core.client.WindowManager;
import com.sencha.gxt.widget.core.client.container.NorthSouthContainer;
import com.sencha.gxt.widget.core.client.container.PortalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.Viewport;
import com.sencha.gxt.widget.core.client.menu.MenuBar;
import com.smartgxt.core.client.prototypes.Prototypeble;
import com.smartgxt.ui.client.desktop.ui.taskbar.TaskBar;

public class Desktop extends Viewport {

	private  PortalLayoutContainer portal;
	private  MenuBar mainMenu;
	private  TaskBar taskBar;

	public Desktop() {
		initGUI();
	}

	public Desktop(ViewportAppearance appearance) {
		super(appearance);
		initGUI();
	}

	private void initGUI() {
		portal = new PortalLayoutContainer(2);
		mainMenu = new MenuBar();
		mainMenu.setHeight(30);

		taskBar = new TaskBar(portal);
		taskBar.setHeight(30);

		NorthSouthContainer comp = new NorthSouthContainer();

		comp.add(portal);
		comp.setNorthWidget(mainMenu);
		comp.setSouthWidget(taskBar);

		// cp.add(comp);
		add(comp);

		RootPanel.get().add(this);
	}
	

	public  PortalLayoutContainer getPortal() {
		return portal;
	}

	public  MenuBar getMainMenu() {
		return mainMenu;
	}

	public  TaskBar getTaskBar() {
		return taskBar;
	}
}
