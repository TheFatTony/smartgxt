package com.smartgxt.ui.client.data;

//import com.extjs.gxt.ui.client.data.BaseLoader;
//import com.extjs.gxt.ui.client.data.LoadEvent;
//import com.extjs.gxt.ui.client.data.Loader;
//import com.extjs.gxt.ui.client.event.Listener;
//import com.extjs.gxt.ui.client.event.MessageBoxEvent;
//import com.extjs.gxt.ui.client.widget.MessageBox;
//import com.google.gwt.core.client.Scheduler;
//import com.google.gwt.core.client.Scheduler.ScheduledCommand;

import com.sencha.gxt.data.shared.loader.Loader;

/**
 * @author Anton Alexeyev
 * 
 */
public class LoaderErrorMessage {

	private Loader<?, ?> loader;

	public LoaderErrorMessage(Loader<?, ?> loader) {
		this.loader = loader;

		// // TODO problem with listeners added in runtime
		// // GXT architecture issue
		// // opened thread
		// //
		// http://www.sencha.com/forum/showthread.php?142402-BaseLoader-onLoadFailure-setCanceled&p=632425
		//
		// Scheduler.get().scheduleDeferred(new ScheduledCommand() {
		// public void execute() {
		// getLoader().addListener(Loader.LoadException,
		// new Listener<LoadEvent>() {
		// public void handleEvent(LoadEvent be) {
		// System.out
		// .println("LoaderErrorMessages.LoaderErrorMessages(...).new Listener() {...}.handleEvent()");
		// if (!be.isCancelled()) {
		// Listener<MessageBoxEvent> listener = null;
		// MessageBox
		// .alert(Localization.get()
		// .error_dialog_header(),
		// be.exception.getMessage(),
		// listener);
		// }
		// }
		// });
		// }
		// });
	}

	//
	public Loader<?, ?> getLoader() {
		return loader;
	}

}
