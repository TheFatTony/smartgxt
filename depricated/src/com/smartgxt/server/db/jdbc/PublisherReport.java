package com.smartgxt.server.db.jdbc;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.SQLException;

import oracle.apps.xdo.XDOException;
import oracle.apps.xdo.template.FOProcessor;

import com.smartgxt.server.base.configs.Configuration;
import com.smartgxt.server.base.sessions.SessionManager;

/**
 * @author Anton Alexeyev
 * 
 */
public class PublisherReport {

	private static String reportsDir = Configuration.getLocation()
			+ Configuration.getAppName() + "/reports/"
			+ SessionManager.get().getSession().getLanguage() + "/";
	private String code;
	private FOProcessor processor;
	private BufferedReader reader;

	// private static FileInputStream cfg;
	private static FileInputStream cfg;

	static {
		try {
			cfg = new FileInputStream("publisher/conf.xml");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public PublisherReport() {
		processor = new FOProcessor();
		processor.setConfig(cfg);
		// try {
		// cfg = new FileInputStream("publisher/conf.xml");
		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// }
	}

	public void report(OutputStream stream, byte format) throws SQLException,
			XDOException, IOException {

		InputStream foIS = new FileInputStream(reportsDir + code + ".xsl");
		processor.setTemplate(new BufferedReader(new InputStreamReader(foIS),
				Configuration.getBuffersSize()));

		processor.setData(reader);

		processor.setOutputFormat(format);
		processor.setOutput(stream);
		processor.generate();

		stream.close();
		System.gc();
	}

	public BufferedReader getReader() {
		return reader;
	}

	public void setReader(BufferedReader reader) {
		this.reader = reader;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
