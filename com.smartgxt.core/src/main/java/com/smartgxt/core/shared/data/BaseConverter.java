package com.smartgxt.core.shared.data;

public class BaseConverter<I, O> implements IsConverter<I, O> {

	@SuppressWarnings("unchecked")
	@Override
	public O convert(I value) {
		return (O) value;
	}

}
