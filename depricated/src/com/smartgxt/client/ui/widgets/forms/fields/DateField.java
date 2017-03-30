package com.smartgxt.client.ui.widgets.forms.fields;

import java.util.Date;

/**
 * @author Anton Alexeyev
 * 
 */
public class DateField extends LabeledTextField<Date> {

	public DateField() {
		super(new com.extjs.gxt.ui.client.widget.form.DateField());
	}

	@Override
	public com.extjs.gxt.ui.client.widget.form.DateField get() {
		return (com.extjs.gxt.ui.client.widget.form.DateField) getField();
	}

	/**
	 * Returns the field's max value.
	 * 
	 * @return the max value
	 */
	public Date getMaxValue() {
		return get().getMaxValue();
	}

	/**
	 * Returns the field's min value.
	 * 
	 * @return the min value
	 */
	public Date getMinValue() {
		return get().getMinValue();
	}

	/**
	 * Returns true if formatting is enabled.
	 * 
	 * @return the format value state
	 */
	public boolean isFormatValue() {
		return get().isFormatValue();
	}

	/**
	 * True to format the user entered value using the field's property editor
	 * after passing validation (defaults to false). Format value should not be
	 * enabled when auto validating.
	 * 
	 * @param formatValue
	 *            true to format the user value
	 */
	public void setFormatValue(boolean formatValue) {
		get().setFormatValue(formatValue);
	}

	/**
	 * Sets the field's max value.
	 * 
	 * @param maxValue
	 *            the max value
	 */
	public void setMaxValue(Date maxValue) {
		get().setMaxValue(maxValue);
	}

	/**
	 * The maximum date allowed.
	 * 
	 * @param minValue
	 *            the max value
	 */
	public void setMinValue(Date minValue) {
		get().setMinValue(minValue);
	}

}
