package com.smartgxt.client.push;

import java.util.ArrayList;

import com.extjs.gxt.ui.client.data.LoadEvent;
import com.extjs.gxt.ui.client.data.Loader;
import com.extjs.gxt.ui.client.event.Listener;
import com.google.gwt.user.client.Timer;
import com.smartgxt.client.data.RpcLoader;
import com.smartgxt.shared.events.RpcEvents;
import com.smartgxt.shared.push.ServerPushData;

/**
 * @author Anton Alexeyev
 * 
 */
public class ShortPolling extends PoolingRequest {

	private RpcLoader<ArrayList<ServerPushData>> shortPollCall;
	private Timer timer;
	private int interval;

	public ShortPolling(int interval) {
		this.interval = interval;
		shortPollCall = new RpcLoader<ArrayList<ServerPushData>>(
				RpcEvents.ShortPoll);
		shortPollCall.addListener(Loader.Load, new Listener<LoadEvent>() {

			@Override
			public void handleEvent(LoadEvent be) {
				ArrayList<ServerPushData> list = be.getData();

				for (ServerPushData event : list) {
					PushEven clientEvent = new PushEven();
					clientEvent.setData(event.getData());
					ServerPush.get().fireEvent(ServerPush.PushEvent,
							clientEvent);
				}

				timer.schedule(ShortPolling.this.interval);
			}
		});
		shortPollCall.addListener(Loader.LoadException,
				new Listener<LoadEvent>() {

					@Override
					public void handleEvent(LoadEvent be) {
						be.setCancelled(true);
					}
				});

		timer = new Timer() {

			@Override
			public void run() {
				shortPollCall.load();
			}
		};
		timer.schedule(100);
	}

	public RpcLoader<ArrayList<ServerPushData>> getShortPollCall() {
		return shortPollCall;
	}

	public Timer getTimer() {
		return timer;
	}

}
