package com.smartgxt.server.db.oracle.executer.seeded;

import java.io.Writer;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import oracle.sql.ArrayDescriptor;
import oracle.sql.CLOB;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

import com.extjs.gxt.ui.client.data.BaseModel;
import com.smartgxt.server.base.sessions.SessionManager;
import com.smartgxt.server.db.oracle.OracleSession;
import com.smartgxt.server.db.oracle.executer.OracleRequestExecuter;

/**
 * @author Anton Alexeyev
 * 
 */
public class DisconnectExecuter extends
		OracleRequestExecuter<HashMap<String, String>, BaseModel> {

	public DisconnectExecuter() {
		setSystem(true);
	}

	@Override
	public void execute() throws Throwable {
		HashMap<String, String> states = getRequest();
		OracleSession session = getSession();
		if ((session != null) && (states != null) && (session.isLoggedIn())) {
			Connection conn = session.getConnection();

			StructDescriptor userStateType = StructDescriptor.createDescriptor(
					"SGXT.USER_STATE_T", conn);

			ArrayDescriptor userStatesType = ArrayDescriptor.createDescriptor(
					"SGXT.USER_STATES_T", conn);

			List<STRUCT> userStates = new ArrayList<STRUCT>();

			for (String s : states.keySet()) {
				Object[] attributes = new Object[2];
				attributes[0] = s;

				CLOB clob = CLOB.createTemporary(conn, true,
						CLOB.DURATION_SESSION);
				@SuppressWarnings("deprecation")
				Writer w = clob.getCharacterOutputStream();
				w.write(states.get(s));
				w.close();

				attributes[1] = clob;

				STRUCT struct = new STRUCT(userStateType, conn, attributes);
				userStates.add(struct);
			}

			oracle.sql.ARRAY p_states = new oracle.sql.ARRAY(userStatesType,
					conn, userStates.toArray());

			// TODO migrate to OracleCallableStatement
			CallableStatement p = (CallableStatement) conn
					.prepareCall("begin sgxt.user_states_api.update_states(p_states => ?); end;");
			p.setArray(1, p_states);
			p.execute();
			p.close();
			p = null;
		}
		setResponse(new BaseModel());
		SessionManager.get().destroySession();
	}
}
