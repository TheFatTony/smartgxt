package com.smartgxt.ui.client.windows;

import com.sencha.gxt.widget.core.client.Window.WindowAppearance;
import com.smartgxt.ui.client.windowmanager.WindowManagerHelper;

public class Window extends com.sencha.gxt.widget.core.client.Window implements
		IsWindow {

	private boolean isCentered = false;
	
	public Window() {
		super();
	}

	public Window(WindowAppearance appearance) {
		super(appearance);
	}

	@Override
	public void setParent(Window parent) {
		WindowManagerHelper.setParent(this, parent);

	}

	@Override
	public void setModal(boolean modal) {
		WindowManagerHelper.setModal(this, modal);
	}

	@Override
	public void close() {
		onClose();

	}
	
	@Override
	public void center() {
		super.center();
		isCentered = true;
	}

	protected void onClose() {
		// TODO Auto-generated method stub

	}

	public boolean isCentered() {
		return isCentered;
	}


}
