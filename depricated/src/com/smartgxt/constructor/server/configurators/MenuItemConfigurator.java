package com.smartgxt.constructor.server.configurators;

import java.sql.SQLException;

import oracle.sql.STRUCT;

import com.smartgxt.constructor.shared.MenuItemConfig;

/**
 * @author Anton Alexeyev
 * 
 */
public class MenuItemConfigurator extends
		GXTComponentConfigurator<MenuItemConfig> {

	public MenuItemConfigurator() {
	}

	@Override
	public void setOracleObject(MenuItemConfig object, STRUCT struct)
			throws SQLException {
		Object[] attrs = struct.getAttributes();
		object.setText(String.valueOf(attrs[2]));
	}

}
