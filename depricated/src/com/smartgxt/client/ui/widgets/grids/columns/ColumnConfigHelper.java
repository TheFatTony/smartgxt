package com.smartgxt.client.ui.widgets.grids.columns;

import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.HeaderGroupConfig;

/**
 * @author Anton Alexeyev
 * 
 */
public class ColumnConfigHelper {

	public ColumnConfigHelper() {

	}

	public static void addGroup(ColumnModel cm, String text, int colFrom,
			int colTo) {
		HeaderGroupConfig hg = new HeaderGroupConfig(text, 1, colTo - colFrom
				+ 1);
		cm.addHeaderGroup(0, colFrom, hg);
	}

	public static void addGroup(ColumnModel cm, String text, String colFrom,
			String colTo) {
		int colFromIdx = 0;
		int colToIdx = 0;

		int k = 0;
		for (ColumnConfig cc : cm.getColumns()) {
			if (cc.getId().equals(colFrom))
				colFromIdx = k;
			if (cc.getId().equals(colTo))
				colToIdx = k;
			k++;
		}

		addGroup(cm, text, colFromIdx, colToIdx);
	}

}
