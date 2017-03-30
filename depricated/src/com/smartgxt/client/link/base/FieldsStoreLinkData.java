package com.smartgxt.client.link.base;


import java.util.List;

import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.widget.form.Field;

public class FieldsStoreLinkData extends BaseLinkInfo {

	private List<Field<?>> fields = null;
	private ListStore<?> store = null;

	public FieldsStoreLinkData(Object parent, Object child) {
		super(parent, child);
		
		if (getChild() instanceof List<?>)
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

}
