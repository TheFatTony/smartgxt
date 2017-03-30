package com.smartgxt.client.link.base.new2;

import java.util.List;

import com.extjs.gxt.ui.client.data.LoadEvent;
import com.extjs.gxt.ui.client.data.Loader;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.smartgxt.client.ui.widgets.grids.filters.GidFiltersHelper;

public class FiledsStoreLink extends BaseLink {
	private List<Field<?>> fields = null;
	private ListStore<?> store = null;

	public FiledsStoreLink(Object parent, Object child) {
		super(parent, child);
		if (getChild() instanceof List)
			fields = getChild();
		else if (getChild() instanceof Store<?>)
			store = getChild();

		if (getParent() instanceof List<?>)
			fields = getParent();
		else if (getParent() instanceof Store<?>)
			store = getParent();
	}

	public List<Field<?>> getFields() {
		return fields;
	}

	public void setFields(List<Field<?>> fields) {
		this.fields = fields;
	}

	public ListStore<?> getStore() {
		return store;
	}

	public void setStore(ListStore<?> store) {
		this.store = store;
	}

	@Override
	public void onExecute() {
		for (Field<?> f : getFields()) {
			f.addListener(Events.Valid, new Listener<FieldEvent>() {
				@Override
				public void handleEvent(FieldEvent be) {
					GidFiltersHelper.addFilter(getStore(), be.getField());
				}

			});
		}
		getStore().getLoader().addListener(Loader.BeforeLoad,
				new Listener<LoadEvent>() {

					@Override
					public void handleEvent(LoadEvent be) {
						for (Field<?> f : getFields()) {
							f.validate();
						}
					}
				});
	}

}
