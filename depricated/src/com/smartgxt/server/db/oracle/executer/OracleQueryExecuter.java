package com.smartgxt.server.db.oracle.executer;

import java.sql.SQLException;

import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.data.ModelData;
import com.smartgxt.server.base.executers.QueryExecuter;
import com.smartgxt.server.db.jdbc.QueryStatement;
import com.smartgxt.server.db.oracle.OracleSession;
import com.smartgxt.server.db.oracle.jdbc.OracleQueryStatement;

/**
 * @author Anton Alexeyev
 * 
 */
public class OracleQueryExecuter extends QueryExecuter {

	public OracleQueryExecuter() {
	}

	@Override
	public QueryStatement<?, ?, ?> createQueryStatement() {
		return new OracleQueryStatement() {
			@Override
			public ModelData processRow() throws SQLException {
				if (isCounting())
					return super.processRow();
				else
					return OracleQueryExecuter.this.processRow();
			}
		};
	}

	public ModelData processRow() throws SQLException {
		if (getQueryStatement().getColumns() != null) {
			BaseModel bm = new BaseModel();
			for (String s : getQueryStatement().getColumns()) {
				bm.set(s, getQueryStatement().getResultSet().getObject(s));
			}
			return bm;
		} else
			return null;
	}

}
