package com.smartgxt.client.ui.widgets.windows;

/**
 * @author Anton Alexeyev
 * 
 */
public class Dialog extends com.extjs.gxt.ui.client.widget.Dialog {

	public Dialog() {
		setButtons("");
		new WindowExtendStatesTweak(this);
	}

	protected void setParentWindow(com.extjs.gxt.ui.client.widget.Window parent) {

	}

}
