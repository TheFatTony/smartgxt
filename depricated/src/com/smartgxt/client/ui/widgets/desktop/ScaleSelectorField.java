package com.smartgxt.client.ui.widgets.desktop;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.google.gwt.user.client.Element;
import com.smartgxt.client.core.SmartGXT;
import com.smartgxt.client.managers.Scale;
import com.smartgxt.client.managers.ScaleManager;

/**
 * @author Anton Alexeyev
 * 
 */
@Deprecated
public class ScaleSelectorField extends ComboBox<Scale> {

	private Scale last;

	public ScaleSelectorField() {
		setEditable(false);
		setWidth(150);
		// TODO localization
		setToolTip("Выберите масштаб приложения");
		setDisplayField("name");
		setValueField("id");
		setTriggerAction(TriggerAction.ALL);
	}

	@Override
	protected void beforeRender() {
		super.beforeRender();
		ListStore<Scale> store = new ListStore<Scale>();
		store.add(ScaleManager.getScales());
		setStore(store);

		String scale = SmartGXT.getScaleId();
		if (scale == null) {
			setValue(Scale.NORMAL);
		} else {
			for (Scale l : store.getModels()) {
				if (scale.equals(l.getId().toLowerCase())) {
					setValue(l);
					break;
				}
			}
		}
		last = getValue();
	}

	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		Listener<FieldEvent> l = new Listener<FieldEvent>() {
			public void handleEvent(FieldEvent be) {
				Scale s = getValue();
				if (s != last) {
					last = s;
					SmartGXT.switchScale(s);
				}
			}
		};
		addListener(Events.Change, l);
		addListener(Events.Collapse, l);
	}
}
