package com.smartgxt.server.db.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.data.ModelData;

/**
 * @author Anton Alexeyev
 * 
 */
public abstract class QueryStatement<T extends PreparedStatement, R extends ResultSet, M extends ModelData>
		extends Statement<T> {

	protected R rset;

	private Map<String, Object> values;
	private boolean autoparse = false;
	private List<String> columns;
	private List<M> result;
	private String originalQuery;
	private String query;
	private String lasCondition;

	private int bindsCounter = 0;

	private boolean counting;

	public QueryStatement() {
		values = new HashMap<String, Object>();
	}

	public void bind(Map<String, Object> values) {
		this.values = values;
	}

	public abstract void autoParse() throws SQLException;

	public R getResultSet() {
		return rset;
	}

	public List<M> getResultList() throws SQLException {
		execute();
		return result;
	}

	public M getResultValue() throws SQLException {
		execute();
		for (M s : result)
			return s;
		return null;
	}

	public QueryStatement<T, R, M> createQuery(String query) {
		setQuery(query);
		setOriginalQuery(query);
		return this;
	}

	public int calcCount() throws SQLException {
		String queryBuff = query;
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT COUNT (*) cnt_ FROM (");
		sb.append(query);
		sb.append(")");
		query = sb.toString();
		counting = true;
		M m = getResultValue();
		counting = false;
		query = queryBuff;

		return ((Number) m.get("cnt_")).intValue();
	}

	public QueryStatement<T, R, M> orderBy(String field, SortDir dir) {
		String s = getSortDir(dir).toString();

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM (");
		sb.append(query);
		sb.append(") ORDER BY ");
		sb.append(field);
		sb.append(" ");
		sb.append(s);
		query = sb.toString();
		return this;
	}

	public static SortDir getSortDir(SortDir sortDir) {
		return (sortDir == SortDir.ASC) ? SortDir.ASC : SortDir.DESC;
	}

	public abstract QueryStatement<T, R, M> applyPagging(int limit, int offset);

	public QueryStatement<T, R, M> whereForAnd() {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM (");
		sb.append(query);
		sb.append(") WHERE 1 = 1");

		query = sb.toString();
		return this;
	}

	public QueryStatement<T, R, M> whereForOr() {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM (");
		sb.append(query);
		sb.append(") WHERE 1 = 0");

		query = sb.toString();
		return this;
	}

	public QueryStatement<T, R, M> and(String field, String condition,
			Object value) {
		StringBuffer sb = new StringBuffer();
		sb.append(query);
		sb.append(" AND ");
		sb.append(field);
		sb.append(" ");
		sb.append(condition);

		if ("in".equals(condition)) {
			sb.append(" (");
			@SuppressWarnings("unchecked")
			List<String> l = (ArrayList<String>) value;
			int k = 1;
			for (String s : l) {
				String bind = getNexBind();
				sb.append(" :");
				sb.append(bind);
				values.put(bind, s);

				if (k != l.size())
					sb.append(",");
				k++;
			}
			sb.append(")");
		} else {
			String bind = getNexBind();
			sb.append(" :");
			sb.append(bind);
			values.put(bind, value);
		}

		query = sb.toString();
		return this;
	}

	public void execute() throws SQLException {
		open();
		if (isAutoparse())
			autoParse();

		while (rset.next()) {
			M row = processRow();
			if (row != null)
				result.add(row);
		}

		close();
	}

	public List<M> getResult() {
		return result;
	}

	@SuppressWarnings("unchecked")
	public void open() throws SQLException {
		result = new ArrayList<M>();
		setStatement((T) getConnection().prepareStatement(query));
		if (values != null)
			bindParams();

		rset = (R) getStatement().executeQuery();
	}

	public abstract void bindParams() throws SQLException;

	public void close() throws SQLException {
		rset.close();
		rset = null;
		getStatement().close();
		setStatement(null);
	}

	public abstract M processRow() throws SQLException;

	public void setAutoparse(boolean autoparse) {
		this.autoparse = autoparse;
	}

	public boolean isAutoparse() {
		return autoparse;
	}

	public void setOriginalQuery(String originalQuery) {
		this.originalQuery = originalQuery;
	}

	public String getOriginalQuery() {
		return originalQuery;
	}

	public void setLasCondition(String lasCondition) {
		this.lasCondition = lasCondition;
	}

	public String getLasCondition() {
		return lasCondition;
	}

	public String getNexBind() {
		String s = "x_bind";
		s = s + bindsCounter;
		bindsCounter++;
		return s;
	}

	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public Map<String, Object> getValues() {
		return values;
	}

	public void setCounting(boolean counting) {
		this.counting = counting;
	}

	public boolean isCounting() {
		return counting;
	}

}
