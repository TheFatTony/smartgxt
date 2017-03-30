package com.smartgxt.core.shared.exceptions;

/**
 * @author Anton Alexeyev
 * 
 */
@SuppressWarnings("serial")
public class NoSessionException extends BaseGwtServletException {

	public NoSessionException() {
	}
	
	
	public NoSessionException(String error) {
		super("Session wasn't created");
	}

}
