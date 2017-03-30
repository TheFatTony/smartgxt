package com.smartgxt.client.link.base;

import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.smartgxt.client.data.bindings.Bindings;

public class FiledsBindingLinkData extends BaseLinkInfo {

	private Bindings bindings = null;
	private ListStore<?> store = null;

	public FiledsBindingLinkData(Object parent, Object child) {
		super(parent, child);

		if (getChild() instanceof Bindings)
			bindings = getChild();
		else if (getChild() instanceof Store<?>)
			store = getChild();

		if (getParent() instanceof Bindings)
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

}
