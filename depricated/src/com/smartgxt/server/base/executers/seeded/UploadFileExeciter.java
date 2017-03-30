package com.smartgxt.server.base.executers.seeded;

import java.util.Calendar;

import com.smartgxt.server.base.executers.HttpRequestExecuter;
import com.smartgxt.server.base.sessions.GwtSession;

/**
 * @author Anton Alexeyev
 * 
 */
public class UploadFileExeciter extends HttpRequestExecuter<GwtSession> {

	public UploadFileExeciter() {
	}

	protected void delay(long milliseconds) {
		Calendar now = Calendar.getInstance();
		while (((Calendar.getInstance().getTimeInMillis() - now
				.getTimeInMillis()) < milliseconds)) {
		}
	}

	@Override
	public void execute() throws Exception {
		delay(1000 * 5);
	}

}
