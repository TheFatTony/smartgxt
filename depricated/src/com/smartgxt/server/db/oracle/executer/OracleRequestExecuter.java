package com.smartgxt.server.db.oracle.executer;

import java.io.Serializable;

import com.smartgxt.server.base.executers.RpcRequestExecuter;
import com.smartgxt.server.db.oracle.OracleSession;

/**
 * @author Anton Alexeyev
 * 
 */
public abstract class OracleRequestExecuter<Q extends Serializable, S extends Serializable>
		extends RpcRequestExecuter<OracleSession, Q, S> {

	public OracleRequestExecuter() {
	}

}
