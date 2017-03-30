package com.smartgxt.core.shared.exceptions;

/**
 * @author Anton Alexeyev
 * 
 */
@SuppressWarnings("serial")
public class ApplicationOfflineException extends BaseGwtServletException {

	public ApplicationOfflineException() {
	}
	
	public ApplicationOfflineException(String error) {
		super(error);
	}

}
