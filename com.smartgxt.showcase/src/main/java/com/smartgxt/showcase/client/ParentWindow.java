package com.smartgxt.showcase.client;

import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.smartgxt.core.client.prototypes.Processable;
import com.smartgxt.core.client.prototypes.Prototypeble;
import com.smartgxt.core.client.security.Secured;
import com.smartgxt.incubator.client.shortcuts.Shortcut;
import com.smartgxt.incubator.client.shortcuts.ShortcutRegistration;
import com.smartgxt.incubator.client.shortcuts.ShortcutsManager;
import com.smartgxt.ui.client.desktop.ui.taskbar.IsTaskBarWindow;
import com.smartgxt.ui.client.windowmanager.WindowManagerHelper;
import com.smartgxt.ui.client.windows.Window;

@Prototypeble(deffered=true)
public class ParentWindow extends Window implements IsTaskBarWindow {
	private static final int COLUMN_FORM_WIDTH = 680;

	public ShortcutRegistration s;
	public ShortcutRegistration s1;

	@Processable
	public FramedPanel panel;

	@Processable
	@Secured(disabledFor = { "" }, hiddenFor = { "ESLS" })
	public TextField firstName;
	@Processable
	@Secured(disabledFor = { "JRL" }, hiddenFor = { "" })
	public TextButton tb;

	@Processable
	@Secured(disabledFor = { "JRL" }, hiddenFor = { "" })
	public TextButton tb1;

	public ParentWindow() {
		setHeight(160);
		setHeadingText("Parent Window");
		setMaximizable(true);
		setMinimizable(true);
		panel = new FramedPanel();
		panel.setHeadingText("Form Example");
		panel.setWidth(COLUMN_FORM_WIDTH);
		VerticalLayoutContainer p = new VerticalLayoutContainer();
		panel.add(p);
		add(panel);

		firstName = new TextField();
		firstName.setAllowBlank(false);
		firstName.setWidth(60);
		p.add(new FieldLabel(firstName, "First Name"));

		tb = new TextButton("new child");
		// manager = WindowManager.get();
		s = ShortcutsManager.get().addShortcut(tb, "Ctrl+1");
		tb.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				ChildWindow chw = new ChildWindow();
				chw.setParent(ParentWindow.this);
				chw.show();
			}
		});
		addButton(tb);

		tb1 = new TextButton("new modal child");
		// manager = WindowManager.get();
		s1 = ShortcutsManager.get().addShortcut(tb1, "Ctrl+2");
		tb1.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				ChildWindow chw = new ChildWindow();
				chw.setModal(true);
				chw.setParent(ParentWindow.this);
				chw.show();
			}
		});
		addButton(tb1);

	}

	@Override
	protected void onClose() {
		super.onClose();
		System.out.println("ParentWindow.onClose()");
		s.remove();
		s1.remove();

	}
}
