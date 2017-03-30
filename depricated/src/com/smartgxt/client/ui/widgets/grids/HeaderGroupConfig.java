package com.smartgxt.client.ui.widgets.grids;

import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.smartgxt.client.ui.widgets.grids.columns.ColumnConfigHelper;

/**
 * @author Anton Alexeyev
 * 
 */
public class HeaderGroupConfig {

	public HeaderGroupConfig(ColumnModel cm, String text, int colFrom, int colTo) {
		ColumnConfigHelper.addGroup(cm, text, colFrom, colTo);
	}

	public HeaderGroupConfig(ColumnModel cm, String text, String colFrom,
			String colTo) {
		ColumnConfigHelper.addGroup(cm, text, colFrom, colTo);
	}

}
