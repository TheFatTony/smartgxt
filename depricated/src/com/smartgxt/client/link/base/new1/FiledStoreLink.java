package com.smartgxt.client.link.base.new1;

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.grid.Grid;

public class FiledStoreLink<T extends BaseLinkData<?, ?>> extends BaseList<T> {

	@SuppressWarnings("unchecked")
	public FiledStoreLink() {
		super();
		setLink((T) new BaseLinkDataGrid<Grid<ModelData>, String>());
		setLink((T) new BaseLinkDataString<Field<?>, String>());		
	}

	public static final EventType Refresh = new EventType();

	// public void setField(T field) {
	// ListenerLink lsnr = new ListenerLink(field);
	// addListener(Refresh, lsnr);
	// }

	@SuppressWarnings("rawtypes")
	@Override
	public void onHandlerEvent(T link, BaseEvent be) {
		((BaseLinkData) link).getValueChild();
		((BaseLinkData) link).getValueParent();
		
		// link.getChild().getLoader();
		// link.getParent().getData("");
	}

	// ///////////////////////////////////

	@Override
	public void setLink(T link) {
		super.setLink(link);
	}

	// ///////////////////////////////////

	@Override
	public boolean validate() {
		return isLocked();
	}

	@Override
	public void execute() {
		/*
		 * addOnExecuteListner(new Listener<LinkExecuteEvent>() {
		 * 
		 * @Override public void handleEvent(LinkExecuteEvent be) {
		 * getParent().addListener(Events.Valid, new Listener<FieldEvent>() {
		 * 
		 * @Override public void handleEvent(FieldEvent be) { if
		 * (getChild().getLoadConfig() instanceof BaseFilterPagingLoadConfig) {
		 * // TODO extract to the separate class or // Helper, Helper is better
		 * BaseFilterPagingLoadConfig filterLoadConfig =
		 * (BaseFilterPagingLoadConfig) getChild() .getLoadConfig();
		 * List<FilterConfig> filters = filterLoadConfig .getFilterConfigs(); if
		 * (filters == null) { filters = new ArrayList<FilterConfig>();
		 * filterLoadConfig .setFilterConfigs(filters); } FilterConfig config =
		 * null; Object value = be.getValue(); if (value instanceof Date) {
		 * config = new BaseDateFilterConfig();
		 * config.setField(be.getField().getName()); config.setValue(value);
		 * config.setType("date"); config.setComparison("on"); } else if (value
		 * instanceof List<?>) { config = new BaseListFilterConfig();
		 * config.setField(be.getField().getName()); config.setValue(value);
		 * config.setType("list"); // config.setComparison("on"); } else if
		 * (value instanceof Number) { if (value != null) { config = new
		 * BaseNumericFilterConfig(); config.setField(be.getField() .getName());
		 * 
		 * config.setValue(((Number) value) .doubleValue());
		 * config.setType("number"); config.setComparison("on"); } } else {
		 * config = new BaseStringFilterConfig();
		 * config.setField(be.getField().getName()); config.setValue(value);
		 * config.setType("number"); config.setComparison("on"); } if (config !=
		 * null) filters.add(config); } } });
		 * 
		 * } });
		 */
	}

	@Override
	public boolean close() {
		return false;
	}

	@Override
	public void setChild(Object child) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setParent(Object parent) {
		// TODO Auto-generated method stub

	}

	// setImmediateUnlock(false);

	// getChild().getLoader().addListener(Loader.BeforeLoad,
	// new Listener<LoadEvent>() {
	//
	// @Override
	// public void handleEvent(LoadEvent be) {
	// getParent().validate();
	// }
	// });
	// getChild().getLoader().addListener(Loader.Load,
	// new Listener<LoadEvent>() {
	//
	// @Override
	// public void handleEvent(LoadEvent be) {
	// unLock();
	// }
	// });
	// getChild().getLoader().addListener(Loader.LoadException,
	// new Listener<LoadEvent>() {
	//
	// @Override
	// public void handleEvent(LoadEvent be) {
	// unLock();
	// }
	// });

	// @SuppressWarnings("unchecked")
	// public void setParent(C parent) {
	// parent.addValueChangeHandler(new ValueChangeHandler() {
	//
	// @Override
	// public void onValueChange(ValueChangeEvent event) {
	// getChild().getLoadConfig().set("p_field", event.getValue());
	// getChild().getLoader().load();
	// }
	// });
	// }

}
