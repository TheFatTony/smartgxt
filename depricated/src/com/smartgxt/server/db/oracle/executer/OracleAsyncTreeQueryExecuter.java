package com.smartgxt.server.db.oracle.executer;

import java.sql.SQLException;
import java.util.List;

import com.extjs.gxt.ui.client.data.BaseListLoadResult;
import com.extjs.gxt.ui.client.data.BaseRemoteSortTreeLoadConfig;
import com.extjs.gxt.ui.client.data.BaseTreeModel;
import com.extjs.gxt.ui.client.data.TreeModel;
import com.smartgxt.server.base.executers.RpcRequestExecuter;
import com.smartgxt.server.db.oracle.OracleSession;
import com.smartgxt.server.db.oracle.jdbc.OracleTreeQueryStatement;

/**
 * @author Anton Alexeyev
 * 
 */
public class OracleAsyncTreeQueryExecuter extends RpcRequestExecuter {

	private String query;
	private OracleTreeQueryStatement queryStatment;

	public OracleAsyncTreeQueryExecuter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() throws Exception {

		queryStatment = new OracleTreeQueryStatement(
				((OracleSession) getSession()).getConnection()) {
			@Override
			public TreeModel processRow() throws SQLException {
				return OracleAsyncTreeQueryExecuter.this.processRow();
			}
		};
		queryStatment.setAutoparse(true);
		queryStatment.createQuery(getQuery());

		if (getRequest() instanceof BaseRemoteSortTreeLoadConfig) {
			BaseRemoteSortTreeLoadConfig loadConfig = (BaseRemoteSortTreeLoadConfig) getRequest();
			if (loadConfig != null) {
				if (loadConfig.getSortField() != null)
					queryStatment.orderBy(loadConfig.getSortField(),
							loadConfig.getSortDir());
				if (loadConfig.getParent() != null) {
					queryStatment.whereForAnd();
					queryStatment.and(queryStatment.getParentField(), "=",
							loadConfig.getParent().get("id"));
				} else {
					queryStatment.whereForAnd();
					queryStatment.and(queryStatment.getParentField(), "=",
							queryStatment.getStartWith());
				}
			}
		}

		List<TreeModel> l = queryStatment.getResultList();
		setResponse(new BaseListLoadResult<TreeModel>(l));
	}

	public TreeModel processRow() throws SQLException {
		if (queryStatment.getColumns() != null) {
			BaseTreeModel bm = new BaseTreeModel();
			for (String s : queryStatment.getColumns()) {
				if (s.equals("is_leaf"))
					bm.set("is_leaf",
							queryStatment.getResultSet().getBoolean("is_leaf"));
				else
					bm.set(s, queryStatment.getResultSet().getObject(s));
			}

			return bm;
		} else
			return null;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getQuery() {
		return query;
	}

	public OracleTreeQueryStatement getQueryStatment() {
		return queryStatment;
	}
}
