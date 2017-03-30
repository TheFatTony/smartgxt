package com.smartgxt.client.push;

import com.extjs.gxt.ui.client.event.BaseEvent;

/**
 * @author Anton Alexeyev
 * 
 */
public class PushEven extends BaseEvent {

	private Object data;

	public PushEven() {
		super(ServerPush.PushEvent);
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
