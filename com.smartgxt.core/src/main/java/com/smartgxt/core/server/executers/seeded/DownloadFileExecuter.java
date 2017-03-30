package com.smartgxt.core.server.executers.seeded;

import com.smartgxt.core.server.executers.HttpRequestExecuter;
import com.smartgxt.core.server.sessions.GwtSession;
import com.smartgxt.core.server.streams.StreamDefinition;


public class DownloadFileExecuter extends HttpRequestExecuter<GwtSession> {

	public DownloadFileExecuter() {
	}

	@Override
	public void execute() throws Exception {
		StreamDefinition sd = getSession().getUserStreams()
				.getStreamDefinition(getRequest().getParameter("file_id"));
		getResponse().setContentType(sd.getContentType());
		getResponse().setHeader("Content-Disposition",
				"inline; filename=" + sd.getFileName());

		sd.dumpToOutputStream(getResponse().getOutputStream());
		getResponse().getOutputStream().close();
	}

}
