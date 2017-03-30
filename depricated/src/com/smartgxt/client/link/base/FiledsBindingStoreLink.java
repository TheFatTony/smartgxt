package com.smartgxt.client.link.base;

import com.extjs.gxt.ui.client.data.LoadEvent;
import com.extjs.gxt.ui.client.data.Loader;
import com.extjs.gxt.ui.client.event.Listener;
import com.smartgxt.client.ui.widgets.grids.filters.GidFiltersHelper;

public class FiledsBindingStoreLink<D extends FiledsBindingLinkData> extends
		BaseLink<D> {

	public FiledsBindingStoreLink(D linkData) {
		super(linkData);

	}

	@Override
	public void onExecute() {
		getLinkData().getStore().getLoader()
				.addListener(Loader.BeforeLoad, new Listener<LoadEvent>() {
					@Override
					public void handleEvent(LoadEvent be) {
						GidFiltersHelper.addFilter(getLinkData().getStore(),
								getLinkData().getBindings().getFildsModel());
					}
				});
	}

}
