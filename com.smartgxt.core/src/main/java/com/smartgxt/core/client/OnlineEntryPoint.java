package com.smartgxt.core.client;

import java.util.HashMap;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.smartgxt.core.client.rpc.RequestProcessor;
import com.smartgxt.core.shared.data.seeded.ConnectRequestData;
import com.smartgxt.core.shared.data.seeded.ConnectResponseData;
import com.smartgxt.core.shared.data.seeded.LoginRequestData;
import com.smartgxt.core.shared.data.seeded.LoginResponseData;
import com.smartgxt.core.shared.events.RpcEvents;

public abstract class OnlineEntryPoint extends EntryPointPrototype {

	private RequestProcessor<ConnectRequestData, ConnectResponseData> connectCall;
	private RequestProcessor<LoginRequestData, LoginResponseData> loginCall;
	private RequestProcessor<HashMap<String, String>, String> disconnectCall;

	public OnlineEntryPoint(final String title) {
		super(title);

	}

	protected RequestProcessor<ConnectRequestData, ConnectResponseData> getConnectCall() {
		if (connectCall == null) {
			connectCall = new RequestProcessor<ConnectRequestData, ConnectResponseData>(
					RpcEvents.Connect) {
				@Override
				public void onSuccess(ConnectResponseData data) {
					super.onSuccess(data);
					Session.setSesionId(data.getSessionId());
					onConnect(data);
				}

				@Override
				public void onError(Throwable caught) {
					super.onError(caught);
					// SmartGXT.setOnline(false);
				}
			};
		}
		return connectCall;
	}

	public RequestProcessor<LoginRequestData, LoginResponseData> getLoginCall() {
		if (loginCall == null) {
			loginCall = new RequestProcessor<LoginRequestData, LoginResponseData>(
					RpcEvents.Login) {
				@Override
				public void onSuccess(LoginResponseData data) {
					super.onSuccess(data);
					init();
				}

				@Override
				public void onError(Throwable caught) {
					super.onError(caught);
					// SmartGXT.setOnline(false);
				}
			};
		}
		return loginCall;
	}

	public RequestProcessor<HashMap<String, String>, String> getDisconnectCall() {
		if (disconnectCall == null) {
			disconnectCall = new RequestProcessor<HashMap<String, String>, String>(
					RpcEvents.Disconnect) {
				@Override
				public void onSuccess(String data) {
					// super.onSuccess(data);
				}

				@Override
				public void onError(Throwable caught) {
					// super.onError(caught);
					// Window.alert("onError");
				}
			};
		}
		return disconnectCall;
	}

	private void connect() {
		SmartGXT.setOnline(true);
		getConnectCall().execute(new ConnectRequestData());
	}

	private void disconnect() {
		getDisconnectCall().execute(new HashMap<String, String>());
		SmartGXT.setOnline(false);
	}

	public void login(LoginRequestData data) {
		getLoginCall().execute(data);
	}

	@Override
	public final void onModuleLoad() {
		super.onModuleLoad();
		connect();
	}

	@Override
	public void onWindowClosing(ClosingEvent event) {
		disconnect();
		super.onWindowClosing(event);
	}

	public abstract void onConnect(ConnectResponseData data);

}
