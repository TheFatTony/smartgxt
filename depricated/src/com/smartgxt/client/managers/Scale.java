package com.smartgxt.client.managers;

import java.util.Map;

import com.extjs.gxt.ui.client.core.FastMap;
import com.extjs.gxt.ui.client.data.BaseModelData;
import com.smartgxt.client.messages.Localization;

/**
 * @author Anton Alexeyev
 * 
 */
@SuppressWarnings("serial")
public class Scale extends BaseModelData {

	/**
	 * Default normal font size.
	 */
	// TODO localization
	public static Scale NORMAL = new Scale("fn", Localization.get()
			.scale_Normal());

	/**
	 * Large size.
	 */
	// TODO localization
	public static Scale LARGE = new Scale("fl", Localization.get()
			.scale_Large());

	/**
	 * Huge size.
	 */
	public static Scale HUGE = new Scale("fh", Localization.get().scale_Huge());

	public Scale(String id, String name) {
		set("id", id);
		set("name", name);
	}

	/**
	 * Returns the font size id.
	 * 
	 * @return the font size id
	 */
	public String getId() {
		return this.<String> get("id");
	}

	/**
	 * Returns the font size name.
	 * 
	 * @return the font size name
	 */
	public String getName() {
		return this.<String> get("name");
	}

	/**
	 * Returns the font size's file.
	 * 
	 * @return the file including the path
	 */
	public Map<String, Object> asMap() {
		Map<String, Object> map = new FastMap<Object>();
		map.put("id", getId());
		map.put("name", getName());
		return map;
	}

}
