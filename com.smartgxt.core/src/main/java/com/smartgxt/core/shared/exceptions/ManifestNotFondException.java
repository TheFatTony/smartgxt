package com.smartgxt.core.shared.exceptions;

/**
 * @author Anton Alexeyev
 * 
 */
@SuppressWarnings("serial")
public class ManifestNotFondException extends BaseGwtServletException {

	public ManifestNotFondException() {
	}
	
	public ManifestNotFondException(String error) {
		super(error);
	}

}
