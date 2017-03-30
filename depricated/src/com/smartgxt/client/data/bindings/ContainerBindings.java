package com.smartgxt.client.data.bindings;

import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.Container;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.tips.Tip;
import com.google.gwt.user.client.Timer;
import com.smartgxt.client.managers.CompatibilityManager;

/**
 * @author Anton Alexeyev
 * 
 */
public class ContainerBindings extends Bindings {

	protected Container<?> container;
	protected Tip tip = new Tip();
	protected Timer timer;
	protected static int dissmissDalay = 4000;

	public ContainerBindings(Container<?> container) {
		// FormButtonBinding
		this.container = container;
		tip.add(new Html("Не все обязательные поля заполнены"));
		tip.addStyleName("x-form-invalid-tip");
		tip.setHeaderVisible(false);
		timer = new Timer() {

			@Override
			public void run() {
				tip.hide();
			}
		};
		bindAll();
	}

	// TODO recursive
	public void bindAll() {
		for (int i = 0; i < container.getItemCount(); i++) {
			Component component = container.getItem(i);
			if (component instanceof Field<?>) {
				Field<?> field = (Field<?>) component;
				addBinding(field);
			}

		}
	}

	public ModelData getData() {
		boolean hasInvalid = false;
		BaseModel data = new BaseModel();
		for (FieldBinding fieldBinding : getBindings()) {
			Field<?> f = fieldBinding.getField();
			if (f.isValid(false))
				data.set(fieldBinding.getProperty(), fieldBinding.getField()
						.getValue());
			else
				hasInvalid = true;
		}
		if (hasInvalid) {
			tip.showAt(CompatibilityManager.getMousePosition());
			timer.schedule(dissmissDalay);
			// MessageBox.alert(XMessages.instance.alert_error_text(),
			// "Не все обязательные поля заполнены", null);
			return null;
		} else
			return data;
	}
}
