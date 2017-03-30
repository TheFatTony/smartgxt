package com.smartgxt.client.ui.widgets.forms.fields;

import com.extjs.gxt.ui.client.widget.form.NumberPropertyEditor;
import com.extjs.gxt.ui.client.widget.form.SpinnerField.SpinnerFieldMessages;
import com.google.gwt.i18n.client.NumberFormat;

/**
 * @author Anton Alexeyev
 * 
 */
public class NumberField extends LabeledTextField<Number> {

	public NumberField() {
		super(new com.extjs.gxt.ui.client.widget.form.NumberField());
	}

	@Override
	public com.extjs.gxt.ui.client.widget.form.NumberField get() {
		return (com.extjs.gxt.ui.client.widget.form.NumberField) getField();
	}

	/**
	 * Returns true of decimal values are allowed.
	 * 
	 * @return the allow decimal state
	 */
	public boolean getAllowDecimals() {
		return get().getAllowDecimals();
	}

	/**
	 * Returns true if negative values are allowed.
	 * 
	 * @return the allow negative value state
	 */
	public boolean getAllowNegative() {
		return get().getAllowNegative();
	}

	/**
	 * Returns the base characters.
	 * 
	 * @return the base characters
	 */
	public String getBaseChars() {
		return get().getBaseChars();
	}

	/**
	 * Returns the field's number format.
	 * 
	 * @return the number format
	 */
	public NumberFormat getFormat() {
		return get().getPropertyEditor().getFormat();
	}

	/**
	 * Sets the increment value.
	 * 
	 * @return the increment
	 */
	public Number getIncrement() {
		return get().getIncrement();
	}

	/**
	 * Returns the fields max value.
	 * 
	 * @return the max value
	 */
	public Number getMaxValue() {
		return get().getMaxValue();
	}

	@Override
	public SpinnerFieldMessages getMessages() {
		return get().getMessages();
	}

	/**
	 * Returns the field's minimum value.
	 * 
	 * @return the min value
	 */
	public Number getMinValue() {
		return get().getMinValue();
	}

	@Override
	public NumberPropertyEditor getPropertyEditor() {
		return (NumberPropertyEditor) propertyEditor;
	}

	/**
	 * Returns the number property editor number type.
	 * 
	 * @see NumberPropertyEditor#setType(Class)
	 * @return the number type
	 */
	public Class<?> getPropertyEditorType() {
		return get().getPropertyEditorType();
	}

	/**
	 * Sets whether decimal value are allowed (defaults to true).
	 * 
	 * @param allowDecimals
	 *            true to allow negative values
	 */
	public void setAllowDecimals(boolean allowDecimals) {
		get().setAllowDecimals(allowDecimals);
	}

	/**
	 * Sets whether negative value are allowed.
	 * 
	 * @param allowNegative
	 *            true to allow negative values
	 */
	public void setAllowNegative(boolean allowNegative) {
		get().setAllowNegative(allowNegative);
	}

	/**
	 * Sets the base set of characters to evaluate as valid numbers (defaults to
	 * '0123456789').
	 * 
	 * @param baseChars
	 *            the base character
	 */
	public void setBaseChars(String baseChars) {
		get().setBaseChars(baseChars);
	}

	/**
	 * Sets the cell's number formatter.
	 * 
	 * @param format
	 *            the format
	 */
	public void setFormat(NumberFormat format) {
		get().getPropertyEditor().setFormat(format);
	}

	/**
	 * Sets the increment that should be used (defaults to 1d).
	 * 
	 * @param increment
	 *            the increment to set.
	 */
	public void setIncrement(Number increment) {
		get().setIncrement(increment);
	}

	/**
	 * Sets the field's max allowable value.
	 * 
	 * @param maxValue
	 *            the max value
	 */
	public void setMaxValue(Number maxValue) {
		get().setMaxValue(maxValue);
	}

	/**
	 * Sets the field's minimum allowed value.
	 * 
	 * @param minValue
	 *            the minimum value
	 */
	public void setMinValue(Number minValue) {
		get().setMinValue(minValue);
	}

	/**
	 * Specifies the number type used when converting a String to a Number
	 * instance (defaults to Double).
	 * 
	 * @param type
	 *            the number type (Short, Integer, Long, Float, Double).
	 */
	public void setPropertyEditorType(Class<?> type) {
		get().getPropertyEditor().setType(type);
	}

}
