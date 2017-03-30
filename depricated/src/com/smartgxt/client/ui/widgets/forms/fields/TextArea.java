package com.smartgxt.client.ui.widgets.forms.fields;

/**
 * @author Anton Alexeyev
 * 
 */
public class TextArea extends LabeledTextField<String> {

	public TextArea() {
		super(new com.extjs.gxt.ui.client.widget.form.TextArea());
		setHeight(100);
	}

	@Override
	public com.extjs.gxt.ui.client.widget.form.TextArea get() {
		return (com.extjs.gxt.ui.client.widget.form.TextArea) getField();
	}

	/**
	 * Returns true if scroll bars are disabled.
	 * 
	 * @return the scroll bar state
	 */
	public boolean isPreventScrollbars() {
		return get().isPreventScrollbars();
	}

	/**
	 * True to prevent scrollbars from appearing regardless of how much text is
	 * in the field (equivalent to setting overflow: hidden, defaults to false,
	 * pre-render).
	 * 
	 * @param preventScrollbars
	 *            true to disable scroll bars
	 */
	public void setPreventScrollbars(boolean preventScrollbars) {
		get().setPreventScrollbars(preventScrollbars);
	}

}
