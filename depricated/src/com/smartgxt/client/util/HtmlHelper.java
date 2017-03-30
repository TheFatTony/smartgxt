package com.smartgxt.client.util;

import com.google.gwt.user.client.DOM;

/**
 * @author Anton Alexeyev
 * 
 */
public class HtmlHelper {

	public static double percentToDouble(String percent) {
		double w = 0;
		if (percent.indexOf("%") != -1)
			w = 1d / (100d / Integer.parseInt(percent.substring(0,
					percent.indexOf("%"))));
		else
			assert false : "Not a percent";
		return w;
	}

	public static String doubleToPercent(double percent) {
		int w = (int) (percent * 100);
		String s = Integer.toString(w) + "%";
		return s;
	}

	public static double pixelsToDouble(String pixels) {
		double w = 0;
		if (pixels.indexOf("px") != -1)
			w = Double.parseDouble(pixels.substring(0, pixels.indexOf("px")));
		else
			assert false : "Not a pixel";
		return w;
	}

	public static String getMetaValue(String id) {
		String meta = DOM.getElementById(id).getAttribute("content");
		return meta;
	}

}
