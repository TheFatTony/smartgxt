package com.smartgxt.client.security;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.event.ComponentManagerEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.ComponentManager;
import com.google.gwt.core.client.GWT;
import com.smartgxt.shared.data.ComponentDefenition;

/**
 * @author Anton Alexeyev
 * 
 */
public class SecurityRules {

	private static List<String> omitedClasses = new ArrayList<String>();
	private static List<String> allowedClasses = new ArrayList<String>();

	private static List<ComponentDefenition> classes = new ArrayList<ComponentDefenition>();

	public SecurityRules() {
		// GWT.create(SecurityPrototype.class);
		if (GWT.isScript()) {
			ComponentManager.get().addListener(Events.Register,
					onRegisterComponent);
		}
	}

	private Listener<ComponentManagerEvent> onRegisterComponent = new Listener<ComponentManagerEvent>() {
		@Override
		public void handleEvent(ComponentManagerEvent be) {
			final Component c = be.getComponent();

			if (c.getStateId() != null)
				if (!isClassAllowed(c.getStateId()))
					c.removeFromParent();
		}
	};

	public static void preDefineClass(String id, String className) {
		ComponentDefenition cd = new ComponentDefenition();
		cd.setId(id);
		cd.setClassName(className);

		getClasses().add(cd);
	}

	public static List<String> getOmitedClasses() {
		return omitedClasses;
	}

	public static void addOmitedClass(String class_) {
		omitedClasses.add(class_);
	}

	public static List<String> getAllowedClasses() {
		return allowedClasses;
	}

	public static boolean isClassOmited(String class_) {
		for (String s : omitedClasses)
			if (s.equals(class_))
				return true;

		return false;
	}

	public static List<ComponentDefenition> getClasses() {
		return classes;
	}

	public static boolean isClassAllowed(String class_) {
		for (String s : omitedClasses)
			if (s.equals(class_))
				return true;

		for (String s : allowedClasses)
			if (s.equals(class_))
				return true;

		return false;
	}

}
