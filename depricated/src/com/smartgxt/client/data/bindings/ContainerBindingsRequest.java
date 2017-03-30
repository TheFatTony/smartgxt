package com.smartgxt.client.data.bindings;

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.widget.Container;
import com.smartgxt.client.data.RpcLoader;

/**
 * @author Anton Alexeyev
 * 
 */
public class ContainerBindingsRequest<D> extends ContainerBindings {

	private RpcLoader<D> actionLoader;
	private String callId;

	public ContainerBindingsRequest(Container<?> container, String callId) {
		super(container);
		actionLoader = new RpcLoader(callId);
		this.callId = callId;
	}

	public void process() {
		ModelData d = getData();
		// if (d != null) {
		// ReflectionRequestData data = new ReflectionRequestData(callId);
		// for (String s : d.getPropertyNames()) {
		// data.set(s, d.get(s));
		// }
		// actionLoader.load(data);
		// }
	}

}
