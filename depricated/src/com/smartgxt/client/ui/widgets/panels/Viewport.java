package com.smartgxt.client.ui.widgets.panels;

import com.extjs.gxt.ui.client.widget.MessageBox;

/**
 * @author Anton Alexeyev
 * 
 */
public class Viewport extends com.extjs.gxt.ui.client.widget.Viewport {

	public Viewport() {
		setBorders(false);
	}

	@Override
	protected void onWindowResize(int width, int height) {
		// TODO localization
		if ((width < 320) || (height < 240))
			MessageBox
					.alert("Предупреждение",
							"Размер окна слишком мал для корректного отображения приложения",
							null);
		else
			super.onWindowResize(width, height);
	}

}
