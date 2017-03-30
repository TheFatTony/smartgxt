package com.smartgxt.client.core;

import com.extjs.gxt.ui.client.core.XDOM;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;

/**
 * @author Anton Alexeyev
 * 
 */
public class Predefined {

	public static Listener<MessageBoxEvent> doReload = new Listener<MessageBoxEvent>() {
		@Override
		public void handleEvent(MessageBoxEvent be) {
			XDOM.reload();
		}
	};
}
