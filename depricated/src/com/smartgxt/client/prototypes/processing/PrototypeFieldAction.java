package com.smartgxt.client.prototypes.processing;

import com.smartgxt.client.prototypes.ProtoClassType;
import com.smartgxt.client.prototypes.ProtoFieldType;

/**
 * @author Anton Alexeyev
 * 
 */
public interface PrototypeFieldAction {

	public void execute(ProtoClassType parent, ProtoFieldType field);
}
