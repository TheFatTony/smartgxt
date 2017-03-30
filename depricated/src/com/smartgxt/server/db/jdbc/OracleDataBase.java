package com.smartgxt.server.db.jdbc;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * @author Anton Alexeyev
 * 
 */
public class OracleDataBase {

	public OracleDataBase() {
	}

	public static void registerDriver(Class<? extends java.sql.Driver> class_)
			throws SQLException, InstantiationException, IllegalAccessException {
		for (Enumeration<Driver> e = DriverManager.getDrivers(); e
				.hasMoreElements();) {
			if (e.nextElement().getClass().equals(class_)) {
				return;
			}
		}
		DriverManager.registerDriver(class_.newInstance());
	}

}
