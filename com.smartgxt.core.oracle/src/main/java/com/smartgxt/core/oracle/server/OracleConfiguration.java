package com.smartgxt.core.oracle.server;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;


import com.smartgxt.core.oracle.server.executor.seeded.ConnectExecuter;
import com.smartgxt.core.oracle.server.executor.seeded.DisconnectExecuter;
import com.smartgxt.core.oracle.server.executor.seeded.LoginExecuter;
import com.smartgxt.core.server.config.Configuration;
import com.smartgxt.core.server.executers.RunManager;
import com.smartgxt.core.server.security.SecurityRules;
import com.smartgxt.core.shared.events.RpcEvents;

/**
 * @author Anton Alexeyev
 * 
 */
public class OracleConfiguration extends Configuration {
	static private String url;
	static private String urlName;

	public OracleConfiguration() {
		super();

	}

	public void create() throws XPathExpressionException {
		super.create();
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		XPathExpression expr;
		Object result;

		expr = xpath.compile("/config/oracle/@default");
		result = expr.evaluate(getConfig(), XPathConstants.STRING);
		setUrlName(result.toString());
		System.out.println("urlName = " + urlName);

		expr = xpath
				.compile("/config/oracle/connection[@connectionName=/config/oracle/@default]/text()");
		result = expr.evaluate(getConfig(), XPathConstants.STRING);
		setUrl(result.toString());

	}

	public void init() {
		super.init();
		 RunManager.addExecutor(RpcEvents.Connect, ConnectExecuter.class);
		 RunManager.addExecutor(RpcEvents.Login, LoginExecuter.class);
		 RunManager.addExecutor(RpcEvents.Disconnect,
		 DisconnectExecuter.class);

		 setSessionClass(OracleSession.class);

		 SecurityRules.addOmitedServerClass(ConnectExecuter.class);
		 SecurityRules.addOmitedServerClass(LoginExecuter.class);
		 SecurityRules.addOmitedServerClass(DisconnectExecuter.class);

	}

	public static void setUrl(String arg0) {
		url = arg0;
	}

	public static String getUrl() {
		return url;
	}

	public static void setUrlName(String urlName) {
		OracleConfiguration.urlName = urlName;
	}

	public static String getUrlName() {
		return urlName;
	}

}
