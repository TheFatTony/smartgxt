package com.smartgxt.server.db.oracle.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;
import oracle.jdbc.OracleResultSetMetaData;

import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.data.ModelData;
import com.smartgxt.server.db.jdbc.QueryStatement;

/**
 * @author Anton Alexeyev
 * 
 */
public class OracleQueryStatement extends
		QueryStatement<OraclePreparedStatement, OracleResultSet, ModelData> {

	public OracleQueryStatement() {
		super();
	}

	public void autoParse() throws SQLException {
		setColumns(new ArrayList<String>());
		OracleResultSetMetaData metaData = (OracleResultSetMetaData) rset
				.getMetaData();
		for (int i = 1; i <= metaData.getColumnCount(); i++) {
			getColumns().add(metaData.getColumnName(i).toLowerCase());
		}
	}

	public OracleQueryStatement applyPagging(int limit, int offset) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM (");
		sb.append("SELECT a.*, ROWNUM rnum FROM (");
		sb.append(getQuery());
		sb.append(") a WHERE ROWNUM <= :x_last) ");
		sb.append("WHERE rnum > :x_first");

		getValues().put("x_last", limit + offset);
		getValues().put("x_first", limit);

		setQuery(sb.toString());
		return this;
	}

	@Override
	public void bindParams() throws SQLException {
		for (String s : getValues().keySet())
			getStatement().setObjectAtName(s, getValues().get(s));

	}

	public ModelData processRow() throws SQLException {
		if (getColumns() != null) {
			BaseModel bm = new BaseModel();
			for (String s : getColumns()) {
				bm.set(s, rset.getObject(s));
			}
			return bm;
		} else
			return null;
	}

}
