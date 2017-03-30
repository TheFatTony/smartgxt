package com.smartgxt.client.ui.widgets.grids.columns;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.treegrid.TreeGridCellRenderer;

/**
 * @author Anton Alexeyev
 * 
 */
public class TreeColumnConfig extends ColumnConfig {

	public TreeColumnConfig() {
		createRenderer();
	}

	public TreeColumnConfig(String id) {
		super(id);
		createRenderer();
	}

	public TreeColumnConfig(String id, int width) {
		super(id, width);
		createRenderer();
	}

	public TreeColumnConfig(String id, String name, int width) {
		super(id, name, width);
		createRenderer();
	}

	private void createRenderer() {
		setAlignment(HorizontalAlignment.LEFT);
		setRenderer(new TreeGridCellRenderer<ModelData>());
	}

	@Override
	public void setEditable(boolean isEditable) {
		super.setEditable(isEditable);
		if (isEditable) {
			TextField<String> text = new TextField<String>();
			setEditor(new CellEditor(text));
		} else
			setEditor(null);
	}

}
