package com.smartgxt.constructor.shared;

/**
 * @author Anton Alexeyev
 * 
 */
@SuppressWarnings("serial")
public class MenuBarItemConfig extends GXTComponentConfig {

	private String text;

	public MenuBarItemConfig() {

	}

	public MenuBarItemConfig(String text) {
		this.text = text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

}
