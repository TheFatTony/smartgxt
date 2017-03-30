package com.smartgxt.constructor.server.configurators;

import java.sql.SQLException;

import com.smartgxt.constructor.shared.MenuBarItemConfig;

import oracle.sql.STRUCT;

/**
 * @author Anton Alexeyev
 * 
 */
public class MenuBarItemConfigurator extends
		GXTComponentConfigurator<MenuBarItemConfig> {

	public MenuBarItemConfigurator() {
	}

	@Override
	public void setOracleObject(MenuBarItemConfig object, STRUCT struct)
			throws SQLException {
		Object[] attrs = struct.getAttributes();
		object.setText(String.valueOf(attrs[2]));
	}

}
