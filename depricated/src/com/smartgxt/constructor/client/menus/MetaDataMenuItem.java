package com.smartgxt.constructor.client.menus;

import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.smartgxt.client.prototypes.Prototypeble;
import com.smartgxt.client.ui.widgets.menus.MenuItem;
import com.smartgxt.constructor.client.Constructor;
import com.smartgxt.constructor.client.HasConfiguration;
import com.smartgxt.constructor.shared.MenuItemConfig;

/**
 * @author Anton Alexeyev
 * 
 */
@Prototypeble
public class MetaDataMenuItem extends MenuItem implements
		HasConfiguration<MenuItemConfig> {

	public MetaDataMenuItem() {
	}

	public void setConfig(MenuItemConfig config) {
		setText(config.getText());
	}

	@Override
	public void setMetaDataCode(String metaDataCode) {
		Constructor.applyConfiguration(metaDataCode, this);
	}

	@Override
	public void addChildFor(HasConfiguration component) {
		if (getSubMenu() == null)
			setSubMenu(new Menu());

		getSubMenu().add((Component) component);
	}

}
