package com.smartgxt.core.client.rpc;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.smartgxt.core.client.rpc.FailureEvent.FailureHandler;

public class FailureEvent<T> extends GwtEvent<FailureHandler<T>> {

	/**
	 * Handler type.
	 */
	private static Type<FailureHandler<?>> TYPE;

	/**
	 * Gets the type associated with this event.
	 * 
	 * @return returns the handler type
	 */
	public static Type<FailureHandler<?>> getType() {
		return TYPE != null ? TYPE : (TYPE = new Type<FailureHandler<?>>());
	}

	private T item;

	public FailureEvent(T item) {
		this.item = item;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Type<FailureHandler<T>> getAssociatedType() {
		return (Type) TYPE;
	}

	public T getItem() {
		return item;
	}

	@Override
	protected void dispatch(FailureHandler<T> handler) {
		handler.onSuccess(this);

	}

	public interface FailureHandler<T> extends EventHandler {

		void onSuccess(FailureEvent<T> event);
	}

}
