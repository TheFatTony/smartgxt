package com.smartgxt.client.ui.widgets.custom;

import com.extjs.gxt.ui.client.event.IconButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.fx.Resizable;
import com.extjs.gxt.ui.client.widget.button.ToolButton;

/**
 * @author Anton Alexeyev
 * 
 */
public class Portlet extends com.extjs.gxt.ui.client.widget.custom.Portlet {

	protected Resizable resizableComponent;
	protected ToolButton closableComponent;
	boolean closable = false;
	boolean resizable = false;

	public Portlet() {
		super();

	}

	private SelectionListener<IconButtonEvent> onCloseSelection = new SelectionListener<IconButtonEvent>() {

		@Override
		public void componentSelected(IconButtonEvent ce) {
			// removeFromParent();
			hide();
		}

	};

	public boolean isClosable() {
		return closable;
	}

	public void setClosable(boolean closable) {
		this.closable = closable;
		if (closable) {
			closableComponent = new ToolButton("x-tool-close", onCloseSelection);
			getHeader().addTool(closableComponent);
		} else {
			getHeader().removeTool(closableComponent);
			closableComponent = null;
		}
	}

	public boolean isResizable() {
		return resizable;
	}

	public void setResizable(boolean resizable) {
		this.resizable = resizable;
		if (resizable) {
			resizableComponent = new Resizable(this, "s");
		} else {
			resizableComponent.release();
			resizableComponent = null;
		}
	}

}
