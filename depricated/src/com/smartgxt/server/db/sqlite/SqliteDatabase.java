package com.smartgxt.server.db.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.sqlite.JDBC;

/**
 * @author Anton Alexeyev
 * 
 */
import com.smartgxt.server.db.jdbc.OracleDataBase;

/**
 * @author Anton Alexeyev
 * 
 */
public class SqliteDatabase extends OracleDataBase {

	public static Connection createConnection(String url) throws SQLException,
			InstantiationException, IllegalAccessException {

		registerDriver(JDBC.class);
		Connection conn = DriverManager.getConnection(url);
		return conn;
	}

}
