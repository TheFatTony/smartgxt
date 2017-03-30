package com.smartgxt.server.base.sessions;

import java.util.EventListener;

/**
 * @author Anton Alexeyev
 * 
 */
public interface ConnectListener extends EventListener {
	public void handleEvent(ConnectEvent evt);
}
