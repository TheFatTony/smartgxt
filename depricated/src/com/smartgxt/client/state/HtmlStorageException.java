package com.smartgxt.client.state;

/**
 * @author Anton Alexeyev
 * 
 */
@SuppressWarnings("serial")
public class HtmlStorageException extends Exception {

	public HtmlStorageException() {
		super("HTML Storage is not supported in your Browser");
	}

	public HtmlStorageException(String message) {
		super(message);
	}

}
