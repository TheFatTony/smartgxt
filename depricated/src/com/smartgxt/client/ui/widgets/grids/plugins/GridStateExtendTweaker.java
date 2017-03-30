package com.smartgxt.client.ui.widgets.grids.plugins;

import java.util.HashMap;
import java.util.Map;

import com.extjs.gxt.ui.client.event.ColumnModelEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.ComponentPlugin;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;

/**
 * @author Anton Alexeyev
 * 
 */
public class GridStateExtendTweaker {

	private com.extjs.gxt.ui.client.widget.grid.Grid<?> grid;

	public GridStateExtendTweaker(
			com.extjs.gxt.ui.client.widget.grid.Grid<?> grid) {
		this.grid = grid;
		extendState();
		initListeners();
	}

	private void extendState() {
		grid.addListener(Events.StateRestore, onStateRestore);
	}

	private Listener<ComponentEvent> onStateRestore = new Listener<ComponentEvent>() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleEvent(ComponentEvent be) {
			Map<String, Integer> columnReorderMap = (Map<String, Integer>) grid
					.getState().get("columnReorder");
			if (columnReorderMap != null) {
				Map<String, Integer> oldIndexes = new HashMap<String, Integer>();

				int k = 0;
				for (ColumnConfig cc : grid.getColumnModel().getColumns()) {
					oldIndexes.put(cc.getId(), k);
					k++;
				}

				for (String s : columnReorderMap.keySet()) {
					ColumnModel cm = grid.getColumnModel();
					int oldPos = oldIndexes.get(s);
					cm.moveColumn(oldPos, columnReorderMap.get(s));
				}
			}

			Object o = grid.getState().get("isAutoFill");
			if (o != null) {
				boolean b = (Boolean) o;
				for (ComponentPlugin p : grid.getPlugins())
					if (p instanceof GridForceFitPlugin) {
						GridForceFitPlugin pluging = ((GridForceFitPlugin) p);
						pluging.setAutoFill(b);
						pluging.getAutoFillItem().setChecked(b);
					}
			}
		}
	};

	private Listener<ColumnModelEvent> onColumnMove = new Listener<ColumnModelEvent>() {
		@Override
		public void handleEvent(ColumnModelEvent be) {
			Map<String, Object> state = grid.getState();
			Map<String, Integer> columnReorderMap = null;

			columnReorderMap = new HashMap<String, Integer>();

			int k = 0;
			for (ColumnConfig cc : be.getColumnModel().getColumns()) {
				columnReorderMap.put(cc.getId(), k);
				k++;
			}

			state.put("columnReorder", columnReorderMap);
			grid.saveState();
		}
	};

	public void initListeners() {
		grid.addListener(Events.Reconfigure, onReconfigure);
		updateColumnListners(grid.getColumnModel());
	}

	private Listener<GridEvent<?>> onReconfigure = new Listener<GridEvent<?>>() {
		@Override
		public void handleEvent(GridEvent<?> be) {
			updateColumnListners(grid.getColumnModel());
		}
	};

	private void updateColumnListners(ColumnModel cm) {
		if (grid.getColumnModel() != null) {
			grid.getColumnModel().removeListener(Events.ColumnMove,
					onColumnMove);
			grid.getColumnModel().addListener(Events.ColumnMove, onColumnMove);
		}
	}

}
