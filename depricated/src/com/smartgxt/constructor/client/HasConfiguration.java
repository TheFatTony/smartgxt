package com.smartgxt.constructor.client;

import com.smartgxt.constructor.shared.GXTComponentConfig;

/**
 * @author Anton Alexeyev
 * 
 */
public interface HasConfiguration<T extends GXTComponentConfig> {

	public void setMetaDataCode(String metaDataCode);

	public void setConfig(T config);

	public void addChildFor(HasConfiguration component);

}
