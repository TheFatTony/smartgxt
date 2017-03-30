package com.smartgxt.core.client.messages;

import com.google.gwt.core.client.GWT;

/**
 * @author Anton Alexeyev
 * 
 */
public class Localization extends LocalizationPrototype<XMessages> {

	private static LocalizationPrototype<XMessages> instance;
	private static XMessages messages = (XMessages) GWT.create(XMessages.class);

	public Localization() {
		super(messages);
	}

	public static XMessages get() {
		if (instance == null)
			instance = new Localization();
		return instance.getResource();
	}

}
