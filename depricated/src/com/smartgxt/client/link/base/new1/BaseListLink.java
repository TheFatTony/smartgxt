package com.smartgxt.client.link.base.new1;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.event.Listener;

public abstract class BaseListLink<T extends BaseLink<Object, Object>> extends
		BaseLink<Object, Object> {

	public static final EventType HideChild = new EventType();
	public static final EventType CloseChild = new EventType();
	public static final EventType ExecuteChild = new EventType();

	public class ListenerLink implements Listener<BaseEvent> {

		private final T link;

		public ListenerLink(T link) {
			super();
			this.link = link;
		}

		protected T getLink() {
			return link;
		}

		@Override
		public void handleEvent(BaseEvent be) {
			onHandlerEvent(getLink(), be);
		}

	}

	public void onHandlerEvent(T link, BaseEvent be) {
		if (be.getType().equals(CloseChild)) {

			be.setCancelled(link.close());

		} else if (be.getType().equals(HideChild)) {

			be.setCancelled(link.close());

		} else if (be.getType().equals(ExecuteChild)) {

			link.execute();

		}
	}

	@Override
	public boolean validate() {
		return !isLocked();
	}

	public void setLink(T link) {
		Listener<BaseEvent> lsnr = new ListenerLink(link);
		addListener(HideChild, lsnr);
		addListener(CloseChild, lsnr);
		addListener(ExecuteChild, lsnr);
	}

	@Override
	public void onExecute() {
		fireEvent(ExecuteChild);
	}
}
