package com.smartgxt.core.client.prototypes.processing;


/**
 * @author Anton Alexeyev
 * 
 */
public interface PrototypeFieldAction {

	public void execute(ProtoClassType parent, ProtoFieldType field);
}
