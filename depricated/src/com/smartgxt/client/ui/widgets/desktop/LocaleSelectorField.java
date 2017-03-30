package com.smartgxt.client.ui.widgets.desktop;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.google.gwt.user.client.Element;
import com.smartgxt.client.core.SmartGXT;
import com.smartgxt.client.managers.Locale;
import com.smartgxt.client.managers.LocaleManager;

/**
 * @author Anton Alexeyev
 * 
 */
@Deprecated
public class LocaleSelectorField extends ComboBox<Locale> {

	private Locale last;

	public LocaleSelectorField() {
		setEditable(false);
		setWidth(150);
		// TODO localization
		setToolTip("Выберите язык приложения");
		setDisplayField("name");
		setValueField("id");
		setTriggerAction(TriggerAction.ALL);
	}

	@Override
	protected void beforeRender() {
		super.beforeRender();
		ListStore<Locale> store = new ListStore<Locale>();
		store.add(LocaleManager.getLocales());
		setStore(store);

		String locale = SmartGXT.getLocaleId();
		if (locale == null) {
			setValue(Locale.ENGLISH);
		} else {
			for (Locale l : store.getModels()) {
				if (locale.equals(l.getId().toLowerCase())) {
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
				Locale c = getValue();
				if (c != last) {
					last = c;
					SmartGXT.switchLocale(c);
				}
			}
		};
		addListener(Events.Change, l);
		addListener(Events.Collapse, l);
	}
}
