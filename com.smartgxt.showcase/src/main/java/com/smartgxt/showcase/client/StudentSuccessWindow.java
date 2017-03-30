package com.smartgxt.showcase.client;

import com.google.gwt.user.client.ui.Frame;
import com.sencha.gxt.widget.core.client.Window;
import com.smartgxt.core.client.prototypes.Prototypeble;
import com.smartgxt.ui.client.desktop.ui.taskbar.IsTaskBarWindow;

@Prototypeble(deffered=true)
public class StudentSuccessWindow extends Window implements IsTaskBarWindow {
	private Frame frame;
	public StudentSuccessWindow() {
		setHeadingText("Centre for Student Success");
		setMaximizable(true);
		setMinimizable(true);
		frame = new Frame("http://samplepdf.com/sample.pdf");
		add(frame);
	}

	public StudentSuccessWindow(WindowAppearance appearance) {
		super(appearance);
		// TODO Auto-generated constructor stub
	}

}
