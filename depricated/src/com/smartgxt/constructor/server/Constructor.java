package com.smartgxt.constructor.server;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import oracle.sql.STRUCT;

import com.smartgxt.constructor.server.configurators.GXTComponentConfigurator;
import com.smartgxt.constructor.server.configurators.MenuBarItemConfigurator;
import com.smartgxt.constructor.server.configurators.MenuItemConfigurator;
import com.smartgxt.constructor.shared.GXTComponentConfig;
import com.smartgxt.constructor.shared.MenuBarItemConfig;
import com.smartgxt.constructor.shared.MenuItemConfig;

/**
 * @author Anton Alexeyev
 * 
 */
public class Constructor {

	private static Map<String, Class<? extends GXTComponentConfig>> availableConfigs;
	private static Map<String, GXTComponentConfigurator<? extends GXTComponentConfig>> availableConfigurators;

	static {
		availableConfigs = new HashMap<String, Class<? extends GXTComponentConfig>>();
		availableConfigurators = new HashMap<String, GXTComponentConfigurator<?>>();

		// TODO migrate it to config class
		addAvailableConfig("UI_MENU_BAR_ITEM_T", MenuBarItemConfig.class,
				new MenuBarItemConfigurator());
		addAvailableConfig("UI_MENU_ITEM_T", MenuItemConfig.class,
				new MenuItemConfigurator());
	}

	public Constructor() {
	}

	public static Class<? extends GXTComponentConfig> getAvailableConfig(
			String type) {
		return availableConfigs.get(type);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static GXTComponentConfig applyConfiguration(String type,
			STRUCT struct) throws SQLException, InstantiationException,
			IllegalAccessException {
		Class<? extends GXTComponentConfig> class_ = Constructor
				.getAvailableConfig(type);
		GXTComponentConfig config = class_.newInstance();

		GXTComponentConfigurator configurator = availableConfigurators
				.get(type);
		configurator.setOracleObject(config, struct);
		return config;
	}

	public static void addAvailableConfig(String type,
			Class<? extends GXTComponentConfig> class_,
			GXTComponentConfigurator<? extends GXTComponentConfig> configurator) {
		availableConfigs.put(type, class_);
		availableConfigurators.put(type, configurator);
	}

	public static Map<String, Class<? extends GXTComponentConfig>> getAvailableConfigs() {
		return availableConfigs;
	}

}
