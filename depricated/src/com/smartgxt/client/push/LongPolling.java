package com.smartgxt.client.push;

import java.util.ArrayList;

import com.extjs.gxt.ui.client.data.LoadEvent;
import com.extjs.gxt.ui.client.data.Loader;
import com.extjs.gxt.ui.client.event.Listener;
import com.smartgxt.client.data.RpcLoader;
import com.smartgxt.shared.events.RpcEvents;
import com.smartgxt.shared.push.ServerPushData;

/**
 * @author Anton Alexeyev
 * 
 */
public class LongPolling extends PoolingRequest {

	private RpcLoader<ArrayList<ServerPushData>> longPollCall;
	private Long timeout;

	public LongPolling(long timeout) {
		this.timeout = new Long(timeout);
		longPollCall = new RpcLoader<ArrayList<ServerPushData>>(
				RpcEvents.LongPoll);
		longPollCall.addListener(Loader.Load, new Listener<LoadEvent>() {

			@Override
			public void handleEvent(LoadEvent be) {
				ArrayList<ServerPushData> list = be.getData();

				for (ServerPushData event : list) {
					PushEven clientEvent = new PushEven();
					clientEvent.setData(event.getData());
					ServerPush.get().fireEvent(ServerPush.PushEvent,
							clientEvent);
				}

				longPollCall.load(LongPolling.this.timeout);
			}
		});
		longPollCall.addListener(Loader.LoadException,
				new Listener<LoadEvent>() {

					@Override
					public void handleEvent(LoadEvent be) {
						be.setCancelled(true);
					}
				});
		longPollCall.load(this.timeout);
	}

	public RpcLoader<ArrayList<ServerPushData>> getLongPollCall() {
		return longPollCall;
	}

}
