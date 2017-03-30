/**
 * 
 */
package com.smartgxt.client.state;

import java.util.HashMap;
import java.util.Map;

import com.extjs.gxt.ui.client.state.Provider;
import com.extjs.gxt.ui.client.state.StateManager;
import com.google.gwt.storage.client.Storage;
import com.smartgxt.client.core.Session;

/**
 * @author Anton Alexeyev
 * 
 */

public class HtmlStorageProvider extends Provider {

	private Storage localStore;

	public HtmlStorageProvider() throws HtmlStorageException {
		super();
		localStore = Storage.getLocalStorageIfSupported();
		if (localStore == null) {
			throw new HtmlStorageException();
		}
	}

	@Override
	protected void clearKey(String name) {
		localStore.removeItem("sgxt.states." + name);
	}

	public void clearAll() {
		// localStore.clear();
		String ver = localStore.getItem("sgxt.states.version");
		if (ver == null)
			ver = "0";

		Integer version = Integer.parseInt(ver);
		version++;

		for (int i = 0; i < localStore.getLength(); i++) {
			String key = localStore.key(i);
			if (key.startsWith("sgxt.states."))
				localStore.removeItem(key);
		}

		localStore.setItem("sgxt.states.version", version.toString());
		localStore.setItem("sgxt.states.user", Session.getLogin());
	}

	public void init(Map<String, String> states) {
		clearAll();
		for (String s : states.keySet())
			localStore.setItem(s, states.get(s));
	}

	@Override
	public Map<String, Object> getMap(String name) {
		Map<String, Object> obj = super.getMap(name);
		return obj;
	}

	@Override
	protected String getValue(String name) {
		return localStore.getItem("sgxt.states." + name);
	}

	@Override
	protected void setValue(String name, String value) {
		// if (Session.getLogin() != null)
		if (!name.equals(localStore.getItem("sgxt.states." + name))) {
			String ver = localStore.getItem("sgxt.states.version");
			if (ver == null)
				ver = "0";

			Integer version = Integer.parseInt(ver);
			version++;

			localStore.setItem("sgxt.states.version", version.toString());
			localStore.setItem("sgxt.states.user", Session.getLogin());
			localStore.setItem("sgxt.states." + name, value);
		}
	}

	public Storage getLocalStore() {
		return localStore;
	}

	public HashMap<String, String> getStates() {
		HashMap<String, String> states = new HashMap<String, String>();

		Storage localStore = ((HtmlStorageProvider) StateManager.get()
				.getProvider()).getLocalStore();
		for (int i = 0; i < localStore.getLength(); i++) {
			String s = localStore.key(i);
			states.put(s, localStore.getItem(s));
		}

		return states;
	}

}
