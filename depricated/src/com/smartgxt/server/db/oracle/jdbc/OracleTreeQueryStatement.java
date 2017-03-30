package com.smartgxt.server.db.oracle.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;
import oracle.jdbc.OracleResultSetMetaData;

import com.extjs.gxt.ui.client.data.BaseTreeModel;
import com.extjs.gxt.ui.client.data.TreeModel;
import com.smartgxt.server.db.jdbc.QueryStatement;

/**
 * @author Anton Alexeyev
 * 
 */
public class OracleTreeQueryStatement extends
		QueryStatement<OraclePreparedStatement, OracleResultSet, TreeModel> {

	private String parentField = "parent_id";
	private String idField = "id";
	private String startWith = "0";

	public OracleTreeQueryStatement(Connection conn) {
		super();
		setConnection(conn);
	}

	public void autoParse() throws SQLException {
		setColumns(new ArrayList<String>());
		OracleResultSetMetaData metaData = (OracleResultSetMetaData) rset
				.getMetaData();
		for (int i = 1; i <= metaData.getColumnCount(); i++) {
			getColumns().add(metaData.getColumnName(i).toLowerCase());
		}
	}

	@Override
	public void setQuery(String query) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT z.*, connect_by_isleaf is_leaf FROM (");
		sb.append(query);
		sb.append(") z");
		sb.append(" START WITH " + parentField + " = " + startWith);
		sb.append(" CONNECT BY " + parentField + " = prior " + idField);

		super.setQuery(query);
	}

	public OracleTreeQueryStatement applyPagging(int limit, int offset) {
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

	@Override
	public TreeModel processRow() throws SQLException {
		if (getColumns() != null) {
			BaseTreeModel bm = new BaseTreeModel();
			for (String s : getColumns()) {
				if (s.equals("is_leaf"))
					bm.set("is_leaf", rset.getBoolean("is_leaf"));
				else
					bm.set(s, rset.getObject(s));
			}

			return bm;
		} else
			return null;
	}

	public void setParentField(String parentField) {
		this.parentField = parentField;
	}

	public String getParentField() {
		return parentField;
	}

	public String getIdField() {
		return idField;
	}

	public void setIdField(String idField) {
		this.idField = idField;
	}

	public String getStartWith() {
		return startWith;
	}

	public void setStartWith(String startWith) {
		this.startWith = startWith;
	}
}
