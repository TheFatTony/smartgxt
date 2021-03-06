package com.smartgxt.core.server.push;

import java.util.ArrayList;
import java.util.Calendar;

import com.smartgxt.core.server.executers.RpcRequestExecuter;
import com.smartgxt.core.server.sessions.GwtSession;
import com.smartgxt.core.shared.events.push.ServerPushData;

/**
 * @author Anton Alexeyev
 * 
 */
public class LongPollExecuter extends
		RpcRequestExecuter<GwtSession, Long, ArrayList<ServerPushData>> {

	public LongPollExecuter() {
		setSystem(true);
	}

	protected void delay(long milliseconds, ArrayList<ServerPushData> list)
			throws InterruptedException {
		Calendar now = Calendar.getInstance();
		while ((!isCanceled())
				&& ((Calendar.getInstance().getTimeInMillis() - now
						.getTimeInMillis()) < milliseconds)
				&& (list.size() == 0)) {
			// TODO check access
			// Thread.currentThread().sleep(500);
		}
	}

	@Override
	public void execute() throws Throwable {
		ArrayList<ServerPushData> list = ServerPush.get(getSession())
				.getCurrentPushEvents();
		delay(getRequest(), list);
		setResponse(list);
	}

}
