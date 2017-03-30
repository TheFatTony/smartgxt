package com.smartgxt.shared.security;

import com.extjs.gxt.ui.client.data.BaseModelData;

/**
 * @author Anton Alexeyev
 * 
 */
@SuppressWarnings("serial")
public class UIObjectConfig extends BaseModelData {

	public UIObjectConfig() {

	}

	public UIObjectConfig(String id, String className) {
		setId(id);
		setClassName(className);
	}

	public void setClassName(String clazz) {
		set("className", clazz);
	}

	public String getClassName() {
		return get("className");
	}

	public void setId(String id) {
		set("id", id);
	}

	public String getId() {
		return get("id");
	}

}
