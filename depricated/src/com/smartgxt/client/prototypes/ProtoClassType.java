package com.smartgxt.client.prototypes;

/**
 * @author Anton Alexeyev
 * 
 */
public class ProtoClassType {

	private Object object;
	private String className;
	private Class<?> class_;

	public ProtoClassType() {
	}

	public ProtoClassType(Object object, String className, Class<?> class_) {
		setObject(object);
		setClassName(className);
		setClass_(class_);
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public Object getObject() {
		return object;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassName() {
		return className;
	}

	public void setClass_(Class<?> class_) {
		this.class_ = class_;
	}

	public Class<?> getClass_() {
		return class_;
	}
}
