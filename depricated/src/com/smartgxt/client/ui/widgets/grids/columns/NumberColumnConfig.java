package com.smartgxt.client.ui.widgets.grids.columns;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;

/**
 * @author Anton Alexeyev
 * 
 */
public class NumberColumnConfig extends ColumnConfig {

	public NumberColumnConfig() {
		typicalConfig();
	}

	public NumberColumnConfig(String id) {
		super(id);
		typicalConfig();
	}

	public NumberColumnConfig(String id, int width) {
		super(id, width);
		typicalConfig();
	}

	public NumberColumnConfig(String id, String name, int width) {
		super(id, name, width);
		typicalConfig();
	}

	private void typicalConfig() {
		this.format = "#,###,###.######################";
		setAlignment(HorizontalAlignment.RIGHT);
		setRenderer(new NumberCellRenderer() {

			@Override
			public String render(ModelData model, String property,
					ColumnData config, int rowIndex, int colIndex,
					Number value, String formattedValue) {
				return formattedValue;
			}
		});
		// setFormat(ColumnsFormatNumber.FLOAT);

	}

	public void setFormat(String format) {
		this.format = format;
		((NumberCellRenderer) getRenderer()).setFormat(format);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void setRenderer(GridCellRenderer renderer) {
		assert renderer instanceof NumberCellRenderer : "Mast be instance of NumberCellRenderer";
		super.setRenderer(renderer);
		setFormat(this.format);
	}

}
