package com.smartgxt.server.db.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Anton Alexeyev
 * 
 */
public abstract class ProcedureStatement<T extends PreparedStatement> extends
		Statement<T> {
	// aw

	private Map<String, StatementParameter> values;
	private boolean autoBind = false;
	private String statement;

	public ProcedureStatement(Connection conn) {
		setConnection(conn);
		values = new HashMap<String, StatementParameter>();
	}

	public void beforExecute() throws SQLException {
		if (isAutobind()) {
			bindExecuteParams();
		}
	}

	public void afterExecute() throws SQLException {
		if (isAutobind()) {
			bindResultParams();
		}
	}

	public abstract void bindExecuteParams() throws SQLException;

	public abstract void bindResultParams() throws SQLException;

	public Map<String, StatementParameter> getValues() {
		return values;
	}

	public ProcedureStatement<T> bind(String name, StatementParameter value)
			throws SQLException {
		this.values.put(name, value);
		return this;
	}

	public Object getBindValue(String name) {
		return values.get(name).getValue();
	}

	public ProcedureStatement<T> bind(Map<String, StatementParameter> values)
			throws SQLException {
		this.values = values;
		return this;
	}

	public ProcedureStatement<T> createStatement(String call) {
		statement = call;
		return this;
	}

	@SuppressWarnings("unchecked")
	public void open() throws SQLException {
		setStatement((T) getConnection().prepareCall(statement));
		beforExecute();
		getStatement().execute();
		afterExecute();
	}

	public void close() throws SQLException {
		getStatement().close();
		setStatement(null);
	}

	@SuppressWarnings("unchecked")
	public ProcedureStatement<T> execute() throws SQLException {
		setStatement((T) getConnection().prepareCall(statement));
		beforExecute();
		getStatement().execute();
		afterExecute();
		close();
		return this;
	}

	public boolean commit() throws SQLException {
		getConnection().commit();
		return true;
	}

	public void setAutobind(boolean autobind) {
		this.autoBind = autobind;
	}

	public boolean isAutobind() {
		return autoBind;
	}

}
