package com.smartgxt.showcase.client;

import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuBarItem;
import com.sencha.gxt.widget.core.client.menu.MenuItem;
import com.smartgxt.core.client.prototypes.Prototypeble;
import com.smartgxt.ui.client.desktop.Desktop;
import com.smartgxt.ui.client.desktop.mainmenu.NewWindowMenuItem;

@Prototypeble(deffered = true)
public class ShowcaseDesktop extends Desktop {

	public ShowcaseDesktop() {
		Menu menu = new Menu();
		MenuItem item1 = new NewWindowMenuItem("LiveGrid Window",
				com.smartgxt.showcase.client.LiveGridWindow.class, true);
		menu.add(item1);

		MenuItem item7 = new NewWindowMenuItem("PagingGrid Window",
				com.smartgxt.showcase.client.PagingGridWindow.class, true);
		menu.add(item7);

		MenuItem item2 = new NewWindowMenuItem("Dependant Windows",
				com.smartgxt.showcase.client.ParentWindow.class, true);
		menu.add(item2);

		MenuItem item3 = new NewWindowMenuItem("ToolBars Window",
				com.smartgxt.showcase.client.ToolBarsWindow.class, true);
		menu.add(item3);

		MenuItem item4 = new NewWindowMenuItem("FileUpload Window",
				com.smartgxt.showcase.client.FileUploadWindow.class, true);
		menu.add(item4);

		MenuItem item5 = new NewWindowMenuItem("YandexMaps Window",
				com.smartgxt.showcase.client.YandexMapsWindow.class, true);
		menu.add(item5);

		MenuItem item6 = new NewWindowMenuItem("Centre for Student Success",
				com.smartgxt.showcase.client.StudentSuccessWindow.class, true);
		menu.add(item6);

		MenuBarItem textButton = new MenuBarItem("File", menu);
		getMainMenu().add(textButton);
	}

}
