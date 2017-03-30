package com.smartgxt.server.base.sessions;

import java.util.EventListener;

/**
 * @author Anton Alexeyev
 * 
 */
public interface LoginListener extends EventListener {
	public void handleEvent(LoginEvent evt);
}
