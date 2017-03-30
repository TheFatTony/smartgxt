package com.smartgxt.shared.events;

/**
 * @author Anton Alexeyev
 * 
 */
public class RpcEvents {

	public static RequestType Connect = new RequestType(44001, "Connect");

	public static RequestType Disconnect = new RequestType(44002, "Disconnect");

	public static RequestType Download = new RequestType(44003, "Download");

	public static RequestType Upload = new RequestType(44004, "Upload");

	public static RequestType Login = new RequestType(44005, "Login");

	public static RequestType LongPoll = new RequestType(44006, "LongPoll");

	public static RequestType ShortPoll = new RequestType(44007, "ShortPoll");

}
