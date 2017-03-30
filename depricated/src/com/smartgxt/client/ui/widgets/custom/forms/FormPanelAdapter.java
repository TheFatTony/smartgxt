package com.smartgxt.client.ui.widgets.custom.forms;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.FormEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.util.Util;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.form.AdapterField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.FormPanel.Encoding;
import com.extjs.gxt.ui.client.widget.form.FormPanel.Method;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.google.gwt.core.client.GWT;

/**
 * @author Anton Alexeyev
 * 
 */
public class FormPanelAdapter extends AdapterField {

	private FormPanel form;
	private FileUploadFieldProt fileUploadField;
	private MessageBox box;

	public FormPanelAdapter() {
		super(new FormPanel());
		form = (FormPanel) getWidget();
		form.setHeaderVisible(false);
		form.setBodyBorder(false);
		form.setBorders(false);
		form.setPadding(0);
		form.setFrame(false);
		form.setMethod(Method.POST);
		form.setEncoding(Encoding.MULTIPART);
		form.setHideLabels(true);
		form.setAction(GWT.getModuleBaseURL() + "raw");
		form.add(getFileUploadField(), new FormData("100%"));
		form.addListener(Events.Submit, new Listener<FormEvent>() {
			@Override
			public void handleEvent(FormEvent be) {
				String str = be.getResultHtml();
				if (str.indexOf('>') != -1)
					str = str.substring(str.indexOf('>') + 1, str.indexOf("</"));
				fileUploadField.setData("sgxt.value", str);
				box.close();
			}
		});

		setResizeWidget(true);
	}

	@Override
	public void addListener(EventType eventType,
			Listener<? extends BaseEvent> listener) {
		if (eventType.equals(Events.Valid) || eventType.equals(Events.Change))
			fileUploadField.addListener(eventType, listener);
		else
			super.addListener(eventType, listener);
	}

	@Override
	public void setName(String name) {
		getFileUploadField().setName(name);
	}

	@Override
	protected void onFocus(ComponentEvent ce) {
		// TODO Auto-generated method stub
		fileUploadField.fireEvent(Events.Focus);
	}

	@Override
	public boolean isValid(boolean silent) {
		return fileUploadField.isValid(silent);
	}

	@Override
	public boolean isValid() {
		return fileUploadField.isValid();
	}

	@Override
	protected boolean validateValue(String value) {
		return fileUploadField.validateValue(value);
	}

	@Override
	protected void onBlur(ComponentEvent ce) {
		fileUploadField.fireEvent(Events.Blur);
	}

	@Override
	public void setFireChangeEventOnSetValue(boolean fireChangeEventOnSetValue) {
		super.setFireChangeEventOnSetValue(fireChangeEventOnSetValue);
		//
		fileUploadField.setFireChangeEventOnSetValue(fireChangeEventOnSetValue);
	}

	public Object oldValue;

	public FileUploadFieldProt getFileUploadField() {
		if (fileUploadField == null) {
			fileUploadField = new FileUploadFieldProt();
			fileUploadField.setFireChangeEventOnSetValue(true);
			fileUploadField.addListener(Events.Change, onFileUploadFieldChange);
		}
		return fileUploadField;
	}

	private Listener<FieldEvent> onFileUploadFieldChange = new Listener<FieldEvent>() {
		@Override
		public void handleEvent(FieldEvent be) {
			if (!Util.equalWithNull(be.getValue(), oldValue)) {
				box = MessageBox.wait("Progress",
						"Saving your data, please wait...", "Saving...");
				form.submit();
			}
			oldValue = be.getValue();
		}
	};

	public Object getValue() {
		return fileUploadField.getData("sgxt.value");
	};

	public class FileUploadFieldProt extends
			com.extjs.gxt.ui.client.widget.form.FileUploadField {

		public boolean validateValue(String value) {
			return super.validateValue(value);
		};

		@Override
		protected void fireChangeEvent(Object oldValue, Object value) {
			super.fireChangeEvent(oldValue, value);
		}

	}

}
