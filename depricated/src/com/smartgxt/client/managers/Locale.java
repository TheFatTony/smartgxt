package com.smartgxt.client.managers;

import java.util.Map;

import com.extjs.gxt.ui.client.core.FastMap;
import com.extjs.gxt.ui.client.data.BaseModelData;

/**
 * @author Anton Alexeyev
 * 
 */
@SuppressWarnings("serial")
public class Locale extends BaseModelData {

	/**
	 * Default English locale.
	 */
	public static Locale ENGLISH = new Locale("en", "English language",
			"American", "US");

	/**
	 * Russian locale.
	 */
	public static Locale RUSSIAN = new Locale("ru", "Русский язык", "Russian",
			"RU");

	public Locale(String id, String name, String nls, String lang) {
		set("id", id);
		set("name", name);
		set("nls", nls);
		set("lang", lang);
	}

	/**
	 * Returns the locale id.
	 * 
	 * @return the locale id
	 */
	public String getId() {
		return this.<String> get("id");
	}

	/**
	 * Returns the locale name.
	 * 
	 * @return the locale name
	 */
	public String getName() {
		return this.<String> get("name");
	}

	/**
	 * Returns the locale name.
	 * 
	 * @return the locale name
	 */
	public String getNls() {
		return this.<String> get("nls");
	}

	/**
	 * Returns the locale lang.
	 * 
	 * @return the locale lang
	 */
	public String getLang() {
		return this.<String> get("lang");
	}

	/**
	 * Returns the locale's file.
	 * 
	 * @return the file including the path
	 */
	public Map<String, Object> asMap() {
		Map<String, Object> map = new FastMap<Object>();
		map.put("id", getId());
		map.put("name", getName());
		map.put("lang", getLang());
		map.put("nls", getNls());
		return map;
	}

}
