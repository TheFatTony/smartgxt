package com.smartgxt.incubator.client.oracle;

import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.smartgxt.core.client.OnlineEntryPoint;
import com.smartgxt.core.client.rpc.FailureEvent;
import com.smartgxt.core.client.rpc.FailureEvent.FailureHandler;
import com.smartgxt.core.client.rpc.SuccessEvent;
import com.smartgxt.core.client.rpc.SuccessEvent.SuccessHandler;
import com.smartgxt.core.shared.data.seeded.LoginRequestData;
import com.smartgxt.core.shared.data.seeded.LoginResponseData;
import com.smartgxt.incubator.client.shortcuts.ShortcutRegistration;
import com.smartgxt.incubator.client.shortcuts.ShortcutsManager;
import com.smartgxt.ui.client.windows.Window;

public class LoginDialog extends Window {

	private OnlineEntryPoint entryPoint;

	private FramedPanel loginPanel;
	private VerticalLayoutContainer fieldsContainer;

	private TextField loginField;
	private PasswordField passwordField;

	private TextButton loginButton;
	
	private ShortcutRegistration enterButton;

	public LoginDialog() {
		super();
		initGUI();
	}


	private void initGUI() {
		setPixelSize(350, 150);
		setDraggable(false);
		setResizable(false);
		getLoginPanel().add(getFieldsContainer());

		getFieldsContainer().add(new FieldLabel(getLoginField(), "Login"),
				new VerticalLayoutData(1, -1));
		getFieldsContainer().add(
				new FieldLabel(getPasswordField(), "Password"),
				new VerticalLayoutData(1, -1));

		add(getFieldsContainer());
		addButton(getLoginButton());
	}

	public FramedPanel getLoginPanel() {
		if (loginPanel == null) {
			loginPanel = new FramedPanel();
			loginPanel.setHeaderVisible(false);
		}
		return loginPanel;
	}

	public VerticalLayoutContainer getFieldsContainer() {
		if (fieldsContainer == null) {
			fieldsContainer = new VerticalLayoutContainer();
		}
		return fieldsContainer;
	}

	public TextField getLoginField() {
		if (loginField == null) {
			loginField = new TextField();
			loginField.setValue("hr");
			loginField.setAllowBlank(false);
		}
		return loginField;
	}

	public PasswordField getPasswordField() {
		if (passwordField == null) {
			passwordField = new PasswordField();
			passwordField.setValue("oracle");
			passwordField.setAllowBlank(false);
		}
		return passwordField;
	}

	private SuccessHandler<LoginResponseData> loginCallSuccessHandler = new SuccessHandler<LoginResponseData>() {

		@Override
		public void onSuccess(SuccessEvent<LoginResponseData> event) {
			hide();
		}
	};

	private FailureHandler<Throwable> loginCallFailureHandler = new FailureHandler<Throwable>() {

		@Override
		public void onSuccess(FailureEvent<Throwable> event) {
			unmask();
			AlertMessageBox alertMessageBox = new AlertMessageBox("Error", event.getItem()
					.getLocalizedMessage());
			alertMessageBox.show();
		}
	};

	private SelectHandler loginButtonSelectHandler = new SelectHandler() {

		@Override
		public void onSelect(SelectEvent event) {
			mask("Loading...");
			LoginRequestData requestData = new LoginRequestData(getLoginField()
					.getValue(), getPasswordField().getValue(), null);
			getEntryPoint().getLoginCall().addSuccessHandler(
					loginCallSuccessHandler);
			getEntryPoint().getLoginCall().addFailureHandler(
					loginCallFailureHandler);
			getEntryPoint().login(requestData);
		}
	};

	public TextButton getLoginButton() {
		if (loginButton == null) {
			loginButton = new TextButton();
			loginButton.setText("Login");
			loginButton.addSelectHandler(loginButtonSelectHandler);
			enterButton = ShortcutsManager.get().addShortcut(loginButton, "Enter");
		}
		return loginButton;
	}

	public OnlineEntryPoint getEntryPoint() {
		return entryPoint;
	}

	public void setEntryPoint(OnlineEntryPoint entryPoint) {
		this.entryPoint = entryPoint;
	}
	
	@Override
	protected void onClose() {
		
		super.onClose();
	}

}
