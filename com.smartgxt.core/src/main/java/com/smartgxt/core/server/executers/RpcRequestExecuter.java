package com.smartgxt.core.server.executers;

import java.io.Serializable;

import com.smartgxt.core.server.sessions.GwtSession;


/**
 * @author Anton Alexeyev
 * 
 */
public abstract class RpcRequestExecuter<T extends GwtSession, Q extends Serializable, S extends Serializable>
		extends BaseRequestExecuter<T, Q, S> {

	public RpcRequestExecuter() {
	}

	public void run() {
		super.run();
		// for (String s : getResponse().getPropertyNames()) {
		// Object value = getResponse().get(s);
		// if (value instanceof StreamDefinition)
		// getResponse().set(s,
		// new FileDefenition(((StreamDefinition) value).getId()));
		// }
	}

	public abstract void execute() throws Throwable;
	// public void setRequest(Serializable req) {
	// super.setRequest(req);
	// if (req != null)
	// for (String s : req.getPropertyNames()) {
	// Object value = req.get(s);
	// if (value instanceof FileDefenition)
	// req.set(s, StreamDefinitions
	// .getStreamDefinition(((FileDefenition) value)
	// .getFileId()));
	// }
	// }
	// @SuppressWarnings("unchecked")
	// public <X> X getRequestProperty(String property) {
	// if (getRequest() == null)
	// return null;
	// Object value = getRequest().get(property);
	// if (value instanceof FileDefenition)
	// return (X) StreamDefinitions
	// .getStreamDefinition(((FileDefenition) value).getFileId());
	// else
	// return (X) value;
	// }
	// @SuppressWarnings("unchecked")
	// public <X extends Object> X setResponseProperty(String property, X value)
	// {
	// if (value instanceof StreamDefinition)
	// return (X) getResponse().set(property,
	// new FileDefenition(((StreamDefinition) value).getId()));
	// else
	// return getResponse().set(property, value);
	// };

}
