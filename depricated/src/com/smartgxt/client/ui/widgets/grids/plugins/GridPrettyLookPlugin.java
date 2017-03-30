package com.smartgxt.client.ui.widgets.grids.plugins;

import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.ComponentPlugin;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridSelectionModel;
import com.extjs.gxt.ui.client.widget.tips.QuickTip;

/**
 * @author Anton Alexeyev
 * 
 */
public class GridPrettyLookPlugin implements ComponentPlugin {

	@SuppressWarnings("rawtypes")
	private GridSelectionModel gsm = new GridSelectionModel() {
		@SuppressWarnings("unchecked")
		protected void handleMouseDown(GridEvent e) {
			if (e.isRightClick())
				return;
			super.handleMouseDown(e);
		}
	};

	private com.extjs.gxt.ui.client.widget.grid.Grid<?> grid;

	public GridPrettyLookPlugin() {
	}

	@Override
	public void init(Component component) {
		assert component instanceof Grid<?> : "GridForceFitPlugin can only be used with a Grid.";
		this.grid = (Grid<?>) component;
		applyConfiguration();
	}

	@SuppressWarnings("unchecked")
	public void applyConfiguration() {
		// grid.setId("ds-grid");
		grid.addStyleName("ux-grid");
		grid.setStripeRows(true);
		grid.setColumnLines(true);
		grid.setSelectionModel(gsm);
		grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		grid.setTrackMouseOver(false);
		grid.disableTextSelection(false);
		grid.setColumnReordering(true);
		@SuppressWarnings("unused")
		QuickTip qt = new QuickTip(grid);
	}

}
