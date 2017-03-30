package com.smartgxt.client.ui.widgets.menus;

/**
 * @author Anton Alexeyev
 * 
 */
public class MenuBar extends com.extjs.gxt.ui.client.widget.menu.MenuBar {

	private boolean enableOverflow = true;

	public MenuBar() {
		setLayout(new MenuBarLayout());
	}

	/**
	 * Returns true if overflow is enabled.
	 * 
	 * @return the overflow state
	 */
	public boolean isEnableOverflow() {
		return enableOverflow;
	}

	/**
	 * True to show a drop down icon when the available width is less than the
	 * required width (defaults to true).
	 * 
	 * @param enableOverflow
	 *            true to enable overflow support
	 */
	public void setEnableOverflow(boolean enableOverflow) {
		this.enableOverflow = enableOverflow;
	}

	// @Override
	// public boolean add(MenuBarItem item) {
	// boolean tmp = super.add(item);
	// if (rendered)
	// doLayout();
	// return tmp;
	// }
}
