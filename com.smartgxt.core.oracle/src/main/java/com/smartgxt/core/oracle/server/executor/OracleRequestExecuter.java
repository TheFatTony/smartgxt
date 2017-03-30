package com.smartgxt.core.oracle.server.executor;

import java.io.Serializable;

import java.sql.Connection;

import com.smartgxt.core.oracle.server.OracleSession;
import com.smartgxt.core.server.executers.RpcRequestExecuter;

/**
 * @author Anton Alexeyev
 * 
 */
public abstract class OracleRequestExecuter<Q extends Serializable, S extends Serializable>
		extends RpcRequestExecuter<OracleSession, Q, S> {

	public OracleRequestExecuter() {
	}

	public Connection getConnection() {
		return getSession().getConnection();
	}
}
