/**
 * 
 */
package com.smartgxt.core.oracle.server.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

import com.sencha.gxt.data.shared.loader.FilterConfig;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfigBean;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadConfigBean;

/**
 * @author Администратор
 * 
 */
public abstract class BaseQueryBuilder<T> {

	protected String queryInit;
	protected String queryBuilded;
	protected int iOffset;
	protected int iLimit;
	protected int iCount;
	protected ArrayList<T> rows;

	private OracleResultSet rset;
	private OraclePreparedStatement pst;

	protected Connection conn;

	// protected HashMap<String, Object> hmOptionalFilters;
	// protected HashMap<String, Object> hmMandatoryFilters;

	protected HashMap<String, String> hmSpecialFilters;

	public BaseQueryBuilder() {
		hmSpecialFilters = new HashMap<String, String>();
	}

	public void addSpecialFilter(String field, String condition) {
		hmSpecialFilters.put(field, condition);
	}

	public void setQuery(String query) {
		queryInit = query;
	}

	protected String getQueryBuilded() {
		return queryBuilded;
	}

	protected void setQueryBuilded(String query) {
		queryBuilded = query;
	}

	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	// protected void applyOptionalFilters(FilterPagingLoadConfigBean config) {
	// if (config != null && config.get("OptionalFilters") != null) {
	// hmOptionalFilters = config.get("OptionalFilters");
	// queryBuilded = " SELECT * FROM (" + queryBuilded + ") WHERE 1 = 1 ";
	// for (String element : hmOptionalFilters.keySet()) {
	// String condition = getCondition(element,
	// hmOptionalFilters.get(element), "");
	// if (condition != "") {
	// queryBuilded = queryBuilded + " AND " + condition;
	// }
	// }
	// }
	// }

	@SuppressWarnings("unchecked")
	private String getCondition(String field, Object value, String prefix) {
		if (value instanceof String) {
			String specKey = hmSpecialFilters.get(field);
			if (specKey != null)
				return specKey;
			else
				return "lower(" + field + ") LIKE lower(:x_" + field + prefix
						+ ") ";
		} else if (value instanceof ArrayList<?>) {
			String result = "";
			int i = 0;
			for (Object subValue : (ArrayList<Object>) value) {
				result += ((result == "") ? "" : " OR ")
						+ getCondition(field, subValue, "" + i++);
			}
			return (result == "") ? "" : "(" + result + ")";
		} else if (value instanceof Integer) {
			return field + " = :x_" + field + prefix;
		} else if (value instanceof java.util.Date) {
			return "trunc(" + field + ") = trunc(:x_" + field + prefix + ")";
		}
		return "";
	}

	// protected void applySorting(PagingLoadConfig config) {
	// if (config != null && config.getSortInfo() != null
	// && config.getSortInfo().get(0).getSortField() != null) {
	// queryBuilded = "SELECT a.* FROM (" + queryBuilded + " ORDER BY "
	// + config.getSortInfo().get(0).getSortField() + " "
	// + config.getSortInfo().get(0).getSortDir() + ") a ";
	// }
	// }

	public void buildQuery(PagingLoadConfig config, Connection connection)
			throws SQLException {
		queryBuilded = queryInit;
		if (config instanceof FilterPagingLoadConfigBean) {
			for (FilterConfig f : ((FilterPagingLoadConfigBean) config)
					.getFilters()) {
				System.out.println(f.getField() + " = " + f.getValue());
			}
		}
		// applyOptionalFilters(config);
		// applySorting(config);
	}

	@SuppressWarnings("unchecked")
	private void setParamQueryValues(OraclePreparedStatement pst, String field,
			Object value, String prefix) throws SQLException {
		if (value instanceof ArrayList<?>) {
			int i = 0;
			for (Object subValue : (ArrayList<Object>) value)
				setParamQueryValues(pst, field, subValue, "" + i++);
		} else
			pst.setObjectAtName("x_" + field + prefix, value);
	}

	// protected void setParamsQueryValues(OraclePreparedStatement pst,
	// PagingLoadConfig config) throws SQLException {
	// if (config != null && config.get("MandatoryFilters") != null) {
	// hmMandatoryFilters = config.get("MandatoryFilters");
	// for (String element : hmMandatoryFilters.keySet()) {
	// pst.setObjectAtName(element, hmMandatoryFilters.get(element));
	// }
	// }
	// if (config != null && config.get("OptionalFilters") != null) {
	// hmOptionalFilters = config.get("OptionalFilters");
	// for (String element : hmOptionalFilters.keySet()) {
	// setParamQueryValues(pst, element,
	// hmOptionalFilters.get(element), "");
	// }
	// }
	// }

	public void prepareResultSet(Object config) throws SQLException {
		if (config instanceof PagingLoadConfigBean)
			buildQuery((PagingLoadConfigBean) config, conn);
		else
			queryBuilded = queryInit;

		pst = (OraclePreparedStatement) conn
				.prepareStatement(getQueryBuilded());

		if ((config instanceof PagingLoadConfigBean)
				&& (((PagingLoadConfigBean) config).getLimit() != -1)
				&& (iCount > ((PagingLoadConfigBean) config).getOffset())) {
			pst.setObjectAtName("x_ilimit", iLimit);
			pst.setObjectAtName("x_ioffset", iOffset);
		}

		// if (config instanceof PagingLoadConfig)
		// setParamsQueryValues(pst, (PagingLoadConfig) config);
		// else {
		// BaseTreeModel btm = (BaseTreeModel) config;
		// if (btm != null) {
		// pst.setObjectAtName("x_parent_id", btm.get("id"));
		// // pst.setObjectAtName("x_filter", null);
		// } else {
		// pst.setObjectAtName("x_parent_id", null);
		// // pst.setObjectAtName("x_filter", null);
		// }
		// }
		rset = (OracleResultSet) pst.executeQuery();
	}

	public ArrayList<T> executeBaseQuery(Object config) throws SQLException {
		rows = new ArrayList<T>();
		try {
			prepareResultSet(config);
			System.out.println(queryBuilded);
			while (rset.next()) {
				T row = getRow(rset);
				if (row != null)
					rows.add(row);
			}
			rset.close();
			rset = null;
			pst.close();
			pst = null;
		} catch (SQLException e) {
			if (e.getErrorCode() == 17041) {
				iOffset = 0;
				iCount = 0;
			} else
				throw e;

			rows.clear();
			return rows;
		}
		queryBuilded = null;
		return rows;
	}

	protected abstract T getRow(OracleResultSet rset) throws SQLException;

	public OracleResultSet getResultSet() {
		return rset;
	}

}
