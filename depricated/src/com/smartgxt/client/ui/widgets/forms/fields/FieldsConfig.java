package com.smartgxt.client.ui.widgets.forms.fields;

import java.util.List;

/**
 * @author Anton Alexeyev
 * 
 */
public class FieldsConfig {

	private int labelWidth = 120;
	private String labelSeparator = ":";
	private List<LabeledField<?>> felds;

	public FieldsConfig() {
		// TODO Auto-generated constructor stub
	}

	public void setLabelWidth(int labelWidth) {
		this.labelWidth = labelWidth;
		for (LabeledField<?> f : getFelds())
			f.setLabelWidth(labelWidth);
	}

	public int getLabelWidth() {
		return labelWidth;
	}

	public void setLabelSeparator(String labelSeparator) {
		this.labelSeparator = labelSeparator;
		for (LabeledField<?> f : getFelds())
			f.setLabelSeparator(labelSeparator);
	}

	public String getLabelSeparator() {
		return labelSeparator;
	}

	public List<LabeledField<?>> getFelds() {
		return felds;
	}

}
