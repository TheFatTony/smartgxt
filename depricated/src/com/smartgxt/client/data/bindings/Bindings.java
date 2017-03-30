package com.smartgxt.client.data.bindings;

import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.tips.Tip;
import com.google.gwt.user.client.Timer;
import com.smartgxt.client.managers.CompatibilityManager;

/**
 * @author Anton Alexeyev
 * 
 */
public class Bindings extends com.extjs.gxt.ui.client.binding.Bindings {

	protected Tip tip = new Tip();
	protected Timer timer;
	protected static int dissmissDalay = 4000;

	/**
	 * 
	 */
	public Bindings() {
		tip.add(new Html("Не все обязательные поля заполнены"));
		tip.addStyleName("x-form-invalid-tip");
		tip.setHeaderVisible(false);
		timer = new Timer() {

			@Override
			public void run() {
				tip.hide();
			}
		};
	}

	public void addBinding(Field<?> field) {
		addFieldBinding(new FieldBinding(field, field.getName()));
	}

	public ModelData getFildsModel() {
		BaseModel bm = new BaseModel();
		for (FieldBinding binding : getBindings()) {
			Field<?> field = binding.getField();
			field.validate();
			if (field.isValid())
				bm.set(field.getName(), field.getValue());
			else {
				tip.showAt(CompatibilityManager.getMousePosition());
				timer.schedule(dissmissDalay);
			}

		}
		return bm;
	}

}
