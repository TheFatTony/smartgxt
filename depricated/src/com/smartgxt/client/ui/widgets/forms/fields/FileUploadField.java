package com.smartgxt.client.ui.widgets.forms.fields;

import com.smartgxt.client.ui.widgets.custom.forms.FormPanelAdapter;
import com.smartgxt.shared.data.FileDefenition;

/**
 * @author Anton Alexeyev
 * 
 */
public class FileUploadField extends LabeledTextField<Object> {

	public FileUploadField() {
		super(new FormPanelAdapter());
	}

	public FormPanelAdapter get() {
		return ((FormPanelAdapter) getField());
	}

	@Override
	public void setName(String name) {
		super.setName(name);
		get().setName(name);
	}

	/**
	 * Returns the field's allow blank state.
	 * 
	 * @return true if blank values are allowed
	 */
	public boolean getAllowBlank() {
		return get().getFileUploadField().getAllowBlank();
	}

	/**
	 * Sets whether a field is valid when its value length = 0 (default to
	 * true).
	 * 
	 * @param allowBlank
	 *            true to allow blanks, false otherwise
	 */
	public void setAllowBlank(boolean allowBlank) {
		get().getFileUploadField().setAllowBlank(allowBlank);
	}

	@Override
	public FileDefenition getValue() {
		if (get().getValue() != null)
			return new FileDefenition(get().getValue().toString());
		else
			return null;
	}

	@Override
	public void clearInvalid() {
		get().getFileUploadField().clearInvalid();
	}

	@Override
	public void forceInvalid(String msg) {
		get().getFileUploadField().forceInvalid(msg);
	}

	@Override
	public boolean isValid() {
		return get().getFileUploadField().isValid();
	}

	@Override
	public boolean isValid(boolean silent) {
		return get().getFileUploadField().isValid(silent);
	}

}
