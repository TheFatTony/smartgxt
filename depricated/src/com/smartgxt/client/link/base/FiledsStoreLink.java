package com.smartgxt.client.link.base;

import com.extjs.gxt.ui.client.data.LoadEvent;
import com.extjs.gxt.ui.client.data.Loader;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.smartgxt.client.ui.widgets.grids.filters.GidFiltersHelper;

public class FiledsStoreLink<D extends FieldsStoreLinkData> extends BaseLink<D> {

	public FiledsStoreLink(D linkData) {
		super(linkData);

	}

	@Override
	public void onExecute() {
		for (Field<?> f : getLinkData().getFields()) {
			f.addListener(Events.Valid, new Listener<FieldEvent>() {
				@Override
				public void handleEvent(FieldEvent be) {
					GidFiltersHelper.addFilter(getLinkData().getStore(),
							be.getField());
				}

			});
		}
		getLinkData().getStore().getLoader()
				.addListener(Loader.BeforeLoad, new Listener<LoadEvent>() {

					@Override
					public void handleEvent(LoadEvent be) {
						for (Field<?> f : getLinkData().getFields()) {
							f.validate();
						}
					}
				});
	}

}
