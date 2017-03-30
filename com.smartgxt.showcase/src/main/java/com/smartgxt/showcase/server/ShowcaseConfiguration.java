package com.smartgxt.showcase.server;

import com.smartgxt.core.oracle.server.OracleConfiguration;
import com.smartgxt.core.server.push.ServerPush;
import com.smartgxt.core.server.security.SecurityRules;
import com.smartgxt.core.server.sessions.ConnectEvent;
import com.smartgxt.core.server.sessions.ConnectListener;
import com.smartgxt.core.server.sessions.LoginEvent;
import com.smartgxt.core.server.sessions.LoginListener;
import com.smartgxt.core.server.sessions.SessionManager;
import com.smartgxt.core.shared.events.push.PushEventType;

public class ShowcaseConfiguration extends OracleConfiguration {

	public ShowcaseConfiguration() {
		SessionManager.get().addConnectListener(new ConnectListener() {
			@Override
			public void handleEvent(ConnectEvent evt) {
				ServerPush.create(evt.getSession());
				SecurityRules.getSecurityRules(evt.getSession())
						.addAllowedClass(GetPostsExecuter.class);
			}
		});

		SessionManager.get().addLoginListener(new LoginListener() {

			@Override
			public void handleEvent(LoginEvent evt) {
				ServerPush.get(evt.getSession()).pushEvent(new PushEventType(),
						"Hello user!");

			}
		});
	}
}
