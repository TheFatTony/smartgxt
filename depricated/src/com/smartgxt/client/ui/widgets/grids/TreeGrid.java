package com.smartgxt.client.ui.widgets.grids;

import com.extjs.gxt.ui.client.data.TreeModel;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.smartgxt.client.ui.widgets.grids.plugins.GridForceFitPlugin;
import com.smartgxt.client.ui.widgets.grids.plugins.GridHeaderRigthClickPlugin;
import com.smartgxt.client.ui.widgets.grids.plugins.GridPrettyLookPlugin;
import com.smartgxt.client.ui.widgets.grids.plugins.GridRefreshOnColumnResizePlugin;
import com.smartgxt.client.ui.widgets.grids.plugins.GridStateExtendTweaker;

/**
 * @author Anton Alexeyev
 * 
 */
public class TreeGrid extends
		com.extjs.gxt.ui.client.widget.treegrid.TreeGrid<TreeModel> {

	public TreeGrid(TreeStore<TreeModel> store, ColumnModel cm) {
		super(store, cm);
		// setView(new TreeGridView());

		new GridStateExtendTweaker(this);

		addPlugin(new GridForceFitPlugin());
		addPlugin(new GridPrettyLookPlugin());
		addPlugin(new GridHeaderRigthClickPlugin());
		addPlugin(new GridRefreshOnColumnResizePlugin());
	}

}
