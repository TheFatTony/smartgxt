package com.smartgxt.client.link.base;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.google.gwt.core.client.GWT;
import com.smartgxt.client.ui.widgets.grids.filters.GidFiltersHelper;
import com.sun.corba.se.impl.javax.rmi.CORBA.Util;

public class FieldStoreLinkData extends BaseLinkInfo {

	private Field<?> field = null;
	private ListStore<?> store = null;

	public FieldStoreLinkData(Object parent, Object child) {
		super(parent, child);

		if (getChild() instanceof Field<?>)
			field = getChild();
		else if (getChild() instanceof Store<?>)
			store = getChild();

		if (getParent() instanceof Field<?>)
			field = getParent();
		else if (getParent() instanceof Store<?>)
			store = getParent();

		// getField().addListener(Events.Valid, new Listener<FieldEvent>() {
		// @Override
		// public void handleEvent(FieldEvent be) {
		//
		// }
		// });
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

}
