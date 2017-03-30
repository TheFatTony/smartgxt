package com.smartgxt.core.shared.data;

public interface IsConverter<I, O> {

	O convert(I value);

}
