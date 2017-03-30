package com.smartgxt.client.prototypes.processing;

import com.smartgxt.client.prototypes.ProtoClassType;
import com.smartgxt.client.prototypes.ProtoMethodType;

/**
 * @author Anton Alexeyev
 * 
 */
public interface PrototypeMethodAction {

	public void execute(ProtoClassType parent, ProtoMethodType method);
}
