package com.smartgxt.core.server.config;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

import com.smartgxt.core.server.executers.RunManager;
import com.smartgxt.core.server.executers.seeded.ConnectExecuter;
import com.smartgxt.core.server.executers.seeded.DisconnectExecuter;
import com.smartgxt.core.server.executers.seeded.DownloadFileExecuter;
import com.smartgxt.core.server.executers.seeded.LoginExecuter;
import com.smartgxt.core.server.executers.seeded.UploadFileExecuter;
import com.smartgxt.core.server.security.SecurityRules;
import com.smartgxt.core.server.sessions.GwtSession;
import com.smartgxt.core.server.sessions.LoginEvent;
import com.smartgxt.core.server.sessions.LoginListener;
import com.smartgxt.core.server.sessions.SessionManager;
import com.smartgxt.core.shared.events.RpcEvents;

/**
 * @author Anton Alexeyev
 * 
 */
public class Configuration {

	private Class<? extends GwtSession> sessionClass = GwtSession.class;
	private Document xmlConfig;

	static private String logDir;
	static private int maxActiveThreads;
	static private int maxThreadsWait;
	static private int buffersSize;
	static private int threadsWaitTime;
	static private int sessionTimeout;
	static private int killTaskInterval;

	static private String appName;
	static private String appVersion;
	static private String location;

	static private boolean regenerateSessionId;

	public Configuration() {

	}

	public void create() throws XPathExpressionException {
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		XPathExpression expr;

		String pos1 = null;
		expr = xpath.compile("/config/appication/log_dir/text()");
		pos1 = expr.evaluate(getConfig(), XPathConstants.STRING).toString();
		setLogDir(pos1);

		Integer pos2 = null;
		expr = xpath.compile("/config/appication/max_active_threads/text()");
		pos2 = Integer.parseInt(expr.evaluate(getConfig(),
				XPathConstants.STRING).toString());
		setMaxActiveThreads(pos2);

		Integer pos3 = null;
		expr = xpath.compile("/config/appication/max_threads_wait/text()");
		pos3 = Integer.parseInt(expr.evaluate(getConfig(),
				XPathConstants.STRING).toString());
		setMaxThreadsWait(pos3);

		Integer pos4 = null;
		expr = xpath.compile("/config/appication/buffers_size/text()");
		pos4 = Integer.parseInt(expr.evaluate(getConfig(),
				XPathConstants.STRING).toString());
		setBuffersSize(pos4);

		Integer pos5 = null;
		expr = xpath.compile("/config/appication/threads_wait_time/text()");
		pos5 = Integer.parseInt(expr.evaluate(getConfig(),
				XPathConstants.STRING).toString());
		setThreadsWaitTime(pos5);

		Integer pos6 = null;
		expr = xpath.compile("/config/appication/session_timeout/text()");
		pos6 = Integer.parseInt(expr.evaluate(getConfig(),
				XPathConstants.STRING).toString());
		setSessionTimeout(pos6);

		Integer pos7 = null;
		expr = xpath.compile("/config/appication/kill_task_interval/text()");
		pos7 = Integer.parseInt(expr.evaluate(getConfig(),
				XPathConstants.STRING).toString());
		setKillTaskInterval(pos7);

		Boolean pos8 = null;
		expr = xpath.compile("/config/appication/kill_task_interval/text()");
		pos8 = Boolean.valueOf(expr
				.evaluate(getConfig(), XPathConstants.STRING).toString());
		setRegenerateSessionId(pos8);

	}

	public void init() {

		RunManager.addExecutor(RpcEvents.Connect, ConnectExecuter.class);
		RunManager.addExecutor(RpcEvents.Login, LoginExecuter.class);
		RunManager.addExecutor(RpcEvents.Disconnect, DisconnectExecuter.class);

		RunManager.addExecutor(RpcEvents.Download, DownloadFileExecuter.class);
		RunManager.addExecutor(RpcEvents.Upload, UploadFileExecuter.class);

		SecurityRules.addOmitedServerClass(ConnectExecuter.class);
		SecurityRules.addOmitedServerClass(DisconnectExecuter.class);
		SecurityRules.addOmitedServerClass(LoginExecuter.class);

		SessionManager.get().addLoginListener(new LoginListener() {
			@Override
			public void handleEvent(LoginEvent evt) {
				SecurityRules sr = SecurityRules.getSecurityRules(evt
						.getSession());
				sr.addAllowedClass(DownloadFileExecuter.class);
				sr.addAllowedClass(UploadFileExecuter.class);
			}
		});

	}

	protected static void setThreadsWaitTime(int threadsWaitTime) {
		Configuration.threadsWaitTime = threadsWaitTime;
	}

	public static int getThreadsWaitTime() {
		return threadsWaitTime;
	}

	protected static void setMaxActiveThreads(int maxActiveThreads) {
		Configuration.maxActiveThreads = maxActiveThreads;
	}

	public static int getMaxActiveThreads() {
		return maxActiveThreads;
	}

	protected static void setMaxThreadsWait(int maxThreadsWait) {
		Configuration.maxThreadsWait = maxThreadsWait;
	}

	public static int getMaxThreadsWait() {
		return maxThreadsWait;
	}

	protected static void setBuffersSize(int buffersSize) {
		Configuration.buffersSize = buffersSize;
	}

	public static int getBuffersSize() {
		return buffersSize;
	}

	protected void setSessionClass(Class<? extends GwtSession> sessionClass) {
		this.sessionClass = sessionClass;
	}

	public Class<? extends GwtSession> getSessionClass() {
		return sessionClass;
	}

	protected void setConfig(Document config) {
		this.xmlConfig = config;
	}

	public Document getConfig() {
		return xmlConfig;
	}

	protected static void setLogDir(String logDir) {
		Configuration.logDir = logDir;
	}

	public static String getLogDir() {
		return logDir;
	}

	protected static void setAppName(String appName) {
		Configuration.appName = appName;
	}

	public static String getAppName() {
		return appName;
	}

	protected static void setAppVersion(String appVersion) {
		Configuration.appVersion = appVersion;
	}

	public static String getAppVersion() {
		return appVersion;
	}

	protected static void setLocation(String location) {
		Configuration.location = location;
	}

	public static String getLocation() {
		return location;
	}

	public static int getSessionTimeout() {
		return sessionTimeout;
	}

	protected static void setSessionTimeout(int sessionTimeout) {
		Configuration.sessionTimeout = sessionTimeout;
	}

	public static int getKillTaskInterval() {
		return killTaskInterval;
	}

	protected static void setKillTaskInterval(int killTaskInterval) {
		Configuration.killTaskInterval = killTaskInterval;
	}

	public static boolean isRegenerateSessionId() {
		return regenerateSessionId;
	}

	protected static void setRegenerateSessionId(boolean regenerateSessionId) {
		Configuration.regenerateSessionId = regenerateSessionId;
	}

}
