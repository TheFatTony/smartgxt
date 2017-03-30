package com.smartgxt.core.client.prototypes.processing;

/**
 * @author Anton Alexeyev
 * 
 */
public class ProtoMethodType {

	private Object object;
	private String methodName;

	public ProtoMethodType() {
	}

	public ProtoMethodType(Object object, String methodName) {
		setObject(object);
		setMethodName(methodName);
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public Object getObject() {
		return object;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getMethodName() {
		return methodName;
	}

}
