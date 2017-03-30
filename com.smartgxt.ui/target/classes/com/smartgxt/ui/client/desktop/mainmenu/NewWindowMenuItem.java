package com.smartgxt.ui.client.desktop.mainmenu;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.resources.client.ImageResource;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.MenuItem;
import com.smartgxt.core.client.SmartGXT;
import com.smartgxt.core.client.prototypes.AsyncCommand;

public class NewWindowMenuItem extends MenuItem {

	private Class<? extends Window> windowClass;
	private boolean deffered = false;
	private boolean showCentered = false;

	public NewWindowMenuItem() {
	}

	public NewWindowMenuItem(Class<? extends Window> windowClass) {
		setWindowClass(windowClass);
	}

	public NewWindowMenuItem(MenuItemAppearance appearance) {
		super(appearance);
		intiComponent();
	}

	public NewWindowMenuItem(String text) {
		super(text);
		intiComponent();
	}
	
	public NewWindowMenuItem(String text, Class<? extends Window> windowClass) {
		super(text);
		intiComponent();
		setWindowClass(windowClass);
	}

	public NewWindowMenuItem(String text, Class<? extends Window> windowClass, boolean deffered) {
		super(text);
		intiComponent();
		setWindowClass(windowClass);
		setDeffered(deffered);
	}


	public NewWindowMenuItem(MenuItemAppearance menuItemAppearance,
			ItemAppearance itemAppearance) {
		super(menuItemAppearance, itemAppearance);
		intiComponent();
	}

	public NewWindowMenuItem(String text, ImageResource icon) {
		super(text, icon);
		intiComponent();
	}

	public NewWindowMenuItem(String text, SelectionHandler<MenuItem> handler) {
		super(text, handler);
		intiComponent();
	}

	private void intiComponent() {
		addSelectionHandler(new SelectionHandler<Item>() {

			@Override
			public void onSelection(SelectionEvent<Item> event) {
				if (isDeffered()) {
					SmartGXT.create(getWindowClass(), new AsyncCommand() {

						@Override
						public void execute() {
							((Window) getObject()).show();
							if (isShowCentered())
								((Window) getObject()).center();
						}
					});

				} else {
					Window window = SmartGXT.create(getWindowClass());
					window.show();
					if (isShowCentered())
						window.center();
				}
			}
		});
	}

	public Class<? extends Window> getWindowClass() {
		return windowClass;
	}

	public void setWindowClass(Class<? extends Window> windowClass) {
		this.windowClass = windowClass;
	}

	public boolean isDeffered() {
		return deffered;
	}

	public void setDeffered(boolean deffered) {
		this.deffered = deffered;
	}


	public boolean isShowCentered() {
		return showCentered;
	}

	public void setShowCentered(boolean showCentered) {
		this.showCentered = showCentered;
	}

}
