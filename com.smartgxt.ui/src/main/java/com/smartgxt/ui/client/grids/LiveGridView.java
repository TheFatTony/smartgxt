package com.smartgxt.ui.client.grids;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.sencha.gxt.core.client.util.TextMetrics;
import com.sencha.gxt.widget.core.client.tips.Tip;

public class LiveGridView<M> extends
		com.sencha.gxt.widget.core.client.grid.LiveGridView<M> {


	public LiveGridView() {
	}

	@Override
	protected void handleComponentEvent(final Event event) {
		super.handleComponentEvent(event);

	}

}
