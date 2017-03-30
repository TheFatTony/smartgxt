package com.smartgxt.client.ui.widgets.layouts;

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.core.El;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.widget.BoxComponent;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.ComponentHelper;
import com.extjs.gxt.ui.client.widget.Container;
import com.extjs.gxt.ui.client.widget.ScrollContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.AbsoluteData;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.smartgxt.client.util.HtmlHelper;

/**
 * @author Anton Alexeyev
 * 
 */
// TODO refactoring
// TODO more careful scaling on big parent size
// TODO scroll sizes correction

@SuppressWarnings("deprecation")
public class AbsoluteLayout extends
		com.extjs.gxt.ui.client.widget.layout.AbsoluteLayout {

	/*
	 * container mast be stateful and mast have state id
	 */

	public AbsoluteLayout() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onAdd(final Component component) {
		super.onAdd(component);
		DeferredCommand.addCommand(new Command() {
			@Override
			public void execute() {
				if ((component instanceof BoxComponent)
						&& !(component instanceof Button)) {
					BoxComponent b = (BoxComponent) component;

					String prcW;
					if (container.getState().get("originWidth") != null) {
						prcW = HtmlHelper.doubleToPercent(((double) b
								.getWidth() / Double.parseDouble(container
								.getState().get("originWidth").toString())));
						b.setWidth((int) (container.getWidth() * HtmlHelper
								.percentToDouble(prcW)));
					} else
						prcW = HtmlHelper.doubleToPercent(((double) b
								.getWidth() / container.getWidth()));
					b.setData("sgxt.relativeWidth", prcW);
					AbsoluteData data = getAbsoluteData(component);

					if (data != null) {
						String prcL = null;
						if (container.getState().get("originWidth") != null) {
							prcL = HtmlHelper.doubleToPercent(((double) data
									.getLeft() / Double.parseDouble(container
									.getState().get("originWidth").toString())));
							data.setLeft((int) (container.getWidth() * HtmlHelper
									.percentToDouble(prcL)));
						} else
							prcL = HtmlHelper.doubleToPercent(((double) data
									.getLeft() / container.getWidth()));
						b.setData("sgxt.relativeLeft", prcL);
					}
				}
			}
		});
	}

	@Override
	public void setContainer(Container<?> ct) {
		super.setContainer(ct);
	}

	@Override
	protected void onLayout(Container<?> container, El target) {
		if (container instanceof ScrollContainer<?>)
			((ScrollContainer<?>) container).setScrollMode(Scroll.AUTOY);
		super.onLayout(container, target);
	}

	@Override
	protected void onResize(ComponentEvent ce) {

		if ((container.getState().get("originWidth") == null)) {
			container.getState().put("originWidth", container.getWidth());
			container.saveState();
		}

		for (int i = 0; i < container.getItemCount(); i++) {
			Component c = container.getItem(i);
			if ((c instanceof BoxComponent) && !(c instanceof Button)) {
				BoxComponent b = (BoxComponent) c;
				Object o = null;
				o = b.getData("sgxt.relativeWidth");
				if (o != null)
					b.setWidth((int) (container.getWidth() * HtmlHelper
							.percentToDouble(o.toString())));

				AbsoluteData data = getAbsoluteData(c);
				if (data != null) {
					o = b.getData("sgxt.relativeLeft");
					if (o != null)
						data.setLeft((int) (container.getWidth() * HtmlHelper
								.percentToDouble(o.toString())));
				}
			}
		}
		super.onResize(ce);
	}

	private AbsoluteData getAbsoluteData(Component component) {
		AbsoluteData layoutData = (AbsoluteData) component.getLayoutData();
		if (layoutData == null) {
			layoutData = (AbsoluteData) ComponentHelper
					.getLayoutData(component);
		}
		return layoutData;
	}
}
