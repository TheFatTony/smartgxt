package com.smartgxt.client.ui.widgets;

import com.google.gwt.dom.client.Element;
import com.google.gwt.resources.client.ImageResource;

/**
 * @author Anton Alexeyev
 * 
 */
public class Image extends com.google.gwt.user.client.ui.Image {

	public Image() {
	}

	public Image(ImageResource resource) {
		super(resource);
	}

	public Image(String url) {
		super(url);
		// TODO Auto-generated constructor stub
	}

	public Image(Element element) {
		super(element);
	}

	public Image(String url, int left, int top, int width, int height) {
		super(url, left, top, width, height);
	}

}
