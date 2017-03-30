package com.smartgxt.ui.client.grids;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Event;
import com.sencha.gxt.core.client.util.TextMetrics;
import com.smartgxt.ui.client.tips.SimpleToolTip;

public class GridView<M> extends
		com.sencha.gxt.widget.core.client.grid.GridView<M> {

	protected SimpleToolTip toolTip;

	// private currentCellText;

	public GridView() {
		toolTip = new SimpleToolTip();
	}

	public GridView(
			com.sencha.gxt.widget.core.client.grid.GridView.GridAppearance appearance) {
		super(appearance);
		toolTip = new SimpleToolTip();
	}

	@Override
	protected void handleComponentEvent(final Event event) {
		super.handleComponentEvent(event);

		final Element cell = Element.is(event.getEventTarget()) ? findCell((Element) event
				.getEventTarget().cast()) : null;

		switch (event.getTypeInt()) {
		case Event.ONMOUSEMOVE:
			if (toolTip != null && toolTip.isVisible())
				toolTip.setPosition(event.getClientX() + 5,
						event.getClientY() + 5);
			break;
		case Event.ONMOUSEOVER:
			if (cell != null) {
				if (TextMetrics.get().getWidth(cell.getInnerText()) > cell
						.getClientWidth()) {
					toolTip.setBodyHtml(cell.getInnerText());
					toolTip.showAt(event.getClientX() + 5,
							event.getClientY() + 5);

				}
			}
			break;
		case Event.ONMOUSEOUT:

			if (toolTip != null && toolTip.isVisible())
				toolTip.hide();
			break;
		}

	}
}
