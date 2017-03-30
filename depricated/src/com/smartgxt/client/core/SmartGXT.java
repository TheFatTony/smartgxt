package com.smartgxt.client.core;

import java.util.Map;

import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.core.XDOM;
import com.extjs.gxt.ui.client.state.StateManager;
import com.extjs.gxt.ui.client.util.CSS;
import com.extjs.gxt.ui.client.util.Theme;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.smartgxt.client.managers.Locale;
import com.smartgxt.client.managers.Scale;
import com.smartgxt.client.messages.Localization;
import com.smartgxt.client.prototypes.AsyncCommand;
import com.smartgxt.client.prototypes.Prototype;
import com.smartgxt.client.prototypes.PrototypeUtils;
import com.smartgxt.client.prototypes.processing.ComponentClassAction;
import com.smartgxt.client.prototypes.processing.ComponentFieldAction;
import com.smartgxt.client.prototypes.processing.ComponentMethodAction;
import com.smartgxt.client.state.HtmlStorageException;
import com.smartgxt.client.state.HtmlStorageProvider;
import com.smartgxt.client.util.HtmlHelper;

/**
 * @author Anton Alexeyev
 * 
 */
public class SmartGXT {

	private static HtmlStorageProvider localStoreProvider;

	private static boolean isInited = false;
	private static boolean isValid = true;
	private static String version;
	private static boolean online = false;

	public SmartGXT() {
	}

