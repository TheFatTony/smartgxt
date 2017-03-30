package com.smartgxt.shared.data;

import com.extjs.gxt.ui.client.data.BaseModelData;

/**
 * @author Anton Alexeyev
 * 
 */
@SuppressWarnings("serial")
public class ComponentDefenition extends BaseModelData {

	public ComponentDefenition() {

	}

	public ComponentDefenition(String id, String className) {
		setId(id);
		setClassName(className);
	}

	public void setClassName(String className) {
		set("className", className);
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
