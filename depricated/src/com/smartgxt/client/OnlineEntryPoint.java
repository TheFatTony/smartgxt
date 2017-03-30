package com.smartgxt.client;

import java.util.HashMap;

import com.extjs.gxt.ui.client.data.LoadEvent;
import com.extjs.gxt.ui.client.data.Loader;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.state.StateManager;
import com.extjs.gxt.ui.client.util.Util;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.smartgxt.client.core.Predefined;
import com.smartgxt.client.core.Session;
import com.smartgxt.client.core.SmartGXT;
import com.smartgxt.client.data.RpcLoader;
import com.smartgxt.client.managers.CompatibilityManager;
import com.smartgxt.client.state.HtmlStorageProvider;
import com.smartgxt.shared.events.RpcEvents;
import com.smartgxt.shared.seeded.ConnectRequestData;
import com.smartgxt.shared.seeded.ConnectResponseData;
import com.smartgxt.shared.seeded.LoginResponseData;

/**
 * @author Anton Alexeyev
 * 
 */
public abstract class OnlineEntryPoint extends EntryPointPrototype {

	private RpcLoader<ConnectResponseData> connectCall;
	private RpcLoader<ModelData> disconnectCall;
	private String statesVersion;

	public OnlineEntryPoint(final String title) {
		SmartGXT.setOnline(true);
		connectCall = new RpcLoader<ConnectResponseData>(RpcEvents.Connect);

		connectCall.addListener(Loader.Load, new Listener<LoadEvent>() {
			@Override
			public void handleEvent(LoadEvent be) {
				ConnectResponseData data = be.getData();
				setTitle(title + " - " + data.getServerId());
				Session.setSesionId(data.getSessionId());
				disconnectCall = new RpcLoader<ModelData>(RpcEvents.Disconnect);
				disconnectCall.addListener(Loader.LoadException,
						new Listener<LoadEvent>() {

							@Override
							public void handleEvent(LoadEvent be) {
								be.setCancelled(true);
							}
						});
				afterConnect();
			}
		});
		connectCall.addListener(Loader.LoadException,
				new Listener<LoadEvent>() {
					@Override
					public void handleEvent(LoadEvent be) {
						SmartGXT.setOnline(false);
					}
				});
		if (SmartGXT.isValid()) {
			new CompatibilityManager();
			statesVersion = SmartGXT.getLocalStoreProvider().getLocalStore()
					.getItem("sgxt.states.version");
			System.out.println("sending states version " + statesVersion);
			String statesUser = SmartGXT.getLocalStoreProvider()
					.getLocalStore().getItem("sgxt.states.user");
			System.out.println("sending states user " + statesUser);
			connectCall.load(new ConnectRequestData(statesVersion, statesUser));
		}

	}

	public abstract void afterConnect();

	public void loggedIn(LoginResponseData loginResponse) {
		Session.setLogin(loginResponse.getLogin());

		HashMap<String, String> remoteStates = loginResponse.getStates();
		boolean needReload = false;
		if ((remoteStates != null) && (remoteStates.size() > 0)) {
			HtmlStorageProvider provider = ((HtmlStorageProvider) StateManager
					.get().getProvider());
			HashMap<String, String> clentStates = provider.getStates();

			System.out.println("Server version "
					+ remoteStates.get("sgxt.states.version"));
			System.out.println("Client version "
					+ clentStates.get("sgxt.states.version"));

			if (Integer.parseInt(remoteStates.get("sgxt.states.version")) > Integer
					.parseInt(clentStates.get("sgxt.states.version"))) {
				System.out.println("applying states from server");
				if (!Util.equalWithNull(remoteStates.get("theame"),
						clentStates.get("theame"))
						|| !Util.equalWithNull(remoteStates.get("locale"),
								clentStates.get("locale"))
						|| !Util.equalWithNull(remoteStates.get("scale"),
								clentStates.get("scale")))
					needReload = true;
				provider.init(remoteStates);

				// TODO localization
				// TODO restore session without login prompt dialog
				if (needReload)
					MessageBox
							.info("Информация",
									"Ваши настройки обновлены с сервера, "
											+ "необходимо перзапустить приложение.\n"
											+ "Для этого нажмите Ok, приложение перезапустится автоматически.",
									Predefined.doReload);

			}

		}
		if (!needReload)
			init();
	}

	@Override
	public void onClose(CloseEvent<Window> event) {
		super.onClose(event);

	}

	@Override
	public void onWindowClosing(ClosingEvent event) {
		if (SmartGXT.isOnline()) {
			HashMap<String, String> states = null;
			if ((statesVersion != null)
					&& (Integer.parseInt(statesVersion) < Integer
							.parseInt(SmartGXT.getLocalStoreProvider()
									.getLocalStore()
									.getItem("sgxt.states.version")))) {
				states = ((HtmlStorageProvider) StateManager.get()
						.getProvider()).getStates();
			}
			disconnectCall.load(states);
			SmartGXT.setOnline(false);
		}
		super.onWindowClosing(event);
	}
}
