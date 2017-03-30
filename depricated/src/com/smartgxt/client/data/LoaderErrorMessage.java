package com.smartgxt.client.data;

import com.extjs.gxt.ui.client.data.BaseLoader;
import com.extjs.gxt.ui.client.data.LoadEvent;
import com.extjs.gxt.ui.client.data.Loader;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgxt.client.messages.Localization;

/**
 * @author Anton Alexeyev
 * 
 */
public class LoaderErrorMessage {

	private BaseLoader<?> loader;

	public LoaderErrorMessage(BaseLoader<?> loader) {
		this.loader = loader;

		// TODO problem with listeners added in runtime
		// GXT architecture issue
		// opened thread
		// http://www.sencha.com/forum/showthread.php?142402-BaseLoader-onLoadFailure-setCanceled&p=632425

		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				getLoader().addListener(Loader.LoadException,
						new Listener<LoadEvent>() {
							@Override
							public void handleEvent(LoadEvent be) {
								System.out
										.println("LoaderErrorMessages.LoaderErrorMessages(...).new Listener() {...}.handleEvent()");
								if (!be.isCancelled()) {
									Listener<MessageBoxEvent> listener = null;
									MessageBox
											.alert(Localization.get()
													.error_dialog_header(),
													be.exception.getMessage(),
													listener);
								}
							}
						});
			}
		});

	}

	public BaseLoader<?> getLoader() {
		return loader;
	}

}
