package com.smartgxt.ui.client.windows;

import com.smartgxt.ui.client.windowmanager.WindowManagerHelper;

public class Dialog extends com.sencha.gxt.widget.core.client.Dialog implements
		IsWindow {

	public Dialog() {
		// TODO Auto-generated constructor stub
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

	protected void onClose() {
		// TODO Auto-generated method stub
		
	}
	

}
