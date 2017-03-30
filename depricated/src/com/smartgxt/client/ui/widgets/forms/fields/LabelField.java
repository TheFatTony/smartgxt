package com.smartgxt.client.ui.widgets.forms.fields;

/**
 * @author Anton Alexeyev
 * 
 */
public class LabelField extends LabeledField<Object> {

	public LabelField() {
		super(new com.extjs.gxt.ui.client.widget.form.LabelField());
	}

	@Override
	public com.extjs.gxt.ui.client.widget.form.LabelField get() {
		return (com.extjs.gxt.ui.client.widget.form.LabelField) getField();
	}

	/**
	 * Returns the field's text.
	 * 
	 * @return the text
	 */
	public String getText() {
		return get().getText();
	}

	/**
	 * Sets the lable's text.
	 * 
	 * @param text
	 *            the text as HTML
	 */
	public void setText(String text) {
		get().setText(text);
	}

}
