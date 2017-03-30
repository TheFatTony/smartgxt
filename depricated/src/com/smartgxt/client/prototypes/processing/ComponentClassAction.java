package com.smartgxt.client.prototypes.processing;

import com.extjs.gxt.ui.client.widget.Component;
import com.smartgxt.client.prototypes.ProtoClassType;

/**
 * @author Anton Alexeyev
 * 
 */
public class ComponentClassAction implements PrototypeClassAction {

	@Override
	public void execute(ProtoClassType classType) {
		if (classType != null)
			if (classType.getObject() instanceof Component) {
				Component comp = (Component) classType.getObject();
				if (comp.isStateful() && (comp.getStateId() == null))
					comp.setStateId(classType.getClassName());
			}
	}

}
