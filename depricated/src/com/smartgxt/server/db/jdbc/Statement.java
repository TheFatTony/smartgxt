package com.smartgxt.server.db.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Anton Alexeyev
 * 
 */
public class Statement<T extends java.sql.Statement> {

	private T statement;
	private Connection conn;

	public Statement() {
	}

	public Connection getConnection() throws SQLException {
		if (conn == null)
			throw new SQLException("Not connected");
		return conn;
	}

	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	public void setStatement(T statement) {
		this.statement = statement;
	}

	public T getStatement() {
		return statement;
	}

}
