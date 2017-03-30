package com.smartgxt.incubator.client;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Event;
import com.sencha.gxt.widget.core.client.event.SubmitCompleteEvent;
import com.sencha.gxt.widget.core.client.event.SubmitCompleteEvent.SubmitCompleteHandler;
import com.sencha.gxt.widget.core.client.form.AdapterField;
import com.sencha.gxt.widget.core.client.form.FileUploadField.FileUploadFieldMessages;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.FormPanel.Encoding;
import com.sencha.gxt.widget.core.client.form.FormPanel.Method;
import com.smartgxt.core.client.rpc.RequestProcessor;
import com.smartgxt.core.shared.data.FileDefenition;

public class FileUploadField extends AdapterField<FileDefenition> {

	private FormPanel form;
	private com.sencha.gxt.widget.core.client.form.FileUploadField fileUploadField;

	public FileUploadField() {
		super(new FormPanel());
		form = (FormPanel) getWidget();
		form.setMethod(Method.POST);
		form.setEncoding(Encoding.MULTIPART);
		form.setAction(RequestProcessor.getHttpServletUrl());
		form.add(getFileUploadField());
		form.addSubmitCompleteHandler(new SubmitCompleteHandler() {
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				String str = event.getResults();
				if (str.indexOf('>') != -1)
					str = str.substring(str.indexOf('>') + 1, str.indexOf("</"));
				fileUploadField.setData("sgxt.value", str);
			}
		});
		// form.addListener(Events.Submit, new Listener<FormEvent>() {
		// @Override
		// public void handleEvent(FormEvent be) {
		// String str = be.getResultHtml();
		// if (str.indexOf('>') != -1)
		// str = str.substring(str.indexOf('>') + 1, str.indexOf("</"));
		// fileUploadField.setData("sgxt.value", str);
		// box.close();
		// }
		// });
	}

	public com.sencha.gxt.widget.core.client.form.FileUploadField getFileUploadField() {
		if (fileUploadField == null) {
			fileUploadField = new com.sencha.gxt.widget.core.client.form.FileUploadField();
			fileUploadField.addChangeHandler(new ChangeHandler() {

				@Override
				public void onChange(ChangeEvent event) {
					// if (!Util.equalWithNull(event.getValue(), oldValue)) {
					// box = MessageBox
					// .wait("Progress",
					// "Saving your data, please wait...",
					// "Saving...");
					// form.submit();
					// }
					// oldValue = be.getValue();
					form.submit();
				}
			});
			// fileUploadField.addListener(Events.Change,
			// onFileUploadFieldChange);
		}
		return fileUploadField;
	}

	public void setFileUploadField(
			com.sencha.gxt.widget.core.client.form.FileUploadField fileUploadField) {
		this.fileUploadField = fileUploadField;
	}

	@Override
	public void clear() {
		getFileUploadField().clear();
	}

	@Override
	public void clearInvalid() {
		getFileUploadField().clearInvalid();
	}

	/**
	 * Returns the file upload field messages.
	 * 
	 * @return the messages
	 */
	public FileUploadFieldMessages getMessages() {
		return getFileUploadField().getMessages();
	}

	public String getName() {
		return getFileUploadField().getName();
	}

	public FileDefenition getValue() {
		return fileUploadField.getData("sgxt.value");
	}

	/**
	 * Returns the field's allow blank state.
	 * 
	 * @return true if blank values are allowed
	 */
	public boolean isAllowBlank() {
		return getFileUploadField().isAllowBlank();
	}

	/**
	 * Returns the read only state.
	 * 
	 * @return true if read only, otherwise false
	 */
	public boolean isReadOnly() {
		return getFileUploadField().isReadOnly();
	}

	/**
	 * Returns whether or not the field value is currently valid.
	 * 
	 * @return true if the value is valid, otherwise false
	 */
	public boolean isValid() {
		return getFileUploadField().isValid();
	}

	@Override
	public boolean isValid(boolean preventMark) {
		return getFileUploadField().isValid(preventMark);
	}

	@Override
	public void onBrowserEvent(Event event) {
		getFileUploadField().onBrowserEvent(event);
	}

	@Override
	public void reset() {
		getFileUploadField().clear();
	}

	/**
	 * Sets whether a field is valid when its value length = 0 (default to
	 * true).
	 * 
	 * @param allowBlank
	 *            true to allow blanks, false otherwise
	 */
	public void setAllowBlank(boolean allowBlank) {
		getFileUploadField().setAllowBlank(allowBlank);
	}

	/**
	 * Sets the file upload field messages.
	 * 
	 * @param messages
	 *            the messages
	 */
	public void setMessages(FileUploadFieldMessages messages) {
		getFileUploadField().setMessages(messages);
	}

	public void setName(String name) {
		getFileUploadField().setName(name);
	}

	/**
	 * Sets the field's read only state.
	 * 
	 * @param readonly
	 *            the read only state
	 */
	public void setReadOnly(boolean readonly) {
		getFileUploadField().setReadOnly(readonly);
	}

	@Override
	public boolean validate(boolean preventMark) {
		return getFileUploadField().validate(preventMark);
	}

	@Override
	public void setValue(FileDefenition value) {
		getFileUploadField().setValue(null);
	}

}
