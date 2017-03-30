package com.smartgxt.client.link.base.new2;

import java.util.List;

import com.extjs.gxt.ui.client.data.LoadEvent;
import com.extjs.gxt.ui.client.data.Loader;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Store;
import com.smartgxt.client.data.bindings.Bindings;
import com.smartgxt.client.ui.widgets.grids.filters.GidFiltersHelper;

public class FiledsBindingStoreLink extends BaseLink {
	private Bindings bindings = null;
	private ListStore<?> store = null;

	public FiledsBindingStoreLink(Object parent, Object child) {
		super(parent, child);

		if (getChild() instanceof Bindings)
			bindings = getChild();
		else if (getChild() instanceof Bindings)
			store = getChild();

		if (getParent() instanceof List<?>)
			bindings = getParent();
		else if (getParent() instanceof Store<?>)
			store = getParent();
	}

	public Bindings getBindings() {
		return bindings;
	}

	public void setBindings(Bindings bindings) {
		this.bindings = bindings;
	}

	public ListStore<?> getStore() {
		return store;
	}

	public void setStore(ListStore<?> store) {
		this.store = store;
	}

	@Override
	public void onExecute() {
		// f.addListener(Events.Valid, new Listener<FieldEvent>() {
		// @Override
		// public void handleEvent(FieldEvent be) {
		// GidFiltersHelper.addFilter(getStore(), be.getField());
		// }
		//
		// });
		getStore().getLoader().addListener(Loader.BeforeLoad,
				new Listener<LoadEvent>() {

					@Override
					public void handleEvent(LoadEvent be) {
						GidFiltersHelper.addFilter(getStore(), getBindings()
								.getFildsModel());
					}
				});
	}

}
