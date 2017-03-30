package com.smartgxt.server.base.executers;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BaseFilterPagingLoadConfig;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.FilterConfig;
import com.extjs.gxt.ui.client.data.ListLoadConfig;
import com.extjs.gxt.ui.client.data.ModelData;
import com.smartgxt.server.base.sessions.GwtSession;
import com.smartgxt.server.db.jdbc.QueryStatement;
import com.smartgxt.server.db.jdbc.ResultSetToExcel2007;
import com.smartgxt.server.db.oracle.OracleSession;
import com.smartgxt.server.streams.StreamDefinition;
import com.smartgxt.shared.ExcelColumnsConfig;
import com.smartgxt.shared.ExcelColumnsGroups;
import com.smartgxt.shared.data.FileDefenition;

/**
 * @author Anton Alexeyev
 * 
 */
public abstract class QueryExecuter extends RpcRequestExecuter {

	private String query;
	private QueryStatement<?, ?, ?> queryStatement;
	private ResultSetToExcel2007 rsetToExcel;

	public QueryExecuter() {
	}

	@Override
	public void execute() throws Exception {
		// RpcProxyRequestData request = (RpcProxyRequestData) getRequest();

		queryStatement.setConnection(((OracleSession) getSession())
				.getConnection());

		int cnt;
		int offset;
		BaseFilterPagingLoadConfig filtersConfig = null;
		// Object loadConfig = request.getLoadConfig();

		if (getRequest() instanceof BaseFilterPagingLoadConfig) {
			filtersConfig = (BaseFilterPagingLoadConfig) getRequest();

			processFilters(filtersConfig.getFilterConfigs());
			processFilters((List<FilterConfig>) filtersConfig.get("sgxt#filters"));

		}
		
		if (getRequest() instanceof ListLoadConfig) {

			processFilters((List<FilterConfig>) (((ListLoadConfig) getRequest()).get("sgxt#filters")));

		}

		if (filtersConfig.getSortField() != null)
			queryStatement = queryStatement.orderBy(
					filtersConfig.getSortField(), filtersConfig.getSortDir());

		if ("2007".equals(filtersConfig.get("type"))) {

			GwtSession session = getSession();
			StreamDefinition sd = null;

			ArrayList<ExcelColumnsConfig> cols = filtersConfig.get("cols");
			ArrayList<ExcelColumnsGroups> groups = filtersConfig.get("groups");

			queryStatement.open();
			rsetToExcel = new ResultSetToExcel2007(
					queryStatement.getResultSet(), cols, groups, "Лист 1");
			sd = session.getUserStreams().addStreamDefinition("big-grid.xlsx");

			rsetToExcel.generate(sd.getOutputStream());
			queryStatement.close();
			// setResponseProperty("file", sd);
			setResponse(new FileDefenition(((StreamDefinition) sd).getId()));
		} else {
			cnt = queryStatement.calcCount();

			offset = filtersConfig.getOffset();

			queryStatement.applyPagging(filtersConfig.getOffset(),
					filtersConfig.getLimit());

			@SuppressWarnings("unchecked")
			ArrayList<ModelData> l = (ArrayList<ModelData>) queryStatement
					.getResultList();

			if (cnt < offset)
				offset = 0;

			setResponse(new BasePagingLoadResult<ModelData>(l,
					filtersConfig.getOffset(), cnt));
		}

	}

	private void processFilters(List<FilterConfig> filters) {
		if (filters != null) {
			System.out.println("Server fields is not null count="
					+ filters.size());
			queryStatement.whereForAnd();
			for (FilterConfig f : filters) {
				System.out.println("add filter "
						+ getCondition(f.getComparison(), f.getValue()) + " "
						+ f.getValue());
				queryStatement.and(f.getField(),
						getCondition(f.getComparison(), f.getValue()),
						f.getValue());
			}
		}
	}

	private String getCondition(String gxtComparison, Object value) {
		String condition = null;
		if (value instanceof ArrayList<?>)
			condition = "in";
		else if ("lt".equals(gxtComparison))
			condition = "<";
		else if ("gt".equals(gxtComparison))
			condition = ">";
		else if ("befor".equals(gxtComparison))
			condition = "<";
		else if ("after".equals(gxtComparison))
			condition = ">";
		else if ("on".equals(gxtComparison))
			condition = "=";
		else
			condition = "=";

		return condition;
	}

	public void setQuery(String query) {
		this.query = query;
		queryStatement = createQueryStatement();
		queryStatement.setAutoparse(true);
		queryStatement.createQuery(query);
		// queryStatement = new OracleQueryStatement();
		// queryStatement.setAutoparse(true);
		// queryStatement = queryStatement.createQuery(query);
	}

	public abstract QueryStatement<?, ?, ?> createQueryStatement();

	public String getQuery() {
		return query;
	}

	public QueryStatement<?, ?, ?> getQueryStatement() {
		return queryStatement;
	}
}
