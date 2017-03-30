package com.smartgxt.core.oracle.server.executor;

import java.io.Serializable;
import java.sql.SQLException;

import oracle.jdbc.OracleResultSet;

import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;
import com.smartgxt.core.oracle.server.jdbc.QueryBuilder;

/**
 * @author Anton Alexeyev
 * 
 */
public abstract class OracleQueryExecuter<D extends Serializable> extends
		OracleRequestExecuter<PagingLoadConfig, PagingLoadResultBean<D>> {

	QueryBuilder<D> queryBuilder;

	public OracleQueryExecuter() {
		queryBuilder = new QueryBuilder<D>() {

			@Override
			protected D getRow(OracleResultSet rset) throws SQLException {
				return getQueryRow(rset);
			}
		};
	}

	@Override
	public void execute() throws Throwable {
		PagingLoadConfig config = (PagingLoadConfig) getRequest();

		queryBuilder.setConnection(getConnection());
		PagingLoadResultBean<D> result = ((QueryBuilder) queryBuilder).executeQuery(config);
		setResponse(result);

	}

	public void setQuery(String query) {
		queryBuilder.setQuery(query);
	}

	protected abstract D getQueryRow(OracleResultSet rset) throws SQLException;

}
