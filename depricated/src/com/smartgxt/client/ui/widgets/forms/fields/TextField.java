package com.smartgxt.client.ui.widgets.forms.fields;

import com.extjs.gxt.ui.client.widget.form.Validator;

/**
 * @author Anton Alexeyev
 * 
 */
public class TextField extends LabeledTextField<String> {

	public TextField() {
		super(new com.extjs.gxt.ui.client.widget.form.TextField<String>());
	}

	public com.extjs.gxt.ui.client.widget.form.TextField<String> get() {
		return (com.extjs.gxt.ui.client.widget.form.TextField<String>) getField();
	}

	/**
	 * Returns true if the field is a password field.
	 * 
	 * @return that password state
	 */
	public boolean isPassword() {
		return get().isPassword();
	}

	/**
	 * True to create the text field as a password input (defaults to false,
	 * pre-render).
	 * 
	 * @param password
	 *            the password state
	 */
	public void setPassword(boolean password) {
		get().setPassword(password);
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
		get().setValidator(validator);
	}

}
