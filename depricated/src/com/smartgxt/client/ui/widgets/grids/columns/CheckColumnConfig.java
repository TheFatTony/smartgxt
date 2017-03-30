package com.smartgxt.client.ui.widgets.grids.columns;

import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;

/**
 * @author Anton Alexeyev
 * 
 */
public class CheckColumnConfig extends
		com.extjs.gxt.ui.client.widget.grid.CheckColumnConfig {

	private boolean isEditable;

	public CheckColumnConfig() {
		super();
	}

	public CheckColumnConfig(String id, int width) {
		super(id, id, width);
	}

	public CheckColumnConfig(String id, String name, int width) {
		super(id, name, width);
	}

	public boolean isEditable() {
		return isEditable;
	}

	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
		if (isEditable) {
			CheckBox text = new CheckBox();
			setEditor(new CellEditor(text));
		} else
			setEditor(null);
	}

}
