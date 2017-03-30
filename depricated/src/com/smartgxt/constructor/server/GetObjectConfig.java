package com.smartgxt.constructor.server;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

import com.extjs.gxt.ui.client.data.BaseTreeModel;
import com.extjs.gxt.ui.client.data.TreeModel;
import com.smartgxt.server.base.executers.RpcRequestExecuter;
import com.smartgxt.server.db.oracle.OracleSession;

/**
 * @author Anton Alexeyev
 * 
 */
public class GetObjectConfig extends RpcRequestExecuter {

	String firstParent;

	private class Reordable {
		public TreeModel child;
		public TreeModel parent;

		public Reordable(TreeModel child, TreeModel parent) {
			this.child = child;
			this.parent = parent;
		}

	}

	public GetObjectConfig() {
	}

	@Override
	public void execute() throws Throwable {
		String code = (String) getRequest();

		Connection oc = ((OracleSession) getSession()).getConnection();

		OraclePreparedStatement s = (OraclePreparedStatement) oc
				.prepareStatement("SELECT o.object_, o.object_type, o.id, o.parent_id, connect_by_isleaf is_leaf "
						+ "FROM (SELECT * FROM sgxt.UI_OBJECTS WHERE APLLICATION_ID = 1 AND LANGUAGE = userenv ('LANG')) o "
						+ "START WITH O.CODE = ? "
						+ "CONNECT BY o.parent_id = prior o.id");
		s.setObject(1, code);
		OracleResultSet rs = (OracleResultSet) s.executeQuery();

		List<TreeModel> l = new ArrayList<TreeModel>();

		while (rs.next()) {
			if (firstParent == null)
				firstParent = rs.getString("parent_id");

			TreeModel tm = new BaseTreeModel();
			String objectType = rs.getString("object_type");
			tm.set("object_type", objectType);

			tm.set("id", rs.getString("id"));
			tm.set("parent_id", rs.getString("parent_id"));
			tm.set("object_",
					Constructor.applyConfiguration(objectType,
							rs.getSTRUCT("object_")));
			l.add(tm);
		}

		rs.close();
		s.close();
		rs = null;
		s = null;

		// TODO ликвидировать это УБОЖЕСТВО!!!

		List<Reordable> reparentMap = new ArrayList<Reordable>();
		for (TreeModel t : l) {
			if ((t.get("parent_id") != null)
					&& (t.get("parent_id").toString().equals(firstParent))) {
				reparentMap.add(new Reordable(t, null));
			}
			for (TreeModel t1 : l) {
				if ((t1.get("id") != null)
						&& (t.get("parent_id") != null)
						&& (t.get("parent_id").toString().intern() == t1
								.get("id").toString().intern())) {
					reparentMap.add(new Reordable(t, t1));

				}
			}
		}

		// ArrayList<TreeModel> l1 = new ArrayList<TreeModel>();
		BaseTreeModel l1 = new BaseTreeModel();
		// Collections.reverse(reparentMap);

		for (Reordable r : reparentMap) {
			if (r.parent != null) {
				r.parent.add(r.child);
			} else {
				l1 = (BaseTreeModel) r.child;
			}
		}

		setResponse(l1);

	}
}
