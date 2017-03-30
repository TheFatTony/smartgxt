package com.smartgxt.client.ui.widgets.grids.columns;

/**
 * @author Anton Alexeyev
 * 
 */
public class ColumnConfig extends
		com.extjs.gxt.ui.client.widget.grid.ColumnConfig {

	public boolean isEditable;

	public String format;

	public ColumnConfig() {
	}

	public ColumnConfig(String id) {
		super(id, 120);
	}

	public ColumnConfig(String id, int width) {
		super(id, width);
	}

	public ColumnConfig(String id, String name, int width) {
		super(id, name, width);
	}

	public String getFormat() {
		return format;
	}

	public boolean isEditable() {
		return isEditable;
	}

	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}

}
