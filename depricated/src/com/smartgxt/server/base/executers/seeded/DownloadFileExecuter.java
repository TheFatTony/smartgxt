package com.smartgxt.server.base.executers.seeded;

import com.smartgxt.server.base.executers.HttpRequestExecuter;
import com.smartgxt.server.base.sessions.GwtSession;
import com.smartgxt.server.streams.StreamDefinition;

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
