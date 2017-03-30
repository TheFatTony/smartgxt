package com.smartgxt.core.shared.events;

/**
 * @author Anton Alexeyev
 * 
 */
@SuppressWarnings("serial")
public class ReflectionRequestType extends RequestType {

	private String class_;

	public ReflectionRequestType() {
		super(44000, "Reflection");
	}

	public ReflectionRequestType(String class_) {
		super(44000, "Reflection(" + class_ + ")");
		setClassName(class_);
	}

	public void setClassName(String class_) {
		this.class_ = class_;
	}

	public String getClassName() {
		return class_;
	}

}
