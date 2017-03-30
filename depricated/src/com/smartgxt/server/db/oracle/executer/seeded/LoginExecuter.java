package com.smartgxt.server.db.oracle.executer.seeded;

import java.sql.CallableStatement;
import java.util.HashMap;
import java.util.Map;

import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;
import oracle.jdbc.driver.OracleConnection;

import com.smartgxt.server.db.oracle.OracleConfiguration;
import com.smartgxt.server.db.oracle.OracleDatabase;
import com.smartgxt.server.db.oracle.OracleSession;
import com.smartgxt.server.db.oracle.executer.OracleRequestExecuter;
import com.smartgxt.shared.seeded.LoginRequestData;
import com.smartgxt.shared.seeded.LoginResponseData;

/**
 * @author Anton Alexeyev
 * 
 */
public class LoginExecuter extends
		OracleRequestExecuter<LoginRequestData, LoginResponseData> {

	public LoginExecuter() {
		setSystem(true);
	}

	@Override
	public void execute() throws Throwable {

		LoginRequestData data = getRequest();

		OracleConnection oc = OracleDatabase.createConnection(
				OracleConfiguration.getUrl(), data.getLogin(),
				data.getPassword());

		OracleSession session = getSession();

		session.setConnection(oc);
		session.loggedIn(data.getLogin());

		System.out.println("server statesUser "
				+ session.getAttribute("sgxt.statesUser"));
		System.out.println("server statesVersion "
				+ session.getAttribute("sgxt.statesVersion"));

		OraclePreparedStatement s = (OraclePreparedStatement) oc
				.prepareStatement("SELECT name, value FROM TABLE (sgxt.user_states_api.get_states(?, ?))");
		s.setObject(1, session.getAttribute("sgxt.statesUser"));
		s.setObject(2, session.getAttribute("sgxt.statesVersion"));
		OracleResultSet rs = (OracleResultSet) s.executeQuery();

		HashMap<String, String> states = new HashMap<String, String>();

		while (rs.next()) {
			states.put(rs.getString("name"), rs.getString("value"));
		}
		rs.close();
		s.close();
		rs = null;
		s = null;

		Map<String, Object> locale = data.getLocale();

		session.setLanguage((String) locale.get("id"));

		CallableStatement stmt = oc
				.prepareCall("ALTER SESSION SET nls_language = '"
						+ locale.get("nls") + "'");
		stmt.execute();
		stmt.close();
		stmt = null;

		LoginResponseData resData = new LoginResponseData();
		resData.setStates(states);
		resData.setLogin(data.getLogin());

		setResponse(resData);

	}
}
