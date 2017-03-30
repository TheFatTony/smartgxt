package com.smartgxt.client.managers;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anton Alexeyev
 * 
 */
public class ScaleManager {

	private static List<Scale> scales = new ArrayList<Scale>();

	static {
		register(Scale.NORMAL);
		register(Scale.LARGE);
		register(Scale.HUGE);
	}

	public ScaleManager() {
	}

	/**
	 * Returns all registered scales.
	 * 
	 * @return the scales
	 */
	public static List<Scale> getScales() {
		return scales;
	}

	/**
	 * Registers a scale.
	 * 
	 * @param locale
	 *            to register.
	 */
	public static void register(Scale scale) {
		scales.add(scale);
	}
}
