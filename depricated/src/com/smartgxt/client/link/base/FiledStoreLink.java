package com.smartgxt.client.link.base;

import com.extjs.gxt.ui.client.data.LoadEvent;
import com.extjs.gxt.ui.client.data.Loader;
import com.extjs.gxt.ui.client.event.Listener;
import com.smartgxt.client.ui.widgets.grids.filters.GidFiltersHelper;

public class FiledStoreLink<D extends FieldStoreLinkData> extends BaseLink<D> {

	public FiledStoreLink(D linkData) {
		super(linkData);
		System.out.println("FiledStoreLink.FiledStoreLink()");
		getLinkData().getStore().getLoader()
				.addListener(Loader.BeforeLoad, new Listener<LoadEvent>() {

					@Override
					public void handleEvent(LoadEvent be) {
						execute();
					}
				});
	}

	@Override
	public void onExecute() {
		System.out.println("FiledStoreLink.onExecute()");
		getLinkData().getField().validate();
		System.out.println("FiledStoreLink.onExecute() isValid() valie = "
				+ getLinkData().getField().getValue());
		GidFiltersHelper.addFilter(getLinkData().getStore(), getLinkData()
				.getField());
		endExecute();

	}
}
