package com.smartgxt.constructor.client.menus;

import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.smartgxt.client.prototypes.Prototypeble;
import com.smartgxt.constructor.client.Constructor;
import com.smartgxt.constructor.client.HasConfiguration;
import com.smartgxt.constructor.shared.MenuBarItemConfig;

/**
 * @author Anton Alexeyev
 * 
 */
@Prototypeble
public class MetaDataMenuBarItem extends
		com.smartgxt.client.ui.widgets.menus.MenuBarItem implements
		HasConfiguration<MenuBarItemConfig> {

	public MetaDataMenuBarItem() {
		super("empty", new Menu());
	}

	public void setMetaDataCode(String metaDataCode) {
		Constructor.applyConfiguration(metaDataCode, this);
	}

	public void setConfig(MenuBarItemConfig config) {
		setText(config.getText());
	}

	@Override
	public void addChildFor(HasConfiguration component) {
		getMenu().add((Component) component);
	}

}
