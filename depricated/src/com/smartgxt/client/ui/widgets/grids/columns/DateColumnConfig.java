package com.smartgxt.client.ui.widgets.grids.columns;

import java.util.Date;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;

/**
 * @author Anton Alexeyev
 * 
 */
public class DateColumnConfig extends ColumnConfig {

	public DateColumnConfig() {
		typicalConfig();
	}

	public DateColumnConfig(String id) {
		super(id);
		typicalConfig();
	}

	public DateColumnConfig(String id, int width) {
		super(id, width);
		typicalConfig();
	}

	public DateColumnConfig(String id, String name, int width) {
		super(id, name, width);
		typicalConfig();
	}

	private void typicalConfig() {
		this.format = "dd.MM.yyyy HH:mm:ss";
		setAlignment(HorizontalAlignment.RIGHT);
		setRenderer(new DateCellRenderer() {

			@Override
			public String render(ModelData model, String property,
					ColumnData config, int rowIndex, int colIndex, Date value,
					String formattedValue) {
				return formattedValue;
			}

		});
		// setFormat(ColumnsFormatDate.DATETIME);

	}

	@Override
	public void setEditable(boolean isEditable) {
		super.setEditable(isEditable);
	}

	public void setFormat(String format) {
		this.format = format;
		((DateCellRenderer) getRenderer()).setFormat(format);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void setRenderer(GridCellRenderer renderer) {
		assert renderer instanceof DateCellRenderer : "Mast be instance of DateCellRenderer";
		super.setRenderer(renderer);
		setFormat(this.format);
	}
}
