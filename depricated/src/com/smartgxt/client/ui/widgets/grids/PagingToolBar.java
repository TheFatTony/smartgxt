package com.smartgxt.client.ui.widgets.grids;

/**
 * @author Anton Alexeyev
 * 
 */
public class PagingToolBar extends
		com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar {

	public PagingToolBar(int pageSize) {
		super(pageSize);
		setReuseConfig(false);
	}

}
