package com.smartgxt.server.db.oracle;

import java.sql.CallableStatement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import oracle.jdbc.OracleDriver;
import oracle.jdbc.driver.OracleConnection;

import com.smartgxt.server.base.configs.Configuration;
import com.smartgxt.server.db.jdbc.OracleDataBase;

/**
 * @author Anton Alexeyev
 * 
 */
public class OracleDatabase extends OracleDataBase {

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
}
