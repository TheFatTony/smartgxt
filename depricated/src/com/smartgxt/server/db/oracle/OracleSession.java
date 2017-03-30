package com.smartgxt.server.db.oracle;

import java.sql.Connection;
import java.sql.SQLException;

import com.smartgxt.server.base.sessions.GwtSession;

/**
 * @author Anton Alexeyev
 * 
 */
public class OracleSession extends GwtSession {

	private Connection conn;

	public OracleSession() {
		super();
	}

	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	public Connection getConnection() {
		return conn;
	}

	// public static OracleSession getCurrent() {
	// return (OracleSession) SessionManager.get().getSession();
	// }

	@Override
	public void invalidate() {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conn = null;
		super.invalidate();
	}
}
