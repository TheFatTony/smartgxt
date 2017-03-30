package com.smartgxt.client.link.base.new2;

import com.extjs.gxt.ui.client.data.LoadEvent;
import com.extjs.gxt.ui.client.data.Loader;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.smartgxt.client.ui.widgets.grids.filters.GidFiltersHelper;

public class FiledStoreLink extends BaseLink {

	private Field<?> field = null;
	private ListStore<?> store = null;

	public FiledStoreLink(Object parent, Object child) {
		super(parent, child);
		if (getChild() instanceof Field<?>)
			field = getChild();
		else if (getChild() instanceof Store<?>)
			store = getChild();

		if (getParent() instanceof Field<?>)
			field = getParent();
		else if (getParent() instanceof Store<?>)
			store = getParent();
	}

	public Field<?> getField() {
		return field;
	}

	public void setField(Field<?> field) {
		this.field = field;
	}

	public ListStore<?> getStore() {
		return store;
	}

	public void setStore(ListStore<?> store) {
		this.store = store;
	}

	@Override
	public void onExecute() {
		getField().addListener(Events.Valid, new Listener<FieldEvent>() {
			@Override
			public void handleEvent(FieldEvent be) {
				GidFiltersHelper.addFilter(getStore(), be.getField());
			}
		});

		getStore().getLoader().addListener(Loader.BeforeLoad,
				new Listener<LoadEvent>() {

					@Override
					public void handleEvent(LoadEvent be) {
						getField().validate();
					}
				});
	}
}
