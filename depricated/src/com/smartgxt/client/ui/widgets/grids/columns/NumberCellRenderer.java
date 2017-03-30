package com.smartgxt.client.ui.widgets.grids.columns;

import java.math.BigDecimal;

import com.google.gwt.i18n.client.NumberFormat;

/**
 * @author Anton Alexeyev
 * 
 */
public abstract class NumberCellRenderer extends CellRenderer<Number> {

	private NumberFormat formater;

	@Override
	public void setRawValue(Object value) {
		setValue((BigDecimal) value);
	}

	public void setFormat(String format) {
		// this.format = format;
		formater = NumberFormat.getFormat(format);
	}

	@Override
	public String getFormattedValue() {
		return formater.format(getValue());
		// return getValue().toString();
	}

}
