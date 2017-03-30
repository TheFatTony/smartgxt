package com.smartgxt.constructor.client;

import java.util.HashMap;
import java.util.Map;

import com.extjs.gxt.ui.client.data.BaseLoader;
import com.extjs.gxt.ui.client.data.BaseTreeModel;
import com.extjs.gxt.ui.client.data.LoadEvent;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.smartgxt.client.core.SmartGXT;
import com.smartgxt.client.data.RpcLoader;
import com.smartgxt.constructor.client.menus.MetaDataMenuItem;
import com.smartgxt.constructor.shared.GXTComponentConfig;
import com.smartgxt.constructor.shared.MenuItemConfig;

/**
 * @author Anton Alexeyev
 * 
 */
public class Constructor {

	private static Map<String, Class<? extends HasConfiguration<?>>> availableConfigurators;

	static {
		availableConfigurators = new HashMap<String, Class<? extends HasConfiguration<?>>>();
		addAvailableConfigurators("UI_MENU_ITEM_T", MetaDataMenuItem.class);
	}

	public Constructor() {
	}

	public static void applyConfiguration(String metaDataCode,
			final HasConfiguration component) {
		final MessageBox box = MessageBox.progress("Подождите пожалуйста",
				"Загрузка...", "Загрузка...");
		RpcLoader<BaseTreeModel> data = new RpcLoader<BaseTreeModel>(
				"com.smartgxt.constructor.server.GetObjectConfig");
		data.addListener(BaseLoader.Load, new Listener<LoadEvent>() {

			@Override
			public void handleEvent(LoadEvent be) {
				// MenuBarItemConfig config = be.getData();
				// MetaDataMenuBarItem.this.setText(config.getText());
				BaseTreeModel config = be.getData();
				component.setConfig((GXTComponentConfig) config.get("object_"));
				getChilds(config, component);
				box.close();
			}
		});
		data.load(metaDataCode);
	}

	public static void getChilds(BaseTreeModel model, HasConfiguration parent) {
		for (ModelData item : model.getChildren()) {
			String objectType = item.get("object_type");

			HasConfiguration object = createConfigurator(objectType);
			object.setConfig((MenuItemConfig) item.get("object_"));
			parent.addChildFor(object);
			getChilds((BaseTreeModel) item, object);
		}
	}

	public static Map<String, Class<? extends HasConfiguration<?>>> getAvailableConfigurators() {
		return availableConfigurators;
	}

	public static HasConfiguration createConfigurator(String type) {
		Class<? extends HasConfiguration<?>> class_ = getAvailableConfigurator(type);
		return SmartGXT.create(class_);
	}

	public static Class<? extends HasConfiguration<?>> getAvailableConfigurator(
			String type) {
		return availableConfigurators.get(type);
	}

	public static void addAvailableConfigurators(String type,
			Class<? extends HasConfiguration<?>> configurator) {
		availableConfigurators.put(type, configurator);
	}
}