	public static void init() {
		if (isInited) {
			return;
		}
		isInited = true;

		// if (!Cookies.isCookieEnabled()) {
		// // TODO localization
		// MessageBox
		// .alert("Ошибка",
		// "В ващем браузере отключены Cookies. Активируйте и нажмите Ok.",
		// Predefined.doReload);
		// setValid(false);
		// }
		// else {
		// cookiesProvider = new CookiesProvider();
		// }

		if (isValid) {
			try {
				localStoreProvider = new HtmlStorageProvider();
				StateManager.get().setProvider(localStoreProvider);
			} catch (HtmlStorageException e) {
				MessageBox.alert(Localization.get().error_dialog_header(),
						e.getLocalizedMessage(), Predefined.doReload);
				setValid(false);
			}
		}

		if (isValid) {
			// TODO extract to client config
			if (getLocaleId() == null) {
				switchLocale(Locale.ENGLISH);
				setValid(false);
			}
		}

		if (isValid) {
			setVersion(HtmlHelper.getMetaValue("application-version"));
			GXT.init();
		}

		if (isValid) {
			if ("gray".equals(getThemeId())) {
				CSS.addStyleSheet("sgxt-gray", "smartgxt/css/smartgxt-gray.css");
			} else if ("slate".equals(getThemeId())) {
				CSS.addStyleSheet("sgxt-slate",
						"smartgxt/css/smartgxt-slate.css");
			}
		}

		PrototypeUtils.setAfterObjectCreate(new ComponentClassAction());
		PrototypeUtils.setOnFiledsInterator(new ComponentFieldAction());
		PrototypeUtils.setOnMethodsInterator(new ComponentMethodAction());

		GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			@Override
			public void onUncaughtException(Throwable e) {
				MessageBox.alert(Localization.get().error_dialog_header(),
						e.getMessage(), null);
				e.printStackTrace();
			}
		});

	}

	public static HtmlStorageProvider getLocalStoreProvider() {
		return localStoreProvider;
	}

	@SuppressWarnings("unchecked")
	public static <T> T create(Class<?> classLiteral, Object... args) {
		return (T) ((Prototype) GWT.create(Prototype.class)).newInstance(
				classLiteral, args);
	}

	// @SuppressWarnings("unchecked")
	// public static <T> T create(Class<?> classLiteral) {
	// return (T) ((Prototype) GWT.create(Prototype.class)).newInstance(
	// classLiteral, null);
	// }

	public static void create(Class<?> classLiteral, AsyncCommand command,
			Object... args) {
		((Prototype) GWT.create(Prototype.class)).newInstance(classLiteral,
				command, args);
	}

	// public static void create(Class<?> classLiteral, AsyncCommand command) {
	// ((Prototype) GWT.create(Prototype.class)).newInstance(classLiteral,
	// command, null);
	// }

	// public static <T> T create(Class<?> classLiteral) {
	// final MessageBox messageBox = MessageBox.progress(
	// "Подождите пожалуйста", "Загрузка...", "Загрузка...");
	// try {
	// // TODO localization
	// // ClassHelper<LoginDialog> ch = ClassHelper
	// // .AsClass(LoginDialog.class);
	// // Class<?> cl = ClassHelper
	// // .forName("com.smartgxt.rctest.client.LoginDialog");
	// ClassHelper<?> classHelper = ClassHelper.AsClass(classLiteral);
	//
	// @SuppressWarnings("unchecked")
	// T containerObject = (T) classHelper.newInstance();
	//
	// if (containerObject instanceof Component) {
	// Component containerComponent = (Component) containerObject;
	// if (containerComponent.isStateful())
	// containerComponent.setStateId(classHelper.getType()
	// .getQualifiedSourceName());
	//
	// for (Field f : classHelper.getFields()) {
	// if (f.isPublic()) {
	// Object object = f.getFieldValue(containerObject);
	// if (object instanceof Component) {
	// Component component = (Component) object;
	// if ((component.isStateful())
	// && (component.getStateId() == null)) {
	// component.setStateId(f.getType()
	// .getQualifiedSourceName());
	// }
	// }
	// }
	// }
	// }
	// messageBox.close();
	// return containerObject;
	// } catch (Exception e) {
	// e.printStackTrace();
	// messageBox.close();
	// return null;
	// }
	// }
	// public static void create(final Class<?> classLiteral,
	// final AsyncCommand command) {
	//
	// final MessageBox messageBox = MessageBox.progress(
	// "Подождите пожалуйста", "Загрузка...", "Загрузка...");
	//
	// try {
	// // TODO localization
	// // ClassHelper<LoginDialog> ch = ClassHelper
	// // .AsClass(LoginDialog.class);
	// // Class<?> cl = ClassHelper
	// // .forName("com.smartgxt.rctest.client.LoginDialog");
	// ClassHelper<?> classHelper = ClassHelper.AsClass(classLiteral);
	//
	// // Object containerObject =
	// classHelper.newInstance(command);
	//
	// // if (containerObject instanceof Component) {
	// // Component containerComponent = (Component)
	// // containerObject;
	// // if (containerComponent.isStateful())
	// // containerComponent.setStateId(classHelper.getType()
	// // .getQualifiedSourceName());
	// //
	// // for (Field f : classHelper.getFields()) {
	// // if (f.isPublic()) {
	// // Object object = f.getFieldValue(containerObject);
	// // if (object instanceof Component) {
	// // Component component = (Component) object;
	// // if ((component.isStateful())
	// // && (component.getStateId() == null)) {
	// // component.setStateId(f.getType()
	// // .getQualifiedSourceName());
	// // }
	// // }
	// // }
	// // }
	// // }
	// // command.setObject(containerObject);
	// // command.execute();
	// messageBox.close();
	// } catch (Exception e) {
	// e.printStackTrace();
	// messageBox.close();
	// }
	//
	// }

	public static String getLocaleId() {
		Map<String, Object> map = StateManager.get().getMap("locale");
		if (map != null) {
			return map.get("id").toString();
		}
		return null;
	}

	public static Map<String, Object> getLocale() {
		Map<String, Object> map = StateManager.get().getMap("locale");
		return map;
	}

	public static void switchLocale(Locale locale) {
		StateManager.get().set("locale", locale.asMap());
		XDOM.reload();
	}

	public static void switchScale(Scale scale) {
		StateManager.get().set("scale", scale.asMap());
		XDOM.reload();
	}

	public static void switchTheme(Theme theme) {
		StateManager.get().set(GWT.getModuleBaseURL() + "theme", theme.asMap());
		XDOM.reload();
	}

	public static String getScaleId() {
		Map<String, Object> map = StateManager.get().getMap("scale");
		if (map != null) {
			return map.get("id").toString();
		}
		return null;
	}

	/**
	 * Returns the current theme id.
	 * 
	 * @return the theme id
	 */
	public static String getThemeId() {
		return GXT.getThemeId();
	}

	private static void setValid(boolean isValid) {
		SmartGXT.isValid = isValid;
	}

	public static boolean isValid() {
		return isValid;
	}

	public static void setVersion(String version) {
		SmartGXT.version = version;
	}

	public static String getVersion() {
		return version;
	}

	public static boolean isOnline() {
		return online;
	}

	public static void setOnline(boolean online) {
		SmartGXT.online = online;
	}

}
