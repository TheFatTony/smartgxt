package com.smartgxt.client.ui.widgets.panels;

import com.extjs.gxt.ui.client.widget.Layout;

/**
 * @author Anton Alexeyev
 * 
 */
public class ContentPanel extends com.extjs.gxt.ui.client.widget.ContentPanel {

	public ContentPanel() {
		setBodyBorder(false);
		setBorders(false);
		getHeader().setBorders(false);
	}

	public ContentPanel(Layout layout) {
		super(layout);
		setBodyBorder(false);
		setBorders(false);
		getHeader().setBorders(false);
	}

}
