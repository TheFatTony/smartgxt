package com.smartgxt.showcase.client;

import com.sencha.gxt.widget.core.client.Window;
import com.smartgxt.core.client.prototypes.Prototypeble;
import com.smartgxt.ui.client.desktop.ui.taskbar.IsTaskBarWindow;

@Prototypeble(deffered=true)
public class YandexMapsWindow extends Window implements IsTaskBarWindow {

	public YandexMapsWindow() {
		initGUI();
	}

	public YandexMapsWindow(WindowAppearance appearance) {
		super(appearance);
		initGUI();
	}

	public void initGUI() {
		setHeadingText("YandexMaps Window");
		setId("YMapsID");
		// createMap();
	}

	public native void createMap() /*-{
		var myMap = new $wnd.ymaps.Map("YMapsID", {
			// Центр карты
			center : [ 55.76, 37.64 ],
			// Коэффициент масштабирования
			zoom : 10,
			// Тип карты
			type : "yandex#satellite"
		});
	}-*/;
}
