package com.smartgxt.showcase.server;

import java.sql.SQLException;

import oracle.jdbc.OracleResultSet;

import com.smartgxt.core.oracle.server.executor.OracleQueryExecuter;
import com.smartgxt.showcase.shared.Post;

public class GetPostsExecuter extends OracleQueryExecuter<Post> {
	// RpcRequestExecuter<GwtSession, PagingLoadConfig,
	// PagingLoadResultBean<Post>> {

	public GetPostsExecuter() {
		setQuery("select rownum id " + ", 'forum ' " + "  ||rownum forum "
				+ ",sysdate effective_date " + ", 'subject ' "
				+ "  ||rownum subject " + ", 'user' user_name " + "from dual "
				+ "  connect by level < 400");
	}

	@Override
	protected Post getQueryRow(OracleResultSet rset) throws SQLException {
		Post p = new Post();
		p.setId(rset.getInt("id"));
		p.setForum(rset.getString("forum"));
		p.setDate(rset.getDate("effective_date"));
		p.setSubject(rset.getString("subject"));
		p.setUsername(rset.getString("user_name"));
		return p;
	}

}
