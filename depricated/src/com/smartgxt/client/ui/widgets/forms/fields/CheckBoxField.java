package com.smartgxt.client.ui.widgets.forms.fields;

import com.extjs.gxt.ui.client.widget.form.CheckBox;

/**
 * @author Anton Alexeyev
 * 
 */
public class CheckBoxField extends LabeledField<Boolean> {

	public CheckBoxField() {
		super(new CheckBox());
	}

	@Override
	public CheckBox get() {
		return (CheckBox) getField();
	}

	public String getBoxLabel() {
		return get().getBoxLabel();
	}

	@Override
	public String getRawValue() {
		return get().getRawValue();
	}

	/**
	 * 
	 * Returns the value property of the input element
	 */
	public String getValueAttribute() {
		return get().getValueAttribute();
	}

	@Override
	public void markInvalid(String msg) {
		get().markInvalid(msg);
	}

	/**
	 * The text that appears beside the checkbox (defaults to null).
	 * 
	 * @param boxLabel
	 *            the box label
	 */
	public void setBoxLabel(String boxLabel) {
		get().setBoxLabel(boxLabel);
	}

	@Override
	public void setRawValue(String value) {
		get().setRawValue(value);
	}

	@Override
	public void setValue(Boolean value) {
		get().setValue(value);
	}

}
