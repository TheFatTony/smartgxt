package com.smartgxt.shared.exceptions;

/**
 * @author Anton Alexeyev
 * 
 */
@SuppressWarnings("serial")
public class AccessDeniedException extends BaseGwtServletException {

	public AccessDeniedException() {
	}
	
	public AccessDeniedException(String error) {
		super(error);
	}

}
