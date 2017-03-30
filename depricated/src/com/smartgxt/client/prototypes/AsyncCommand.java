package com.smartgxt.client.prototypes;

import com.google.gwt.user.client.Command;

/**
 * @author Anton Alexeyev
 * 
 */
public abstract class AsyncCommand implements Command {

	private Object object;

	public void setObject(Object object) {
		this.object = object;
	}

	public Object getObject() {
		return object;
	}

}
