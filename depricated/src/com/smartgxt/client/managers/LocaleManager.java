package com.smartgxt.client.managers;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anton Alexeyev
 * 
 */
public class LocaleManager {

	private static List<Locale> locales = new ArrayList<Locale>();

	static {
		register(Locale.ENGLISH);
	}

	public LocaleManager() {
	}

	/**
	 * Returns all registered locales.
	 * 
	 * @return the locales
	 */
	public static List<Locale> getLocales() {
		return locales;
	}

	/**
	 * Registers a locale.
	 * 
	 * @param locale
	 *            to register.
	 */
	public static void register(Locale locale) {
		locales.add(locale);
	}
}
