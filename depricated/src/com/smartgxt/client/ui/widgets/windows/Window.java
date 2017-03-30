package com.smartgxt.client.ui.widgets.windows;

/**
 * @author Anton Alexeyev
 * 
 */
public class Window extends com.extjs.gxt.ui.client.widget.Window {

	public Window() {
		new WindowExtendStatesTweak(this);
	}

	protected void setParentWindow(com.extjs.gxt.ui.client.widget.Window parent) {
	}

}
