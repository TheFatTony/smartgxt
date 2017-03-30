package com.smartgxt.client.messages;

import com.google.gwt.i18n.client.Localizable;

/**
 * @author Anton Alexeyev
 * 
 */
public class LocalizationPrototype<T extends Localizable> {

	private T resource;

	public LocalizationPrototype(T resource) {
		setResource(resource);
	}

	public void setResource(T resource) {
		this.resource = resource;
	}

	public T getResource() {
		return resource;
	}

}
