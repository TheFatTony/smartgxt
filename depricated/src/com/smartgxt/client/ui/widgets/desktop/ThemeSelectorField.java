package com.smartgxt.client.ui.widgets.desktop;

/**
 * @author Anton Alexeyev
 * 
 */
@Deprecated
public class ThemeSelectorField extends
		com.extjs.gxt.ui.client.widget.custom.ThemeSelector {

	public ThemeSelectorField() {
		super();
		setEditable(false);
		setWidth(150);
		// TODO localization
		setToolTip("Выберите тему приложения");
		setDisplayField("name");
		setValueField("id");
	}

}
