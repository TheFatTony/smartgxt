package com.smartgxt.constructor.server.configurators;

import java.sql.SQLException;

import com.smartgxt.constructor.shared.GXTComponentConfig;

import oracle.sql.STRUCT;

/**
 * @author Anton Alexeyev
 * 
 */
public abstract class GXTComponentConfigurator<T extends GXTComponentConfig> {

	public GXTComponentConfigurator() {
	}

	public abstract void setOracleObject(T object, STRUCT struct)
			throws SQLException;

}
