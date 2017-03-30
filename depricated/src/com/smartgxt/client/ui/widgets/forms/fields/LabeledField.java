package com.smartgxt.client.ui.widgets.forms.fields;

import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.binding.Converter;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.MultiField;
import com.google.gwt.user.client.Element;
import com.smartgxt.client.util.UIHelper;

/**
 * @author Anton Alexeyev
 * 
 */
public abstract class LabeledField<D> extends MultiField<D> {

	protected LabelField label;
	protected String labelText;
	protected Field<D> field;
	// private FieldsConfig fieldsConfig;
	private int index;
	private boolean focused;
	private int labelWidth = 120;
	private String labelSeparator = ":";
	private LabelAlign labelAlign = LabelAlign.LEFT;

	private Converter converter;

	public LabeledField(Field<D> field) {
		super();
		// fieldsConfig = new FieldsConfig();
		setMessageTarget("tooltip");
		label = new LabelField();
		label.setWidth(getLabelWidth());
		add(label);
		if (field != null)
			setField(field);

		setHeight(22);
		setWidth(220);
	}

	@Override
	public final void setOrientation(Orientation orientation) {
		// super.setOrientation(orientation);
	}

	public LabeledField() {
		super();
		// fieldsConfig = new FieldsConfig();
		setMessageTarget("tooltip");
		label = new LabelField();
		label.setWidth(getLabelWidth());
		add(label);

		setHeight(22);
		setWidth(220);
	}

	public abstract Field<D> get();

	@Override
	public void addListener(EventType eventType,
			Listener<? extends BaseEvent> listener) {
		if (eventType.equals(Events.Valid) || eventType.equals(Events.Change))
			get().addListener(eventType, listener);
		else
			super.addListener(eventType, listener);
	}

	public String getLabelSeparator() {
		return labelSeparator;
	}

	public void setLabelSeparator(String labelSeparator) {
		this.labelSeparator = labelSeparator;
	}

	@Override
	public void markInvalid(String msg) {
		field.markInvalid(msg);
	}

	@Override
	public void forceInvalid(String msg) {
		field.forceInvalid(msg);
	}

	@Override
	public void clearInvalid() {
		field.clearInvalid();
	}

	@Override
	protected void onAttach() {
		super.onAttach();
		if (focused)
			UIHelper.getParentWindow(this).setFocusWidget(field);
	}

	@Override
	public boolean validate() {
		return field.validate();
	}

	/**
	 * Validates the field value.
	 * 
	 * @param preventMark
	 *            true to not mark the field valid and fire invalid event when
	 *            invalid
	 * @return <code>true</code> if valid, otherwise <code>false</code>
	 */
	@Override
	public boolean validate(boolean preventMark) {
		return field.validate(preventMark);
	}

	@Override
	public boolean isValid() {
		return field.isValid();
	}

	@Override
	public boolean isValid(boolean silent) {
		return field.isValid(silent);
	}

	@Override
	public void setFieldLabel(String fieldLabel) {
		labelText = fieldLabel;
		label.setText(fieldLabel);
	}

	@Override
	public String getFieldLabel() {
		return label.getText();
	}

	protected Field<D> getField() {
		return field;
	}

	public Object oldValue;

	public void setField(final Field<D> field) {
		this.field = field;
		field.setAutoValidate(true);
		field.setMessageTarget("tooltip");
		field.setWidth(getWidth() - getLabelWidth());
		add(field);
		// label.setFieldLabel(field.getFieldLabel());
	}

	@Override
	public void setValue(D value) {
		field.setValue(value);
	};

	protected D onConvertFieldValue(Object value) {
		if (converter != null) {
			return (D) converter.convertFieldValue(value);
		}
		return (D) value;
	}

	protected Object onConvertModelValue(Object value) {
		if (converter != null) {
			return converter.convertModelValue(value);
		}
		return value;
	}

	@Override
	public D getValue() {
		return onConvertFieldValue(field.getValue());
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public void setFocused(boolean focused) {
		this.focused = focused;
	}

	public boolean isFocused() {
		return focused;
	}

	@Override
	protected void onRender(Element target, int index) {
		if (labelText != null)
			label.setText(labelText + getLabelSeparator());
		super.onRender(target, index);
	}

	@Override
	protected void onResize(int width, int height) {
		super.onResize(width, height);
		if (labelAlign == LabelAlign.LEFT) {
			super.setOrientation(Orientation.HORIZONTAL);
			field.setWidth(getWidth() - getLabelWidth());
		} else if (labelAlign == LabelAlign.TOP) {
			super.setOrientation(Orientation.VERTICAL);
			field.setWidth(getWidth());
		}
	}

	@Override
	protected boolean validateValue(String value) {
		boolean b = super.validateValue(value);
		return b;
	}

	public void setLabelWidth(int labelWidth) {
		this.labelWidth = labelWidth;
		if (labelAlign == LabelAlign.LEFT) {
			label.setWidth(getLabelWidth());
			field.setWidth(getWidth() - getLabelWidth());
		}
	}

	public int getLabelWidth() {
		return labelWidth;
	}

	public void setLabelAlign(LabelAlign labelAlign) {
		this.labelAlign = labelAlign;
		if (labelAlign == LabelAlign.LEFT) {
			super.setOrientation(Orientation.HORIZONTAL);
			// setHeight(22);
			// label.setWidth(getLabelWidth());
			// field.setWidth(getWidth() - getLabelWidth());
		} else if (labelAlign == LabelAlign.TOP) {
			super.setOrientation(Orientation.VERTICAL);
			// setHeight(44);
			// label.setWidth(getLabelWidth());
			// field.setWidth(getWidth());
		}
	}

	public LabelAlign getLabelAlign() {
		return labelAlign;
	}

	public Converter getConverter() {
		return converter;
	}

	public void setConverter(Converter converter) {
		this.converter = converter;
	}

	// public void setFieldsConfig(FieldsConfig fieldsConfig) {
	// if (fieldsConfig != null)
	// fieldsConfig.getFelds().add(this);
	// else if (this.fieldsConfig != null)
	// this.fieldsConfig.getFelds().remove(this);
	//
	// this.fieldsConfig = fieldsConfig;
	// setLabelSeparator(fieldsConfig.getLabelSeparator());
	// setLabelWidth(fieldsConfig.getLabelWidth());
	// }
	//
	// public FieldsConfig getFieldsConfig() {
	// return fieldsConfig;
	// }

}
