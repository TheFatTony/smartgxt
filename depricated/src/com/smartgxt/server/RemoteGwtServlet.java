package com.smartgxt.server;

import java.io.Serializable;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.smartgxt.server.base.configs.ConfigLoader;
import com.smartgxt.server.base.configs.Configuration;
import com.smartgxt.server.base.executers.RunManager;
import com.smartgxt.shared.events.RequestType;
import com.smartgxt.shared.exceptions.AccessDeniedException;
import com.smartgxt.shared.exceptions.BaseGwtServletException;

/**
 * @author Anton Alexeyev
 * 
 */
@SuppressWarnings("serial")
public class RemoteGwtServlet extends RemoteServiceServlet implements
		com.smartgxt.service.RemoteService {

	private static RemoteGwtServlet instance;
	private static Configuration configuration;
	private Throwable hasErrors;

	public RemoteGwtServlet() {
		super();
		instance = this;
	}

	@Override
	public void init(ServletConfig config) {
		try {
			super.init(config);
		} catch (ServletException e1) {
			hasErrors = e1;
		}

		if (configuration == null) {
			try {
				configuration = ConfigLoader.load(config);
			} catch (Throwable e) {
				hasErrors = e;
			}
		}
	}

	public void setSessionId(String sessionId) {
		this.getThreadLocalRequest().setAttribute("sgxt.sessionId", sessionId);
	}

	public String getSessionId() {
		return String.valueOf(this.getThreadLocalRequest().getAttribute(
				"sgxt.sessionId"));
	}

	public static RemoteGwtServlet get() {
		return instance;
	}

	public static Configuration getConfiguration() {
		return configuration;
	}

	@Override
	public Serializable doRpcCall(RequestType type, Serializable request)
			throws Throwable {

		Serializable response = null;
		try {
			hasErrors();
			response = (Serializable) RunManager.processRequest(type, request);
		} catch (Throwable e) {
			// TODO logging
			e.printStackTrace();
			// try {
			// RPC.getDefaultSerializationPolicy().validateDeserialize(
			// e.getClass());
			// RPC.getDefaultSerializationPolicy().validateSerialize(
			// e.getClass());
			// } catch (Throwable e1) {
			// System.out.println("SerializationException!!!!!!!!!!!!!");
			// throw new BaseGwtServletException(e1.getMessage());
			// }
			// System.out.println("?????????????!!!!!!!!!!!!!");
			if (e instanceof IsSerializable) {
				throw e;
			} else {
				throw new BaseGwtServletException(e);
			}
		}
		return response;

	}

	public void setHasErrors(Throwable hasErrors) {
		this.hasErrors = hasErrors;
	}

	private void hasErrors() throws Throwable {
		if (hasErrors != null)
			throw hasErrors;
	}

}
