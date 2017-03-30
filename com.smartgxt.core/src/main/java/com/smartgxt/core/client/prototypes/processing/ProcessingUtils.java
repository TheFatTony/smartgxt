package com.smartgxt.core.client.prototypes.processing;

/**
 * @author Anton Alexeyev
 * 
 */
public class ProcessingUtils {

	private static PrototypeClassAction afterObjectCreate;

	private static AnyPrototypeAction beforeAnyObjectCreate;

	private static AnyPrototypeAction afterAnyObjectCreate;

	private static PrototypeFieldAction onFiledsInterator;

	private static PrototypeMethodAction onMethodsInterator;

	public ProcessingUtils() {
	}

	public static AnyPrototypeAction getBeforeAnyObjectCreate() {
		return beforeAnyObjectCreate;
	}

	public static void setBeforeAnyObjectCreate(
			AnyPrototypeAction beforeAnyObjectCreate) {
		ProcessingUtils.beforeAnyObjectCreate = beforeAnyObjectCreate;
	}

	public static AnyPrototypeAction getAfterAnyObjectCreate() {
		return afterAnyObjectCreate;
	}

	public static void setAfterAnyObjectCreate(
			AnyPrototypeAction afterAnyObjectCreate) {
		ProcessingUtils.afterAnyObjectCreate = afterAnyObjectCreate;
	}

	public static void setAfterObjectCreate(
			PrototypeClassAction afterObjectCreate) {
		ProcessingUtils.afterObjectCreate = afterObjectCreate;
	}

	public static PrototypeClassAction getAfterObjectCreate() {
		return afterObjectCreate;
	}

	public static void setOnFiledsInterator(
			PrototypeFieldAction onFiledsInterator) {
		ProcessingUtils.onFiledsInterator = onFiledsInterator;
	}

	public static PrototypeFieldAction getOnFiledsInterator() {
		return onFiledsInterator;
	}

	public static void setOnMethodsInterator(
			PrototypeMethodAction onMethodsInterator) {
		ProcessingUtils.onMethodsInterator = onMethodsInterator;
	}

	public static PrototypeMethodAction getOnMethodsInterator() {
		return onMethodsInterator;
	}

	public static void beforeAnyPrototype() {
		if (getBeforeAnyObjectCreate() != null)
			getBeforeAnyObjectCreate().execute();
	}
	
	public static void afterAnyPrototype() {
		if (getAfterAnyObjectCreate() != null)
			getAfterAnyObjectCreate().execute();
	}

	public static void afterObjectCreate(ProtoClassType classType) {
		if (getAfterObjectCreate() != null)
			getAfterObjectCreate().execute(classType);
	}

	public static void onFiledsInterator(ProtoClassType parent,
			ProtoFieldType field) {
		if (getOnFiledsInterator() != null)
			getOnFiledsInterator().execute(parent, field);
	}

	public static void onMethodsInterator(ProtoClassType parent,
			ProtoMethodType method) {
		if (getOnMethodsInterator() != null)
			getOnMethodsInterator().execute(parent, method);
	}

}
