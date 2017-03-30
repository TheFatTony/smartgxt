package com.smartgxt.core.server.sessions;

import java.util.EventListener;

/**
 * @author Anton Alexeyev
 * 
 */
public interface LoginListener extends EventListener {
	public void handleEvent(LoginEvent evt);
}
