package com.smartgxt.core.client.push;

import java.util.ArrayList;

import com.google.gwt.user.client.Timer;
import com.smartgxt.core.client.rpc.RequestProcessor;
import com.smartgxt.core.client.rpc.SuccessEvent;
import com.smartgxt.core.client.rpc.SuccessEvent.SuccessHandler;
import com.smartgxt.core.shared.events.RpcEvents;
import com.smartgxt.core.shared.events.push.ServerPushData;

/**
 * @author Anton Alexeyev
 * 
 */
public class ShortPolling extends PoolingRequest {

	private RequestProcessor<Object, ArrayList<ServerPushData>> shortPollCall;
	private Timer timer;
	private int interval;

	public ShortPolling(int interval) {
		this.interval = interval;
		shortPollCall = new RequestProcessor<Object, ArrayList<ServerPushData>>(
				RpcEvents.ShortPoll);

		shortPollCall
				.addSuccessHandler(new SuccessHandler<ArrayList<ServerPushData>>() {

					@Override
					public void onSuccess(
							SuccessEvent<ArrayList<ServerPushData>> event) {
						ArrayList<ServerPushData> list = event.getItem();
						for (ServerPushData events : list) {
							// Info.display("Push event",
							// "String: " + events.getData());
						}

						timer.schedule(ShortPolling.this.interval);
					}
				});

		timer = new Timer() {

			@Override
			public void run() {
				shortPollCall.execute(null);
			}
		};
		timer.schedule(ShortPolling.this.interval);
	}

	public RequestProcessor<Object, ArrayList<ServerPushData>> getShortPollCall() {
		return shortPollCall;
	}

	public Timer getTimer() {
		return timer;
	}

}
