package com.smartgxt.client.ui.widgets.grids.filters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.extjs.gxt.ui.client.data.BaseDateFilterConfig;
import com.extjs.gxt.ui.client.data.BaseFilterConfig;
import com.extjs.gxt.ui.client.data.BaseFilterPagingLoadConfig;
import com.extjs.gxt.ui.client.data.BaseListFilterConfig;
import com.extjs.gxt.ui.client.data.BaseNumericFilterConfig;
import com.extjs.gxt.ui.client.data.BaseStringFilterConfig;
import com.extjs.gxt.ui.client.data.FilterConfig;
import com.extjs.gxt.ui.client.data.FilterPagingLoadConfig;
import com.extjs.gxt.ui.client.data.ListLoadConfig;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.form.Field;

public class GidFiltersHelper {

	public GidFiltersHelper() {
	}

	public static void addFilter(ListStore<?> store, Field<?> field) {
		addFilterbyStore(store, field.getValue(), field.getName());
		System.out.println("GidFiltersHelper.addFilter()");
	}

	public static void addFilter(ListStore<?> store, ModelData data) {
		for (String name : data.getPropertyNames()) {
			addFilterbyStore(store, data.get(name), name);
		}
		System.out.println("GidFiltersHelper.addFilter()");

	}

	public static void addFilterbyStore(ListStore<?> store, Object value,
			String name) {
		if (store.getLoadConfig() instanceof ListLoadConfig) {
			System.out
					.println("store.getLoadConfig() instanceof FilterPagingLoadConfig");
			ListLoadConfig filterLoadConfig = (ListLoadConfig) store
					.getLoadConfig();
			setFilter(filterLoadConfig, value, name);
		}
	}

	public static FilterConfig setFilter(
			ListLoadConfig filterLoadConfig, Object value,
			String name) {
		System.out.println("GidFiltersHelper.setFilter()");
//		filterLoadConfig.setAllowNestedValues(true);
//		List<FilterConfig> filters = new ArrayList<FilterConfig>();
//		filterLoadConfig.setFilterConfigs(filters);
		
		List<FilterConfig> filters = new ArrayList<FilterConfig>();
		
		
		
		FilterConfig config = null;
		if (value instanceof Date) {
			System.out.println("Date");
			config = new BaseDateFilterConfig();
			config.setField(name);
			config.setValue(value);
			config.setType("date");
			config.setComparison("on");
		} else if (value instanceof List<?>) {
			System.out.println("List<?>");
			config = new BaseListFilterConfig();
			config.setField(name);
			config.setValue(value);
			config.setType("list");
			// config.setComparison("on");
		} else if (value instanceof Number) {
			System.out.println("Number");
			if (value != null) {
				config = new BaseNumericFilterConfig();
				config.setField(name);
				config.setValue(((Number) value).doubleValue());
				config.setType("number");
				config.setComparison("on");
			}
		} else {
			System.out.println("string");
			config = new BaseStringFilterConfig();
			config.setField(name);
			config.setValue(value);
			config.setType("string");
			config.setComparison("on");
		}

		filters.add(config);
//		filterLoadConfig.setFilterConfigs(filters);
		filterLoadConfig.set("sgxt#filters", filters);
		System.out.println("Client fields is not null count=" + filters.size());
		return config;
	}
}
