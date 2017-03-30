package com.smartgxt.constructor.shared;

import java.util.ArrayList;

/**
 * @author Anton Alexeyev
 * 
 */
@SuppressWarnings("serial")
public class MenuItemConfig extends GXTComponentConfig {

	private String text;
	private String icon;
	private ArrayList<MenuItemConfig> subMenuItems;

	public MenuItemConfig() {
	}

	public MenuItemConfig(String text, String icon) {
		this.text = text;
		this.icon = icon;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIcon() {
		return icon;
	}

	public void setSubMenuItems(ArrayList<MenuItemConfig> subMenuItems) {
		this.subMenuItems = subMenuItems;
	}

	public ArrayList<MenuItemConfig> getSubMenuItems() {
		return subMenuItems;
	}

}
