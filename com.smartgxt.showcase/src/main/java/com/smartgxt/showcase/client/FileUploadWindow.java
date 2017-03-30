package com.smartgxt.showcase.client;

import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.smartgxt.core.client.prototypes.Prototypeble;
import com.smartgxt.incubator.client.FileUploadField;
import com.smartgxt.ui.client.desktop.ui.taskbar.IsTaskBarWindow;

@Prototypeble(deffered=true)
public class FileUploadWindow extends Window implements IsTaskBarWindow {
	private static final int COLUMN_FORM_WIDTH = 680;

	public FileUploadWindow() {
		setHeadingText("FileUpload Window");
		setMaximizable(true);
		setMinimizable(true);
		FramedPanel panel = new FramedPanel();
		panel.setHeadingText("Form Example");
		panel.setWidth(COLUMN_FORM_WIDTH);
		VerticalLayoutContainer p = new VerticalLayoutContainer();
		panel.add(p);
		add(panel);

		FileUploadField firstName = new FileUploadField();
		firstName.setAllowBlank(false);
		firstName.setWidth(160);
		p.add(new FieldLabel(firstName, "First Name"));

	}

}
