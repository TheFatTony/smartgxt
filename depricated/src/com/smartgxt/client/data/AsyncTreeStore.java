package com.smartgxt.client.data;

import java.util.List;

import com.extjs.gxt.ui.client.data.BaseLoader;
import com.extjs.gxt.ui.client.data.BaseRemoteSortTreeLoadConfig;
import com.extjs.gxt.ui.client.data.BaseRemoteSortTreeLoader;
import com.extjs.gxt.ui.client.data.ModelKeyProvider;
import com.extjs.gxt.ui.client.data.TreeModel;
import com.smartgxt.shared.events.RequestType;

/**
 * @author Anton Alexeyev
 * 
 */
public class AsyncTreeStore extends
		com.extjs.gxt.ui.client.store.TreeStore<TreeModel> {

	public AsyncTreeStore(String call) {
		super(new BaseRemoteSortTreeLoader<TreeModel>(
				new RpcProxy<List<TreeModel>>(call) {
				}) {
			@Override
			public boolean hasChildren(TreeModel parent) {
				if (parent != null) {
					Object o = parent.get("is_leaf");
					if (o != null) {
						boolean b = (Boolean) o;
						return !b;
					}
				}
				return true;
			}

			@Override
			protected Object newLoadConfig() {
				BaseRemoteSortTreeLoadConfig config = new BaseRemoteSortTreeLoadConfig();
				return config;
			}
		});
		new LoaderErrorMessage((BaseLoader<?>) getLoader());
		((BaseRemoteSortTreeLoader<?>) getLoader()).setRemoteSort(true);

		setKeyProvider(new ModelKeyProvider<TreeModel>() {
			@Override
			public String getKey(TreeModel model) {
				return model.get("id").toString();
			}
		});
	}

	public AsyncTreeStore(RequestType type) {
		super(new BaseRemoteSortTreeLoader<TreeModel>(
				new RpcProxy<List<TreeModel>>(type) {
				}) {
			@Override
			public boolean hasChildren(TreeModel parent) {
				if (parent != null) {
					Object o = parent.get("is_leaf");
					if (o != null) {
						boolean b = (Boolean) o;
						return !b;
					}
				}
				return true;
			}

			@Override
			protected Object newLoadConfig() {
				BaseRemoteSortTreeLoadConfig config = new BaseRemoteSortTreeLoadConfig();
				return config;
			}
		});
		new LoaderErrorMessage((BaseLoader<?>) getLoader());
		((BaseRemoteSortTreeLoader<?>) getLoader()).setRemoteSort(true);

		setKeyProvider(new ModelKeyProvider<TreeModel>() {
			@Override
			public String getKey(TreeModel model) {
				return model.get("id").toString();
			}
		});
	}
}
