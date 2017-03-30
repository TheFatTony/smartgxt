package com.smartgxt.client.ui.widgets.grids.plugins;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BaseFilterPagingLoadConfig;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.LoadEvent;
import com.extjs.gxt.ui.client.data.Loader;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.ComponentPlugin;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.smartgxt.client.data.RpcLoader;
import com.smartgxt.client.data.RpcProxy;
import com.smartgxt.client.ui.widgets.grids.columns.ColumnConfig;
import com.smartgxt.shared.ExcelColumnsConfig;
import com.smartgxt.shared.ExcelColumnsGroups;
import com.smartgxt.shared.data.FileDefenition;

/**
 * @author Anton Alexeyev
 * 
 */
public class GridExcelExportPlugin implements ComponentPlugin {

	protected Grid<?> grid;
	protected MenuItem exportData;
	private RpcLoader call;

	public GridExcelExportPlugin() {

		// TODO localization
		exportData = new MenuItem("Export data");
		exportData.addSelectionListener(onExportDataMenuSelection);
	}

	private SelectionListener<MenuEvent> onExportDataMenuSelection = new SelectionListener<MenuEvent>() {
		@Override
		public void componentSelected(MenuEvent ce) {
			export();
			ce.getMenu().hide();
		}
	};

	public void export() {
		// getGrid().setData("sgxt.type", "2007");
		// RpcProxyRequestData r = new RpcProxyRequestData(callId);
		BaseFilterPagingLoadConfig config = (BaseFilterPagingLoadConfig) grid
				.getStore().getLoadConfig();
		if (config == null)
			config = new BaseFilterPagingLoadConfig();

		config.set("type", "2007");

		if (grid.getColumnModel().getHeaderGroups() != null) {
			List<ExcelColumnsGroups> groups = new ArrayList<ExcelColumnsGroups>();
			for (com.extjs.gxt.ui.client.widget.grid.HeaderGroupConfig h : grid
					.getColumnModel().getHeaderGroups()) {

				ExcelColumnsGroups group = new ExcelColumnsGroups(
						h.getColumn(), h.getColspan(), h.getHtml());
				groups.add(group);
				for (int i = h.getColumn(); i < (h.getColspan() + h.getColumn()); i++) {
					if (grid.getColumnModel().getColumn(i).isHidden()) {
						group.setColumnsSpan(group.getColumnsSpan() - 1);
						if (group.getColumnsSpan() == 0)
							groups.remove(group);
					}
				}

			}
			config.set("groups", groups);
			for (com.extjs.gxt.ui.client.widget.grid.HeaderGroupConfig h : grid
					.getColumnModel().getHeaderGroups()) {
				for (int i = h.getColumn(); i < (h.getColspan() + h.getColumn()); i++) {

					if (grid.getColumnModel().getColumn(i).isHidden()) {
						for (ExcelColumnsGroups cg : groups) {
							if ((cg.getColumn() != 0) && (cg.getColumn() > i))
								cg.setColumn(cg.getColumn() - 1);
						}
					}
				}
			}
		}

		List<ExcelColumnsConfig> cols = new ArrayList<ExcelColumnsConfig>();

		int k = 0;
		for (com.extjs.gxt.ui.client.widget.grid.ColumnConfig cc : grid
				.getColumnModel().getColumns()) {
			com.smartgxt.client.ui.widgets.grids.columns.ColumnConfig ccr = (com.smartgxt.client.ui.widgets.grids.columns.ColumnConfig) cc;
			if (!cc.isHidden()) {
				cols.add(new ExcelColumnsConfig(cc.getId(), ccr.getHeader(), k,
						cc.getWidth(), ccr.getFormat(), !cc.isHidden()));
				k++;
			}
		}
		config.set("cols", cols);
		call.load(config);
	}

	@Override
	public void init(Component component) {
		assert component instanceof Grid<?> : "GridExcelExport can only be used with a Grid.";
		this.grid = (Grid<?>) component;

		call = new RpcLoader(((RpcProxy<?>) ((BasePagingLoader<?>) this.grid
				.getStore().getLoader()).getProxy()).getCall());
		call.addListener(Loader.Load, new Listener<LoadEvent>() {

			@Override
			public void handleEvent(LoadEvent be) {
				RpcProxy.downloadFile((FileDefenition) be.getData());
			}
		});
		grid.removeListener(Events.HeaderContextMenu, onHeaderContextMenu);
		grid.addListener(Events.HeaderContextMenu, onHeaderContextMenu);
		// callId = ((PaggingListStore) grid.getStore()).getCall();
	}

	private Listener<GridEvent<?>> onHeaderContextMenu = new Listener<GridEvent<?>>() {
		public void handleEvent(GridEvent<?> be) {
			Menu headerContextMenu = be.getMenu();
			headerContextMenu.add(exportData);
		}
	};

}
