/**
 * 
 */
package com.smartgxt.client.data;

import com.extjs.gxt.ui.client.data.ModelData;

/**
 * @author Anton Alexeyev
 * 
 */
public class ModelComparer implements
		com.extjs.gxt.ui.client.data.ModelComparer<ModelData> {

	static private ModelComparer INSTANCE = new ModelComparer();

	@Override
	public boolean equals(ModelData m1, ModelData m2) {
		if (m1.getProperties().size() != m2.getProperties().size())
			return false;

		for (String str : m1.getProperties().keySet()) {
			if (((m1.get(str) != null) && !m1.get(str).equals(m2.get(str)))
					|| ((m1 == null) && (m2 != null)))
				return false;
		}
		return true;
	}

	public static ModelComparer get() {
		return INSTANCE;
	}
}
