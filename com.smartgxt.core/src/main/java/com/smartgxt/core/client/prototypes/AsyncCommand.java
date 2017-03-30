package com.smartgxt.core.client.prototypes;

import com.google.gwt.user.client.Command;

/**
 * @author Anton Alexeyev
 * 
 */
public abstract class AsyncCommand implements Command {

	private Object object;

	public <X> X setObject(X object) {
		this.object = object;
		return object;
	}

	@SuppressWarnings("unchecked")
	public <X> X getObject() {
		return (X) object;
	}

}
