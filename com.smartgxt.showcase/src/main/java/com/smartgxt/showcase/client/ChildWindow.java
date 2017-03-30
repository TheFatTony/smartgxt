package com.smartgxt.showcase.client;

import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.smartgxt.ui.client.windows.Window;

public class ChildWindow extends Window {
	private static final int COLUMN_FORM_WIDTH = 680;

	
	public ChildWindow() {
		super();
		setHeight(140);
		setWidth(100);
		FramedPanel panel = new FramedPanel();
		panel.setHeadingText("Form Example");
		panel.setWidth(COLUMN_FORM_WIDTH);
		VerticalLayoutContainer p = new VerticalLayoutContainer();
		panel.add(p);
		add(panel);

		TextField firstName = new TextField();
		firstName.setAllowBlank(false);
		firstName.setWidth(60);
		p.add(new FieldLabel(firstName, "First Name"));

		
		TextButton tb = new TextButton("new child");
		// manager = WindowManager.get();
		tb.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				ChildWindow chw = new ChildWindow();
				chw.setParent(ChildWindow.this);
				chw.show();
			}
		});
		addButton(tb);
	}

}
