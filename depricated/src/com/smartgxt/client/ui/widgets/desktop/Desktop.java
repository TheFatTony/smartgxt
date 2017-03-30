package com.smartgxt.client.ui.widgets.desktop;

import com.extjs.gxt.ui.client.core.XDOM;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.state.StateManager;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.SeparatorMenuItem;
import com.smartgxt.client.core.SmartGXT;
import com.smartgxt.client.data.RpcProxy;
import com.smartgxt.client.messages.Localization;
import com.smartgxt.client.notifications.Notification;
import com.smartgxt.client.prototypes.AsyncCommand;
import com.smartgxt.client.state.HtmlStorageProvider;
import com.smartgxt.client.ui.widgets.desktop.dashboard.ConfigureDashboardMenuItem;
import com.smartgxt.client.ui.widgets.desktop.dashboard.Dashboard;
import com.smartgxt.client.ui.widgets.layouts.FitLayout;
import com.smartgxt.client.ui.widgets.menus.MenuBarItem;
import com.smartgxt.client.ui.widgets.menus.MenuItem;
import com.smartgxt.client.ui.widgets.panels.ContentPanel;
import com.smartgxt.client.ui.widgets.panels.Viewport;
import com.smartgxt.client.ui.widgets.windows.Dialog;

/**
 * @author Anton Alexeyev
 * 
 */
public class Desktop extends Viewport {

	public ContentPanel desktop;
	public LayoutContainer workArea;
	public ApplicationMenu applicationMenu;
	public TaskBar taskBar;
	public Dashboard dashboard;

	public MenuBarItem options;
	public SeparatorMenuItem separatorAfterScale;
	public MenuBarItem help;

	public SeparatorMenuItem separatorAfterSpecification;
	public MenuItem aboutMenuItem;

	public LocaleSelectorMenuItem localeSelectorMenuItem;
	public ScaleSelectorMenuItem scaleSelectorMenuItem;
	public ThemeSelectorMenuItem themeSelectorMenuItem;
	public ConfigureDashboardMenuItem configureDashboardMenuItem;
	public MenuItem resetStatesMenuItem;
	public MenuItem enableNotificationsMenuItem;
	public MenuItem enableFilesDownload;

	public MenuItem userManualMenuItem;

	private Class<? extends Dialog> aboutDialogClass;

	public Desktop() {
		addStyleName("ux-desktop");
		setStateful(true);
		setLayout(new FitLayout());
		add(getDesktop());
	}

	@Override
	protected void beforeRender() {
		getApplicationMenu().add(getOptions());
		getApplicationMenu().add(getHelp());
		super.beforeRender();
	}

	protected ContentPanel getDesktop() {
		if (desktop == null) {
			desktop = new ContentPanel();
			desktop.setHeaderVisible(false);
			desktop.setLayout(new FitLayout());
			desktop.add(getWorkArea());
			desktop.setTopComponent(getApplicationMenu());
			desktop.setBottomComponent(getTaskBar());
		}
		return desktop;
	}

	protected LayoutContainer getWorkArea() {
		if (workArea == null) {
			workArea = new LayoutContainer();
			workArea.setLayout(new FitLayout());
			// workArea.add(getDashboard());
		}
		return workArea;
	}

	protected ApplicationMenu getApplicationMenu() {
		if (applicationMenu == null) {
			applicationMenu = new ApplicationMenu();
		}
		return applicationMenu;
	}

	protected TaskBar getTaskBar() {
		if (taskBar == null) {
			taskBar = new TaskBar();
			taskBar.setWorkArea(getWorkArea());
		}
		return taskBar;
	}

	protected MenuBarItem getOptions() {
		if (options == null) {
			Menu menu = new Menu();
			menu.add(getLocaleSelectorMenuItem());
			menu.add(getThemeSelectorMenuItem());
			menu.add(getScaleSelectorMenuItem());
			menu.add(getConfigureDashboardMenuItem());
			if (Notification.isSupported())
				menu.add(getEnableNotificationsMenuItem());
			menu.add(getEnableFilesDownload());

			menu.add(getSeparatorAfterScale());
			menu.add(getResetStatesMenuItem());

			menu.addListener(Events.Show, new Listener<MenuEvent>() {
				@Override
				public void handleEvent(MenuEvent be) {
					if (Notification.isSupported()
							&& Notification.isNotificationNotAllowed())
						getEnableNotificationsMenuItem().enable();
					else
						getEnableNotificationsMenuItem().disable();
				}
			});

			options = new MenuBarItem(Localization.get().desktop_Settings(),
					menu);
		}
		return options;
	}

	protected SeparatorMenuItem getSeparatorAfterScale() {
		if (separatorAfterScale == null) {
			separatorAfterScale = new SeparatorMenuItem();
		}
		return separatorAfterScale;
	}

