package com.smartgxt.ui.client.data;

import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.smartgxt.ui.shared.KeyBean;

public class ModelWithKeyProvider<T extends KeyBean> implements ModelKeyProvider<T> {

	public ModelWithKeyProvider() {
	}

	@Override
	public String getKey(T item) {
		return  item.getKey();
	}

}
