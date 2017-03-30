package com.smartgxt.client.prototypes;

import com.smartgxt.client.prototypes.processing.PrototypeClassAction;
import com.smartgxt.client.prototypes.processing.PrototypeFieldAction;
import com.smartgxt.client.prototypes.processing.PrototypeMethodAction;

/**
 * @author Anton Alexeyev
 * 
 */
public class PrototypeUtils {

	private static PrototypeClassAction afterObjectCreate;

	private static PrototypeFieldAction onFiledsInterator;

	private static PrototypeMethodAction onMethodsInterator;

	public PrototypeUtils() {
	}

	public static void setAfterObjectCreate(
			PrototypeClassAction afterObjectCreate) {
		PrototypeUtils.afterObjectCreate = afterObjectCreate;
	}

	public static PrototypeClassAction getAfterObjectCreate() {
		return afterObjectCreate;
	}

	public static void setOnFiledsInterator(
			PrototypeFieldAction onFiledsInterator) {
		PrototypeUtils.onFiledsInterator = onFiledsInterator;
	}

	public static PrototypeFieldAction getOnFiledsInterator() {
		return onFiledsInterator;
	}

	public static void setOnMethodsInterator(
			PrototypeMethodAction onMethodsInterator) {
		PrototypeUtils.onMethodsInterator = onMethodsInterator;
	}

	public static PrototypeMethodAction getOnMethodsInterator() {
		return onMethodsInterator;
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
