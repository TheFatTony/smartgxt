package com.smartgxt.core.client.security;

public @interface Secured {
	String[] hiddenFor();
	String[] disabledFor();
}
