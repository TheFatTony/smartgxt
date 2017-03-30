package com.smartgxt.ui.client.grids;

import java.util.ArrayList;

import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;

public class ModifiableColumnModel<M> extends ColumnModel<M> {

	public ModifiableColumnModel() {
		super(new ArrayList<ColumnConfig<M,?>>());
		configs = new ArrayList<ColumnConfig<M,?>>();
	}
}
