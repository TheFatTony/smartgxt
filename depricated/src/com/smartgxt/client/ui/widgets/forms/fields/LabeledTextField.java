package com.smartgxt.client.ui.widgets.forms.fields;

import com.extjs.gxt.ui.client.widget.form.AdapterField;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.MultiField;
import com.extjs.gxt.ui.client.widget.form.Validator;
import com.google.gwt.user.client.Element;

/**
 * @author Anton Alexeyev
 * 
 */
public abstract class LabeledTextField<D> extends LabeledField<D> {

	public LabeledTextField(Field<D> field) {
		super();
		assert (field instanceof com.extjs.gxt.ui.client.widget.form.TextField
				|| field instanceof AdapterField || field instanceof MultiField<?>) : "Mast be instance of TextField";
		setField(field);
	}

	/**
	 * Returns the field's allow blank state.
	 * 
	 * @return true if blank values are allowed
	 */
	public boolean getAllowBlank() {
		return ((com.extjs.gxt.ui.client.widget.form.TextField<?>) get())
				.getAllowBlank();
	}

	/**
	 * Sets whether a field is valid when its value length = 0 (default to
	 * true).
	 * 
	 * @param allowBlank
	 *            true to allow blanks, false otherwise
	 */
	public void setAllowBlank(boolean allowBlank) {
		((com.extjs.gxt.ui.client.widget.form.TextField<?>) get())
				.setAllowBlank(allowBlank);
	}

	/**
	 * Returns the cursor position.
	 * 
	 * @return the cursor position
	 */
	public int getCursorPos() {
		return ((com.extjs.gxt.ui.client.widget.form.TextField<?>) get())
				.getCursorPos();
	}

	/**
	 * Returns the field's max length.
	 * 
	 * @return the max length
	 */
	public int getMaxLength() {
		return ((com.extjs.gxt.ui.client.widget.form.TextField<?>) get())
				.getMaxLength();
	}

	/**
	 * Returns the minimum length.
	 * 
	 * @return the min length
	 */
	public int getMinLength() {
		return ((com.extjs.gxt.ui.client.widget.form.TextField<?>) get())
				.getMinLength();
	}

	/**
	 * Returns the field's regex value.
	 * 
	 * @return the regex value
	 */
	public String getRegex() {
		return ((com.extjs.gxt.ui.client.widget.form.TextField<?>) get())
				.getRegex();
	}

	/**
	 * Returns the selected text.
	 * 
	 * @return the selected text
	 */
	public String getSelectedText() {
		return ((com.extjs.gxt.ui.client.widget.form.TextField<?>) get())
				.getSelectedText();
	}

	/**
	 * Returns the length of the current selection.
	 * 
	 * @return the selection length
	 */
	public int getSelectionLength() {
		return ((com.extjs.gxt.ui.client.widget.form.TextField<?>) get())
				.getSelectionLength();
	}

	/**
	 * Returns the select of focus state.
	 * 
	 * @return true if select on focus is enabled
	 */
	public boolean getSelectOnFocus() {
		return ((com.extjs.gxt.ui.client.widget.form.TextField<?>) get())
				.getSelectOnFocus();
	}

	/**
	 * Returns the field's validator instance.
	 * 
	 * @return the validator
	 */
	public Validator getValidator() {
		return ((com.extjs.gxt.ui.client.widget.form.TextField<?>) get())
				.getValidator();
	}

	/**
	 * Returns true if the field is a password field.
	 * 
	 * @return that password state
	 */
	public boolean isPassword() {
		return ((com.extjs.gxt.ui.client.widget.form.TextField<?>) get())
				.isPassword();
	}

	/**
	 * Sets the cursor position.
	 * 
	 * @param pos
	 *            the position
	 */
	public void setCursorPos(int pos) {
		((com.extjs.gxt.ui.client.widget.form.TextField<?>) get())
				.setSelectionRange(pos, 0);
	}

	@Override
	public void setEmptyText(String emptyText) {
		((com.extjs.gxt.ui.client.widget.form.TextField<?>) get())
				.setEmptyText(emptyText);
	}

	/**
	 * Sets the maximum input field length.
	 * 
	 * @param maxLength
	 *            the max length
	 */
	public void setMaxLength(int maxLength) {
		((com.extjs.gxt.ui.client.widget.form.TextField<?>) get())
				.setMaxLength(maxLength);
	}

	/**
	 * Minimum input field length required (defaults to 0).
	 * 
	 * @param minLength
	 *            the minimum length
	 */
	public void setMinLength(int minLength) {
		((com.extjs.gxt.ui.client.widget.form.TextField<?>) get())
				.setMinLength(minLength);
	}

	/**
	 * True to create the text field as a password input (defaults to false,
	 * pre-render).
	 * 
	 * @param password
	 *            the password state
	 */
	public void setPassword(boolean password) {
		((com.extjs.gxt.ui.client.widget.form.TextField<?>) get())
				.setPassword(password);
	}

	/**
	 * Sets regular expression to be tested against the field value during
	 * validation. If available, this regex will be evaluated only after the
	 * basic validators all return true. If the test fails, the field will be
	 * marked invalid using the regex error message.
	 * 
	 * @param regex
	 *            the regex expression
	 */
	public void setRegex(String regex) {
		((com.extjs.gxt.ui.client.widget.form.TextField<?>) get())
				.setRegex(regex);
	}

	/**
	 * Selects the range.
	 * 
	 * @param pos
	 *            the position
	 * @param length
	 *            the range length
	 */
	public void setSelectionRange(int pos, int length) {
		((com.extjs.gxt.ui.client.widget.form.TextField<?>) get())
				.setSelectionRange(pos, length);
	}

	/**
	 * True to automatically select any existing field text when the field
	 * receives input focus (defaults to false).
	 * 
	 * @param selectOnFocus
	 *            true to focus
	 */
	public void setSelectOnFocus(boolean selectOnFocus) {
		((com.extjs.gxt.ui.client.widget.form.TextField<?>) get())
				.setSelectOnFocus(selectOnFocus);
	}

	/**
	 * Sets the validator instance to be called during field validation. It will
	 * be called only after the basic validators all return true, and will be
	 * passed the current field value and expected to return <code>null</code>
	 * if the value is valid or a string error message if invalid. Default value
	 * is <code>null</code>.
	 * 
	 * @param validator
	 *            the validator
	 */
	public void setValidator(Validator validator) {
		((com.extjs.gxt.ui.client.widget.form.TextField<?>) get())
				.setValidator(validator);
	}

	@Override
	protected void onRender(Element target, int index) {
		if (!getAllowBlank()) {
			setFieldLabel(getFieldLabel() + "*");
		}
		super.onRender(target, index);
	}
}
