package com.smartgxt.core.server.executers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smartgxt.core.server.sessions.GwtSession;

/**
 * @author Anton Alexeyev
 * 
 */
public abstract class HttpRequestExecuter<T extends GwtSession> extends
		BaseRequestExecuter<T, HttpServletRequest, HttpServletResponse> {
	public HttpRequestExecuter() {
	}

	public String getRequestProperty(String property) {
		return getRequest().getParameter(property);
	}

}
