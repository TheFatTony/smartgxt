package com.smartgxt.client.ui.widgets.desktop;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.button.ToggleButton;
import com.extjs.gxt.ui.client.widget.menu.CheckMenuItem;
import com.extjs.gxt.ui.client.widget.menu.Menu;

/**
 * @author Anton Alexeyev
 * 
 */
public class TaskBarLayout extends
		com.extjs.gxt.ui.client.widget.layout.ToolBarLayout {

	public TaskBarLayout() {

	}

	@Override
	protected void addComponentToMenu(Menu menu, Component c) {
		// super.addComponentToMenu(menu, c);
		if (c instanceof ToggleButton) {
			final ToggleButton b = (ToggleButton) c;
			CheckMenuItem item = new CheckMenuItem(b.getText());
			// item.setIcon(b.getIcon());
			item.setItemId(c.getItemId());
			item.setGroup("taskbarRadioGroup");

			if (b.isPressed())
				item.setChecked(true);

			if (b.getData("gxt-menutext") != null) {
				item.setText(b.getData("gxt-menutext").toString());
			}
			if (b.getMenu() != null) {
				item.setHideOnClick(false);
				item.setSubMenu(b.getMenu());
			}
			item.setEnabled(c.isEnabled());
			item.addSelectionListener(new SelectionListener<MenuEvent>() {

				@Override
				public void componentSelected(MenuEvent ce) {
					b.toggle();
					ButtonEvent e = new ButtonEvent(b);
					e.setEvent(ce.getEvent());
					b.fireEvent(Events.Select, e);
				}

			});
			menu.add(item);
		}
	}

	@Override
	protected void initMore() {
		super.initMore();
	}
}
