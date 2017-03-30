package com.smartgxt.client.prototypes.processing;

import com.extjs.gxt.ui.client.widget.Component;
import com.smartgxt.client.prototypes.ProtoClassType;
import com.smartgxt.client.prototypes.ProtoFieldType;

/**
 * @author Anton Alexeyev
 * 
 */
public class ComponentFieldAction implements PrototypeFieldAction {

	@Override
	public void execute(ProtoClassType parent, ProtoFieldType field) {
		if (field != null)
			if (field.getObject() instanceof Component) {
				Component comp = (Component) field.getObject();
				if (comp.isStateful() && (comp.getStateId() == null))
					comp.setStateId(parent.getClassName() + "."
							+ field.getFiledName());
			}
	}

}
