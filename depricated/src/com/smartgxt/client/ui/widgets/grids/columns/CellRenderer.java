package com.smartgxt.client.ui.widgets.grids.columns;

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.TextMetrics;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;

/**
 * @author Anton Alexeyev
 * 
 */
public abstract class CellRenderer<M> implements GridCellRenderer<ModelData> {

	private ModelData model;
	private String property;
	private M value;

	@Override
	public final Object render(ModelData model, String property,
			ColumnData config, int rowIndex, int colIndex,
			ListStore<ModelData> store, Grid<ModelData> grid) {
		this.model = model;
		this.property = property;
		setRawValue(model.get(property));
		if (TextMetrics.get().getWidth(getFormattedValue()) > grid
				.getColumnModel().getColumnWidth(colIndex))
			return getTemplate(render(model, property, config, rowIndex,
					colIndex, getValue(), getFormattedValue()));
		else
			return render(model, property, config, rowIndex, colIndex,
					getValue(), getFormattedValue());
	}

	public abstract String render(ModelData model, String property,
			ColumnData config, int rowIndex, int colIndex, M value,
			String formattedValue);

	public M getValue() {
		return value;
	}

	public abstract void setRawValue(Object value);

	public void setValue(M value) {
		this.value = value;
	}

	public abstract void setFormat(String format);

	public abstract String getFormattedValue();

	public native String getTemplate(String qtip) /*-{
		var html = [ '<span qtip=\'', , qtip, '\'>', qtip, '</span>' ];
		return html.join("");
	}-*/;

}
