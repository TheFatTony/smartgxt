package com.smartgxt.server.db.oracle;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import com.smartgxt.constructor.server.GetObjectConfig;
import com.smartgxt.server.base.configs.Configuration;
import com.smartgxt.server.base.executers.RunManager;
import com.smartgxt.server.base.sessions.ConnectEvent;
import com.smartgxt.server.base.sessions.ConnectListener;
import com.smartgxt.server.base.sessions.SessionManager;
import com.smartgxt.server.db.oracle.executer.seeded.ConnectExecuter;
import com.smartgxt.server.db.oracle.executer.seeded.DisconnectExecuter;
import com.smartgxt.server.db.oracle.executer.seeded.LoginExecuter;
import com.smartgxt.server.push.LongPollExecuter;
import com.smartgxt.server.push.ShortPollExecuter;
import com.smartgxt.server.security.SecurityRules;
import com.smartgxt.shared.events.RpcEvents;

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
		RunManager.addExecutor(RpcEvents.Disconnect, DisconnectExecuter.class);
		RunManager.addExecutor(RpcEvents.LongPoll, LongPollExecuter.class);
		RunManager.addExecutor(RpcEvents.ShortPoll, ShortPollExecuter.class);
		setSessionClass(OracleSession.class);

		SecurityRules.addOmitedServerClass(ConnectExecuter.class);
		SecurityRules.addOmitedServerClass(LoginExecuter.class);
		SecurityRules.addOmitedServerClass(DisconnectExecuter.class);

		SessionManager.get().addConnectListener(new ConnectListener() {
			@Override
			public void handleEvent(ConnectEvent evt) {
				evt.getSession().getSecurityRules()
						.addAllowedClass(GetObjectConfig.class);
				evt.getSession().getSecurityRules()
						.addAllowedClass(LongPollExecuter.class);
				evt.getSession().getSecurityRules()
						.addAllowedClass(ShortPollExecuter.class);
			}
		});
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
