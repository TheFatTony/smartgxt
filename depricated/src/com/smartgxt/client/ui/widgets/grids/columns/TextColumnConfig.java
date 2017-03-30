package com.smartgxt.client.ui.widgets.grids.columns;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;

/**
 * @author Anton Alexeyev
 * 
 */
public class TextColumnConfig extends ColumnConfig {

	public TextColumnConfig() {
		typicalConfig();
	}

	public TextColumnConfig(String id) {
		super(id);
		typicalConfig();
	}

	public TextColumnConfig(String id, int width) {
		super(id, width);
		typicalConfig();
	}

	public TextColumnConfig(String id, String name, int width) {
		super(id, name, width);
		typicalConfig();
	}

	private void typicalConfig() {
		setAlignment(HorizontalAlignment.LEFT);
		setRenderer(new TextCellRenderer() {
			@Override
			public String render(ModelData model, String property,
					ColumnData config, int rowIndex, int colIndex,
					String value, String formattedValue) {
				return formattedValue;
			}
		});
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void setRenderer(GridCellRenderer renderer) {
		assert renderer instanceof TextCellRenderer : "Mast be instance of TextCellRenderer";
		super.setRenderer(renderer);
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
