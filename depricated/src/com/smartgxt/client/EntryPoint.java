package com.smartgxt.client;

import com.smartgxt.client.managers.CompatibilityManager;

/**
 * @author Anton Alexeyev
 * 
 */
public abstract class EntryPoint extends EntryPointPrototype {

	public EntryPoint() {
		super();
		new CompatibilityManager();
		init();
	}

}
