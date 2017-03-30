package com.smartgxt.server;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author Anton Alexeyev
 * 
 */
@Deprecated
public class SessionsListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		System.out.println("sessionCreated jsessionid = "
				+ arg0.getSession().getId());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		System.out.println("sessionDestroyed jsessionid = "
				+ arg0.getSession().getId());
	}

}
