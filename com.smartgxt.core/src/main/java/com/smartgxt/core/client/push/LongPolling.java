package com.smartgxt.core.client.push;

import java.util.ArrayList;

import com.smartgxt.core.client.rpc.RequestProcessor;
import com.smartgxt.core.client.rpc.SuccessEvent;
import com.smartgxt.core.client.rpc.SuccessEvent.SuccessHandler;
import com.smartgxt.core.shared.events.RpcEvents;
import com.smartgxt.core.shared.events.push.ServerPushData;

/**
 * @author Anton Alexeyev
 * 
 */
public class LongPolling extends PoolingRequest {

	private RequestProcessor<Long, ArrayList<ServerPushData>> longPollCall;
	private Long timeout;

	public LongPolling(long timeout) {
		this.timeout = new Long(timeout);
		longPollCall = new RequestProcessor<Long, ArrayList<ServerPushData>>(
				RpcEvents.LongPoll);
		longPollCall
				.addSuccessHandler(new SuccessHandler<ArrayList<ServerPushData>>() {

					@Override
					public void onSuccess(
							SuccessEvent<ArrayList<ServerPushData>> event) {
						ArrayList<ServerPushData> list = event.getItem();
						for (ServerPushData events : list) {
							// Info.display("Push event",
							// "String: " + events.getData());
						}

						longPollCall.execute(LongPolling.this.timeout);
					}
				});

		longPollCall.execute(this.timeout);
	}

	public RequestProcessor<Long, ArrayList<ServerPushData>> getLongPollCall() {
		return longPollCall;
	}

}
