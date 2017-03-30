package com.smartgxt.core.server.sessions;

import java.util.EventListener;

/**
 * @author Anton Alexeyev
 * 
 */
public interface ConnectListener extends EventListener {
	public void handleEvent(ConnectEvent evt);
}
