package com.smartgxt.client.prototypes.processing;

import com.extjs.gxt.ui.client.widget.Component;
import com.smartgxt.client.prototypes.ProtoClassType;
import com.smartgxt.client.prototypes.ProtoMethodType;

/**
 * @author Anton Alexeyev
 * 
 */
public class ComponentMethodAction implements PrototypeMethodAction {

	@Override
	public void execute(ProtoClassType parent, ProtoMethodType method) {
		if (method != null)
			if (method.getObject() instanceof Component) {
				Component comp = (Component) method.getObject();
				if (comp.isStateful() && (comp.getStateId() == null)) {
					String str = method.getMethodName();
					if (str.indexOf("get") == 0)
						str = str.substring(3);
					comp.setStateId(parent.getClassName() + "." + str);
				}
			}
	}

}
