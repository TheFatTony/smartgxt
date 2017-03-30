package com.smartgxt.core.server.messages;

import java.io.IOException;
import java.util.HashMap;

import com.smartgxt.core.server.i18n.GWTI18N;
import com.smartgxt.core.server.sessions.SessionManager;


/**
 * @author Anton Alexeyev
 * 
 */
public class Localization extends LocalizationPrototype<XMessages> {

	private static LocalizationPrototype<XMessages> instance;
	private static String defaultLanguage = "en";

	public Localization() {
		super();
		resources = new HashMap<String, XMessages>();
		try {
			resources.put("ru", GWTI18N.create(XMessages.class, "ru"));
			resources.put("en", GWTI18N.create(XMessages.class, "en"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO localizations list to config file
	}

	public static XMessages get() {
		if (instance == null)
			instance = new Localization();
		if (SessionManager.get().getSession() != null)
			return instance.getResource(SessionManager.get().getSession()
					.getLanguage());
		else
			return instance.getResource(defaultLanguage);
	}

}
