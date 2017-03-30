package com.smartgxt.incubator.client;

public class ClassCaster {

	public ClassCaster() {
	}

	// public static boolean isAssignableFrom(Object object, Class<?> cls) {
	// System.out.println("object = " + object.getClass());
	// System.out.println("cls = " + cls);
	// if (cls == null) {
	// return false;
	// }
	// if (cls.equals(object.getClass())) {
	// return true;
	// }
	//
	// if (cls.isInterface()){
	// System.out.println("cls.toString() = " + cls.getName());
	// }
	// // System.out.println("isInterface = " + isInterface(object, cls));
	//
	// Class currentSuperClass = object.getClass().getSuperclass();
	// while (currentSuperClass != null) {
	// System.out.println("currentSuperClass = "
	// + currentSuperClass.getName());
	// if (currentSuperClass.equals(cls)) {
	// return true;
	// }
	// currentSuperClass = currentSuperClass.getSuperclass();
	// }
	// return false;
	// }

	public static boolean isAssignableFrom(Object object, Class<?> cls) {
		System.out.println("object = " + object.getClass());
		// System.out.println("cls = " + cls);
		if (cls == null) {
			return false;
		}

		if (cls.isInterface()) {
			System.out.println("cls.isInterface name = " + cls.getName());
			// return false;
		}

		if (cls.equals(object.getClass())) {
			return true;
		}

		// System.out.println("isInterface = " + isInterface(object, cls));

		Class currentSuperClass = object.getClass().getSuperclass();
		while (currentSuperClass != null) {
			System.out.println("currentSuperClass = "
					+ currentSuperClass.getName());
			if (currentSuperClass.equals(cls)) {
				return true;
			}
			currentSuperClass = currentSuperClass.getSuperclass();
		}
		return false;
	}

	static native String noOptimizeFalse(Object obj) /*-{
		$wnd.alert(obj.prototype);
		return obj.prototype;
	}-*/;
}
