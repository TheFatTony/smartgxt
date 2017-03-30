package com.smartgxt.showcase.client;

import com.sencha.gxt.widget.core.client.box.AutoProgressMessageBox;
import com.smartgxt.core.client.OnlineEntryPoint;
import com.smartgxt.core.client.SmartGXT;
import com.smartgxt.core.client.prototypes.AsyncCommand;
import com.smartgxt.core.client.prototypes.processing.AnyPrototypeAction;
import com.smartgxt.core.client.prototypes.processing.ProcessingUtils;
import com.smartgxt.core.client.security.SecurityManager;
import com.smartgxt.core.shared.data.seeded.ConnectResponseData;
import com.smartgxt.incubator.client.oracle.LoginDialog;
import com.smartgxt.ui.client.CompatibilityManager;
import com.smartgxt.ui.client.security.GxtSecurotyRuleApplier;
import com.smartgxt.ui.client.windowmanager.WindowManagerHelper;

public class Showcase extends OnlineEntryPoint {

	AutoProgressMessageBox box;

	public Showcase() {
		super("Showcase");
		CompatibilityManager.enable();
		WindowManagerHelper.replaceWindowManager();
		SecurityManager.setRuleApplier(new GxtSecurotyRuleApplier());

		ProcessingUtils.setBeforeAnyObjectCreate(new AnyPrototypeAction() {

			@Override
			public void execute() {
				box = new AutoProgressMessageBox("Loading", "");
				box.setProgressText("Loading...");
				box.auto();
				box.show();

			}
		});

		ProcessingUtils.setAfterAnyObjectCreate(new AnyPrototypeAction() {

			@Override
			public void execute() {
				box.hide();
				box = null;
			}
		});

	}

	@Override
	public void onConnect(ConnectResponseData data) {
		LoginDialog loginDialog = new LoginDialog();
		loginDialog.setEntryPoint(this);
		loginDialog.show();
		loginDialog.center();
		// LoginRequestData requestData = new LoginRequestData("hr", "oracle",
		// null);
		// login(requestData);
	}

	@Override
	public void init() {
		// new ShortPolling(60000);
		// Session.getRoles().add("ESLS");
		// Session.getRoles().add("JRL");
		// SmartGXT.create(Desktop.class, new AsyncCommand() {
		//
		// @Override
		// public void execute() {

		SmartGXT.create(com.smartgxt.showcase.client.ShowcaseDesktop.class,
				new AsyncCommand() {

					@Override
					public void execute() {
					}
				});

		// mm.add(new MenuBarItem("File" + i, ));

		// }
		//
		// });

	}

}
