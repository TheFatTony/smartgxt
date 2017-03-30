package com.smartgxt.server.messages;

import java.util.Map;

import com.google.gwt.i18n.client.Localizable;

/**
 * @author Anton Alexeyev
 * 
 */
public class LocalizationPrototype<T extends Localizable> {

	protected Map<String, T> resources;

	public LocalizationPrototype() {
	}

	public void addResource(String key, T resource) {
		this.resources.put(key, resource);
	}

	public T getResource(String key) {
		return resources.get(key);
	}

}
