package com.smartgxt.client.ui.widgets.grids;

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid.ClicksToEdit;

/**
 * @author Anton Alexeyev
 * 
 */
public class RowEditor<M extends ModelData> extends
		com.extjs.gxt.ui.client.widget.grid.RowEditor<M> {

	public RowEditor() {
		setClicksToEdit(ClicksToEdit.TWO);
	}
}
