package com.smartgxt.core.server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smartgxt.core.server.executers.RunManager;
import com.smartgxt.core.shared.events.RequestType;
import com.smartgxt.core.shared.events.RpcEvents;


/**
 * @author Anton Alexeyev
 * 
 */
@SuppressWarnings("serial")
public class RemoteHttpServlet extends HttpServlet {

	private static RemoteHttpServlet instance;

	public RemoteHttpServlet() {
		super();
		instance = this;

	}

	@Override
	public void init() throws ServletException {
		super.init();
	}


	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		RequestType requestType = RpcEvents.Download;
		requestType.setVersion(request.getParameter("version"));
		requestType.setSessionId(request.getParameter("jsessionid"));
		try {
			RunManager.processRequest(requestType, request, response);
		} catch (Throwable e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		RequestType requestType = RpcEvents.Upload;
		requestType.setVersion(request.getParameter("version"));
		requestType.setSessionId(request.getParameter("jsessionid"));
		try {
			RunManager.processRequest(requestType, request, response);
		} catch (Throwable e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}

	public static RemoteHttpServlet get() {
		return instance;
	}

}
