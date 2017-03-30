package com.smartgxt.shared.exceptions;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author Anton Alexeyev
 * 
 */
@SuppressWarnings("serial")
public class NoSuchCallException extends BaseGwtServletException implements IsSerializable {
	
	
	public NoSuchCallException() {
	}

	public NoSuchCallException(String error) {
		super(error);
	}

}
