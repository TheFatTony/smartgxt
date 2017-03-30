package com.smartgxt.core.server.executers.seeded;

import java.util.Calendar;

import com.smartgxt.core.server.executers.HttpRequestExecuter;
import com.smartgxt.core.server.sessions.GwtSession;

/**
 * @author Anton Alexeyev
 * 
 */
public class UploadFileExecuter extends HttpRequestExecuter<GwtSession> {

	public UploadFileExecuter() {
		setSystem(true);
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
