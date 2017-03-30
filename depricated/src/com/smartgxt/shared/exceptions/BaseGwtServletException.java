package com.smartgxt.shared.exceptions;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author Anton Alexeyev
 * 
 */
@SuppressWarnings("serial")
public class BaseGwtServletException extends Exception implements IsSerializable {

	private String message = null;
	private String exceptionClass = null;

	public BaseGwtServletException() {
		super();
	}
	
	public BaseGwtServletException(String message) {
		super();
		this.message = message;
	}

	// public GwtServletException(String message, BaseExeption seededException)
	// {
	// super();
	// this.message = message;
	// setSeededException(seededException);
	// }

	public BaseGwtServletException(Throwable e) {
		super();
		String class_ = e.getClass().getName();
		setExceptionClass(class_);
		String s = e.getMessage();
		if (s == null)
			this.message = e.getClass().getName();
		else
			this.message = s;
		// setStackTrace(e.getStackTrace());
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getExceptionClass() {
		return exceptionClass;
	}

	public void setExceptionClass(String exceptionClass) {
		this.exceptionClass = exceptionClass;
	}

	// public BaseExeption getSeededException() {
	// return seededException;
	// }
	//
	// public void setSeededException(BaseExeption seededException) {
	// this.seededException = seededException;
	// }

}
