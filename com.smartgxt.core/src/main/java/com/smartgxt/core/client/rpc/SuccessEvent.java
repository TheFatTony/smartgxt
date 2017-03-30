package com.smartgxt.core.client.rpc;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.smartgxt.core.client.rpc.SuccessEvent.SuccessHandler;

public class SuccessEvent<T> extends GwtEvent<SuccessHandler<T>> {

	/**
	 * Handler type.
	 */
	private static Type<SuccessHandler<?>> TYPE;

	/**
	 * Gets the type associated with this event.
	 * 
	 * @return returns the handler type
	 */
	public static Type<SuccessHandler<?>> getType() {
		return TYPE != null ? TYPE : (TYPE = new Type<SuccessHandler<?>>());
	}

	private T item;

	public SuccessEvent(T item) {
		this.item = item;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Type<SuccessHandler<T>> getAssociatedType() {
		return (Type) TYPE;
	}

	public T getItem() {
		return item;
	}

	@Override
	protected void dispatch(SuccessHandler<T> handler) {
		handler.onSuccess(this);

	}

	public interface SuccessHandler<T> extends EventHandler {

		void onSuccess(SuccessEvent<T> event);
	}

}