	protected MenuItem getResetStatesMenuItem() {
		if (resetStatesMenuItem == null) {
			resetStatesMenuItem = new MenuItem(Localization.get()
					.desktop_ResetStates());
			resetStatesMenuItem
					.addSelectionListener(new SelectionListener<MenuEvent>() {
						public void componentSelected(MenuEvent ce) {
							((HtmlStorageProvider) StateManager.get()
									.getProvider()).clearAll();
							XDOM.reload();
						}
					});
		}
		return resetStatesMenuItem;
	}

	protected MenuBarItem getHelp() {
		if (help == null) {
			Menu menu = new Menu();
			menu.add(getUserManualMenuItem());
			menu.add(getSeparatorAfterSpecification());
			menu.add(getAbout());
			help = new MenuBarItem(Localization.get().desktop_Help(), menu);
		}
		return help;
	}

	protected MenuItem getUserManualMenuItem() {
		if (userManualMenuItem == null) {
			userManualMenuItem = new MenuItem(Localization.get()
					.desktop_UserManual());
			userManualMenuItem
					.addSelectionListener(new SelectionListener<MenuEvent>() {
						@Override
						public void componentSelected(MenuEvent ce) {
							RpcProxy.downloadFile("manual/"
									+ SmartGXT.getLocaleId() + "/manual.pdf");
						}
					});
		}
		return userManualMenuItem;
	}

	protected SeparatorMenuItem getSeparatorAfterSpecification() {
		if (separatorAfterSpecification == null) {
			separatorAfterSpecification = new SeparatorMenuItem();
		}
		return separatorAfterSpecification;
	}

	protected MenuItem getAbout() {
		if (aboutMenuItem == null) {
			aboutMenuItem = new MenuItem(Localization.get().desktop_About());
			aboutMenuItem
					.addSelectionListener(new SelectionListener<MenuEvent>() {
						public void componentSelected(MenuEvent ce) {
							SmartGXT.create(aboutDialogClass,
									new AsyncCommand() {
										public void execute() {
											Dialog window = (Dialog) getObject();
											window.show();
										}
									});
						}
					});

		}
		return aboutMenuItem;
	}

	public LocaleSelectorMenuItem getLocaleSelectorMenuItem() {
		if (localeSelectorMenuItem == null) {
			localeSelectorMenuItem = new LocaleSelectorMenuItem();
		}
		return localeSelectorMenuItem;
	}

	public ScaleSelectorMenuItem getScaleSelectorMenuItem() {
		if (scaleSelectorMenuItem == null) {
			scaleSelectorMenuItem = new ScaleSelectorMenuItem();
		}
		return scaleSelectorMenuItem;
	}

	public ThemeSelectorMenuItem getThemeSelectorMenuItem() {
		if (themeSelectorMenuItem == null) {
			themeSelectorMenuItem = new ThemeSelectorMenuItem();
		}
		return themeSelectorMenuItem;
	}

	public Dashboard getDashboard() {
		if (dashboard == null) {
			dashboard = new Dashboard();
			dashboard
					.setStateId("com.smartgxt.client.ui.widgets.desktop.Desktop.dashboard");
			dashboard.setStateful(true);
		}
		return dashboard;
	}

	public ConfigureDashboardMenuItem getConfigureDashboardMenuItem() {
		if (configureDashboardMenuItem == null) {
			configureDashboardMenuItem = new ConfigureDashboardMenuItem();
			configureDashboardMenuItem.setDashboard(getDashboard());
		}
		return configureDashboardMenuItem;
	}

	public MenuItem getEnableNotificationsMenuItem() {
		if (enableNotificationsMenuItem == null) {
			enableNotificationsMenuItem = new MenuItem(Localization.get()
					.desktop_EnableNotifications());
			enableNotificationsMenuItem
					.addSelectionListener(new SelectionListener<MenuEvent>() {
						public void componentSelected(MenuEvent ce) {
							Notification.requestPermission();
						}
					});
		}
		return enableNotificationsMenuItem;
	}

	public MenuItem getEnableFilesDownload() {
		if (enableFilesDownload == null) {
			enableFilesDownload = new MenuItem(Localization.get()
					.desktop_EnableFilesDownload());
			enableFilesDownload
					.addSelectionListener(new SelectionListener<MenuEvent>() {
						public void componentSelected(MenuEvent ce) {
							RpcProxy.downloadFile("empty");
						}
					});
		}
		return enableFilesDownload;
	}

	public void setAboutDialogClass(Class<? extends Dialog> aboutDialogClass) {
		this.aboutDialogClass = aboutDialogClass;
	}

}
