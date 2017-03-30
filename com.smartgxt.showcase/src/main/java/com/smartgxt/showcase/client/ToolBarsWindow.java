package com.smartgxt.showcase.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.sencha.gxt.core.client.resources.ThemeStyles;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.FramedPanel.FramedPanelAppearance;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.ToggleButton;
import com.sencha.gxt.widget.core.client.container.NorthSouthContainer;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;
import com.smartgxt.core.client.prototypes.Prototypeble;
import com.smartgxt.ui.client.desktop.ui.taskbar.IsTaskBarWindow;

@Prototypeble(deffered=true)
public class ToolBarsWindow extends Window implements IsTaskBarWindow {

	public ToolBarsWindow() {
		setHeadingText("ToolBars Window");
		ContentPanel cp = new ContentPanel(
				GWT.<ContentPanelAppearance> create(FramedPanelAppearance.class));
		cp.addStyleName("margin-10");
		cp.setPixelSize(500, 400);
		cp.getBody().getStyle().setBackgroundColor("white");
		cp.getBody().addClassName(ThemeStyles.getStyle().border());

		NorthSouthContainer con = new NorthSouthContainer();

		ToggleGroup group = new ToggleGroup();

		for (int i = 1; i < 6; i++) {
			final ToggleButton btn = new ToggleButton("Button" + i);
			btn.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

				@Override
				public void onValueChange(ValueChangeEvent<Boolean> event) {

				}
			});

			group.add(btn);
			cp.addButton(btn);
		}

		ToggleButton normal = (ToggleButton) cp.getButtonBar().getWidget(0);
		normal.setValue(true, true);

		ToolBar tb = new ToolBar();
		con.setSouthWidget(tb);

		ToggleGroup group1 = new ToggleGroup();
		for (int i = 1; i < 6; i++) {
			final ToggleButton btn = new ToggleButton("Button" + i);
			btn.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

				@Override
				public void onValueChange(ValueChangeEvent<Boolean> event) {

				}
			});

			group1.add(btn);
			tb.add(btn);
		}

		cp.add(con);
		add(cp);
	}

	public ToolBarsWindow(WindowAppearance appearance) {
		super(appearance);
	}

}
