package com.smartgxt.client.ui.widgets.grids.columns;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

/**
 * @author Anton Alexeyev
 * 
 */
public abstract class DateCellRenderer extends CellRenderer<Date> {

	private DateTimeFormat formater;

	// value = Double.parseDouble(str);

	@Override
	public void setRawValue(Object value) {
		if (value != null)
			setValue((Date) value);
		else
			setValue(null);
	}

	@Override
	public String getFormattedValue() {
		if (getValue() != null)
			return formater.format(getValue());
		else
			return null;
	}

	public void setFormat(String format) {
		formater = DateTimeFormat.getFormat(format);
	}

}
