package com.smartgxt.server.base.configs;

import java.io.InputStream;
import java.security.CodeSource;
import java.util.jar.Manifest;

import javax.servlet.ServletConfig;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

/**
 * @author Anton Alexeyev
 * 
 */
public class ConfigLoader {

	public static Configuration load(ServletConfig config) throws Throwable {
		Document xmlConfig;
		String appName;
		String appVersion;
		String location;

		InputStream manifestStream = config.getServletContext()
				.getResourceAsStream("/META-INF/MANIFEST.MF");
		Manifest manifest = null;
		if (manifestStream != null) {
			manifest = new Manifest(manifestStream);
			appVersion = manifest.getMainAttributes().getValue(
					"Implementation-Version");
			appName = manifest.getMainAttributes().getValue("App-Name");
		} else {
			appVersion = "@version@";
			appName = "@name@";
		}

		Object result = null;

		DocumentBuilderFactory domFactory = DocumentBuilderFactory
				.newInstance();
		// domFactory.setNamespaceAware(true);
		DocumentBuilder builder = null;

		builder = domFactory.newDocumentBuilder();
		xmlConfig = builder.parse(appName + "/config.xml");

		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		XPathExpression expr = xpath
				.compile("/config/appication/configuration/text()");
		result = expr.evaluate(xmlConfig, XPathConstants.STRING);

		Class<?> optionsClass = Class.forName(result.toString());
		Configuration configuration = (Configuration) optionsClass
				.newInstance();
		CodeSource cSource = optionsClass.getProtectionDomain().getCodeSource();
		String loc = cSource.getLocation().toString();

		location = loc.substring("file:/".length(), loc.indexOf("WEB-INF"));

		configuration.setConfig(xmlConfig);
		Configuration.setAppName(appName);
		Configuration.setAppVersion(appVersion);
		Configuration.setLocation(location);
		configuration.create();
		configuration.init();
		return configuration;
	}
}
