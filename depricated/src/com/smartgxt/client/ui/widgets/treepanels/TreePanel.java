package com.smartgxt.client.ui.widgets.treepanels;

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.util.IconHelper;

/**
 * @author Anton Alexeyev
 * 
 */
public class TreePanel<M extends ModelData> extends
		com.extjs.gxt.ui.client.widget.treepanel.TreePanel<M> {

	public TreePanel(TreeStore<M> store) {
		super(store);
		getStyle().setJointCollapsedIcon(IconHelper.create("tree-joint-close"));
		getStyle().setJointExpandedIcon(IconHelper.create("tree-joint-open"));
		getStyle().setNodeCloseIcon(null);
		getStyle().setNodeOpenIcon(null);
		setTrackMouseOver(false);
	}

}
