package com.smartgxt.client.link.base.new2;

import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.form.Field;

public class UniversalLink {

	private BaseLink link;

	public UniversalLink(Object parent, Object child) {
		// TODO array with smartgxt create
		if (((parent instanceof Field<?>) || (parent instanceof ListStore<?>))
				&& ((parent instanceof Field<?>) || (parent instanceof ListStore<?>)))
			link = new FiledStoreLink(parent, child);

	}

	public void execute() {
		link.execute();
	}

}
