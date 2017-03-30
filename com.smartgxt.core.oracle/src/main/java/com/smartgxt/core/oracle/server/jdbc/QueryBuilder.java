/**
 * 
 */
package com.smartgxt.core.oracle.server.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadConfigBean;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;

/**
 * @author Администратор
 * 
 */
public abstract class QueryBuilder<T> extends BaseQueryBuilder<T> {

	public int getCount() {
		return iCount;
	}

	public QueryBuilder() {
	}

	@Override
	public void buildQuery(PagingLoadConfig config, Connection connection)
			throws SQLException {
		super.buildQuery(config, connection);
		calcCount(config);
		iOffset = config.getOffset();
		if (iCount > config.getOffset())
			applyPagingLimit(config);
	}

	protected void calcCount(PagingLoadConfig config) throws SQLException {
		if (config != null && config.getLimit() != -1) {
			OraclePreparedStatement pst = (OraclePreparedStatement) conn
					.prepareStatement("SELECT COUNT(*) cnt " + " FROM ("
							+ queryBuilded + ")");

//			setParamsQueryValues(pst, config);

			OracleResultSet rset = (OracleResultSet) pst.executeQuery();
			while (rset.next()) {
				iCount = rset.getInt("cnt");
			}
			rset.close();
			rset = null;
			pst.close();
			pst = null;
		} else {
			iCount = 0;
		}
	}

	protected void applyPagingLimit(PagingLoadConfig config) {
		if (config != null && config.getLimit() != -1) {
			iLimit = Math.min(iOffset + config.getLimit(), iCount);
			queryBuilded = ("SELECT  *  FROM  (SELECT " + " a.*, ROWNUM rnum "
					+ " FROM     (" + queryBuilded + ") a "
					+ " WHERE    ROWNUM <=   :x_ilimit ) " + " WHERE    rnum >  :x_ioffset");
		}
	}

	public PagingLoadResultBean<T> executeQuery(PagingLoadConfig config)
			throws SQLException {
		ArrayList<T> list = super.executeBaseQuery(config);
		int offset = iOffset;
		if (iOffset >= iCount)
			offset = 0;
		return new PagingLoadResultBean<T>(list, getCount(), offset);
	}

	@Override
	protected abstract T getRow(OracleResultSet rset)
			throws SQLException;

}
