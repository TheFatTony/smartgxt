package com.smartgxt.server.db.sqlite.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.data.ModelData;
import com.smartgxt.server.db.jdbc.QueryStatement;

/**
 * @author Anton Alexeyev
 * 
 */
public class SqliteQueryStatement extends
		QueryStatement<PreparedStatement, ResultSet, ModelData> {

	public SqliteQueryStatement() {
		super();
		// setConnection(RemoteServlet.get().getSession()
		// .getEmbeddedDBConnection());
	}

	public void autoParse() throws SQLException {
		setColumns(new ArrayList<String>());
		ResultSetMetaData metaData = (ResultSetMetaData) rset.getMetaData();
		for (int i = 1; i <= metaData.getColumnCount(); i++) {
			getColumns().add(metaData.getColumnName(i).toLowerCase());
		}
	}

	public SqliteQueryStatement applyPagging(int limit, int offset) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM (");
		sb.append(getQuery());
		sb.append(") LIMIT :x_limit, :x_offset ");

		getValues().put("x_limit", limit);
		getValues().put("x_offset", offset);

		setQuery(sb.toString());
		return this;
	}

	@Override
	public void bindParams() throws SQLException {
		int k = 0;
		for (String s : getValues().keySet()) {
			k++;
			getStatement().setObject(k, getValues().get(s));
		}

	}

	public ModelData processRow() throws SQLException {
		if (getColumns() != null) {
			BaseModel bm = new BaseModel();
			for (String s : getColumns()) {
				if (s.equals("effective_date")) {
					bm.set(s, rset.getTimestamp(s));
				} else
					bm.set(s, rset.getObject(s));
			}
			return bm;
		} else
			return null;
	}

}
