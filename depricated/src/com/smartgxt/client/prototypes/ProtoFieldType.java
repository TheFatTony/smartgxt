package com.smartgxt.client.prototypes;

/**
 * @author Anton Alexeyev
 * 
 */
public class ProtoFieldType {

	private Object object;
	// private String className;
	// private Class<?> class_;
	private String filedName;

	public ProtoFieldType() {
	}

	public ProtoFieldType(Object object, String filedName) {
		// public ProtoFieldType(Object object, String className, Class<?>
		// class_,
		// String filedName) {
		setObject(object);
		// setClassName(className);
		// setClass_(class_);
		setFiledName(filedName);
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public Object getObject() {
		return object;
	}

	//
	// public void setClassName(String className) {
	// this.className = className;
	// }
	//
	// public String getClassName() {
	// return className;
	// }
	//
	// public void setClass_(Class<?> class_) {
	// this.class_ = class_;
	// }
	//
	// public Class<?> getClass_() {
	// return class_;
	// }

	public void setFiledName(String filedName) {
		this.filedName = filedName;
	}

	public String getFiledName() {
		return filedName;
	}
}
