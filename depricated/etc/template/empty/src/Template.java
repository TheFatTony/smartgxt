package @package@.client;

import com.extjs.gxt.ui.client.data.LoadEvent;
import com.extjs.gxt.ui.client.data.Loader;
import com.extjs.gxt.ui.client.event.Listener;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgxt.client.OnlineEntryPoint;
import com.smartgxt.client.core.SmartGXT;
import com.smartgxt.client.data.RpcLoader;
import com.smartgxt.client.managers.Locale;
import com.smartgxt.client.managers.LocaleManager;
import com.smartgxt.client.ui.widgets.desktop.Desktop;
import com.smartgxt.shared.events.RpcEvents;
import com.smartgxt.shared.seeded.LoginRequestData;
import com.smartgxt.shared.seeded.LoginResponseData;

public class @name@ extends OnlineEntryPoint {

	public Desktop viewport;
	private RpcLoader<LoginResponseData> login;
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public @name@ () {
		super("@name@");
		LocaleManager.register(Locale.RUSSIAN);

		login = new RpcLoader<LoginResponseData>(RpcEvents.Login);
		login.addListener(Loader.Load, new Listener<LoadEvent>() {
			@Override
			public void handleEvent(LoadEvent be) {
				loggedIn((LoginResponseData) be.getData());
			}
		});
		
	}

	@Override
	public void afterConnect() {
		login.load(new LoginRequestData("sgxt", "sgxt123", SmartGXT.getLocale()));
	}

	@Override
	public void init() {
		viewport = SmartGXT.create(@name@Desktop.class);
		RootPanel.get().add(viewport);
	}
}
