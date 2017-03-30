package com.smartgxt.client.ui.widgets;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.google.gwt.user.client.ui.AbstractImagePrototype;

/**
 * @author Anton Alexeyev
 * 
 */
public class Button extends com.extjs.gxt.ui.client.widget.button.Button {

	public Button() {
		// TODO Auto-generated constructor stub
	}

	public Button(String text) {
		super(text);
		// TODO Auto-generated constructor stub
	}

	public Button(String text, AbstractImagePrototype icon) {
		super(text, icon);
		// TODO Auto-generated constructor stub
	}

	public Button(String text, SelectionListener<ButtonEvent> listener) {
		super(text, listener);
		// TODO Auto-generated constructor stub
	}

	public Button(String text, AbstractImagePrototype icon,
			SelectionListener<ButtonEvent> listener) {
		super(text, icon, listener);
		// TODO Auto-generated constructor stub
	}

}
