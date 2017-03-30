package com.smartgxt.client.link.base;

import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.form.Field;

public class UniversalLink {

	private IsBaseLink link;

	public UniversalLink(Object parent, Object child) {
		// TODO array with smartgxt create
		if (((parent instanceof Field<?>) || (parent instanceof ListStore<?>))
				&& ((parent instanceof Field<?>) || (parent instanceof ListStore<?>)))
			link = new FiledStoreLink(new FieldStoreLinkData(parent, child));

	}

	public void execute() {
		link.execute();
	}

}
