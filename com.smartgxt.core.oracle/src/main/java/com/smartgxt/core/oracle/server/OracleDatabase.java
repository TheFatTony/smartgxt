package com.smartgxt.core.oracle.server;

import java.sql.CallableStatement;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;

import com.smartgxt.core.server.config.Configuration;

import oracle.jdbc.OracleDriver;
import oracle.jdbc.driver.OracleConnection;

/**
 * @author Anton Alexeyev
 * 
 */
public class OracleDatabase {

	public static OracleConnection createConnection(String url, String login,
			String password) throws SQLException, InstantiationException,
			IllegalAccessException {
		registerDriver(OracleDriver.class);

		Properties props = new Properties();
		props.setProperty("user", login);
		props.setProperty("password", password);
		OracleConnection conn = (OracleConnection) DriverManager.getConnection(
				url, props);
		conn.setAutoCommit(false);

		CallableStatement p = (CallableStatement) conn
				.prepareCall("begin dbms_application_info.set_module(?, null); end;");
		p.setString(1, Configuration.getAppName());
		p.execute();
		// dbms_application_info.set_module('MY-APPLICATION',NULL);

		return conn;
	}

	public static void registerDriver(Class<? extends java.sql.Driver> class_)
			throws SQLException, InstantiationException, IllegalAccessException {
		System.out.println("OracleDatabase.registerDriver()");
		// TODO FIX
		for (Enumeration<Driver> e = DriverManager.getDrivers(); e
				.hasMoreElements();) {
			if (e.nextElement().getClass().equals(class_)) {
				return;
			}
		}
		try {
			Class.forName("com.oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		DriverManager.registerDriver(class_.newInstance());
	}
}
