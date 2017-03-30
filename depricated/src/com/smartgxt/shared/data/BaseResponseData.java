package com.smartgxt.shared.data;

import java.util.Map;

/**
 * @author Anton Alexeyev
 * 
 */
@SuppressWarnings("serial")
public class BaseResponseData extends EncryptedModelData {

	public BaseResponseData() {
	}

	public BaseResponseData(Map<String, Object> properties) {
		super(properties);
	}

	// /**
	// * @gwt.typeArgs <java.lang.String, java.lang.Exception>
	// */
	// @Override
	// public Map<String, Object> getProperties() {
	// return super.getProperties();
	// }

}
