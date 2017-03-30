package com.smartgxt.ui.client.security;

import java.util.List;

import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.sencha.gxt.widget.core.client.Component;
import com.sencha.gxt.widget.core.client.event.EnableEvent;
import com.sencha.gxt.widget.core.client.event.EnableEvent.EnableHandler;
import com.smartgxt.core.client.Session;
import com.smartgxt.core.client.security.SecurityRuleApplier;

public class GxtSecurotyRuleApplier extends SecurityRuleApplier {

	public GxtSecurotyRuleApplier() {

	}

	public static void applySecurityRules(Object object,
			List<String> hiddenFor, List<String> disabledFor) {
		if (object instanceof Component) {
			final Component component = (Component) object;

			for (String s : Session.getRoles()) {
				for (String h : hiddenFor) {
					if (s.equals(h)) {
						component.addAttachHandler(new Handler() {

							@Override
							public void onAttachOrDetach(AttachEvent event) {
								component.hide();
							}
						});
					}
				}

				for (String h : disabledFor) {
					if (s.equals(h)) {
						component.disable();
						component.addEnableHandler(new EnableHandler() {
							@Override
							public void onEnable(EnableEvent event) {
								component.disable();
							}
						});
					}
				}

			}

			System.out.println("hiddenFor=" + hiddenFor);
			System.out.println("disabledFor=" + disabledFor);
		}

	}
}
