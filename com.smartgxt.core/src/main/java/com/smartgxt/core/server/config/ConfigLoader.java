package com.smartgxt.core.server.config;

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

import org.apache.commons.io.IOUtils;
import org.apache.tools.ant.taskdefs.ManifestException;
import org.w3c.dom.Document;

import com.smartgxt.core.shared.exceptions.ManifestNotFondException;

/**
 * @author Anton Alexeyev
 * 
 */
public class ConfigLoader {

	public static Configuration load(ServletConfig config) throws Throwable {
		Document xmlConfig;
		String appName;
		String appVersion;
		String configPath;
		String location;

		// TODO temporary
		InputStream manifestStream = config.getServletContext()
				.getResourceAsStream("/META-INF/MANIFEST.MF");
		Manifest manifest = null;
		if (manifestStream != null) {
			// System.out.println(IOUtils.toString(manifestStream));
			manifest = new Manifest(manifestStream);
			// for (String s : manifest.getEntries().keySet())
			// System.out.println(s + " = " + manifest.getEntries().get(s));

			appVersion = manifest.getMainAttributes().getValue(
					"Implementation-Version");
			configPath = manifest.getMainAttributes().getValue("Config-Path");
			appName = manifest.getMainAttributes().getValue("App-Name");

			manifestStream.close();
		} else {
			throw new ManifestNotFondException("MANIFEST.MF Not found");
		}

		if (appVersion == null)
			throw new ManifestException("App-Name Not found");

		if (appName == null)
			throw new ManifestException("Implementation-Version Not found");

		Object result = null;

		DocumentBuilderFactory domFactory = DocumentBuilderFactory
				.newInstance();
		// domFactory.setNamespaceAware(true);
		DocumentBuilder builder = null;

		builder = domFactory.newDocumentBuilder();
		xmlConfig = builder.parse(configPath);

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
