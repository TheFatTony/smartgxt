package com.smartgxt.incubator.client;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.user.client.Window;

public class YMaps {

	static {
		// http: // geocode-maps.yandex.ru/1.x/?geocode=бла бла
		ScriptInjector
				.fromUrl(
						"http://api-maps.yandex.ru/2.0/?load=package.full&mode=debug&lang=ru-RU")
				.setCallback(new Callback<Void, Exception>() {

					@Override
					public void onSuccess(Void result) {
						Window.alert("Script load success.");
					}

					@Override
					public void onFailure(Exception reason) {
						Window.alert("Script load failed.");
					}
				}).inject();
	}

	public YMaps() {
		// TODO Auto-generated constructor stub
	}

}
