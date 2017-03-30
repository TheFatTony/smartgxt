package com.smartgxt.client.ui.widgets.grids.columns;

/**
 * @author Anton Alexeyev
 * 
 */
public abstract class TextCellRenderer extends CellRenderer<String> {

	@Override
	public void setRawValue(Object value) {
		if (value != null)
			setValue(escapeHtml(value.toString()));
		else
			setValue("");
	}

	private static String escapeHtml(String maybeHtml) {
		return maybeHtml.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;").replaceAll("\"", "&quot;");
	}

	@Override
	public void setFormat(String format) {
	}

	@Override
	public String getFormattedValue() {
		return getValue();
	}

}
