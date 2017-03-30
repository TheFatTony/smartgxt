package com.smartgxt.core.client.prototypes.processing;

/**
 * @author Anton Alexeyev
 * 
 */
public class ProtoFieldType {

	private Object object;
	private String filedName;

	public ProtoFieldType() {
	}

	public ProtoFieldType(Object object, String filedName) {
		setObject(object);
		setFiledName(filedName);
	}

	public <X> X setObject(X object) {
		this.object = object;
		return object;
	}

	@SuppressWarnings("unchecked")
	public <X> X getObject() {
		return (X) object;
	}

	public void setFiledName(String filedName) {
		this.filedName = filedName;
	}

	public String getFiledName() {
		return filedName;
	}
}
