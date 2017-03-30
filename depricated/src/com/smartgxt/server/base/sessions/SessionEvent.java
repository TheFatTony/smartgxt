package com.smartgxt.server.base.sessions;

import java.util.EventObject;

/**
 * @author Anton Alexeyev
 * 
 */
@SuppressWarnings("serial")
public class SessionEvent extends EventObject {

	private GwtSession session;

	public SessionEvent(Object source) {
		super(source);
	}

	public void setSession(GwtSession session) {
		this.session = session;
	}

	public GwtSession getSession() {
		return session;
	}

}
