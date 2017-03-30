package com.smartgxt.server.db.oracle.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import com.smartgxt.server.db.jdbc.Direction;
import com.smartgxt.server.db.jdbc.ProcedureStatement;
import com.smartgxt.server.db.jdbc.StatementParameter;

import oracle.jdbc.OracleCallableStatement;

/**
 * @author Anton Alexeyev
 * 
 */
public class OracleProcedureStatement extends
		ProcedureStatement<OracleCallableStatement> {

	public OracleProcedureStatement(Connection conn) {
		super(conn);
	}

	@Override
	public void bindExecuteParams() throws SQLException {
		for (String s : getValues().keySet()) {
			StatementParameter o = getValues().get(s);
			Direction d = o.getDirection();
			if ((d == Direction.OUT) || (d == Direction.INOUT))
				getStatement().registerOutParameter(s, o.getSqlType());
			if ((d == Direction.IN) || (d == Direction.INOUT))
				getStatement().setObject(s, o.getValue());
		}
	}

	@Override
	public void bindResultParams() throws SQLException {
		for (String s : getValues().keySet()) {
			StatementParameter o = getValues().get(s);
			Direction d = o.getDirection();
			if ((d == Direction.OUT) || (d == Direction.INOUT))
				o.setValue(getStatement().getObject(s));
		}

	}

	public boolean commit() throws SQLException {
		boolean res;
		setStatement((OracleCallableStatement) getConnection().prepareCall(
				"begin commit; end;"));
		res = getStatement().execute();
		getStatement().close();
		setStatement(null);
		return res;
	}

}
