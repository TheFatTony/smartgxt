package com.smartgxt.ui.client.tips;

import com.google.gwt.user.client.Timer;
import com.sencha.gxt.widget.core.client.tips.Tip;

public class SimpleToolTip extends Tip {

	protected Timer showTimer;
	private int x;
	private int y;
	private int showDelay = 1000;

	private String bodyHtml;

	public SimpleToolTip() {
		initComponent();
	}

	public SimpleToolTip(TipAppearance appearance) {
		super(appearance);
		initComponent();
	}

	private void initComponent() {
		showTimer = new Timer() {

			@Override
			public void run() {
				SimpleToolTip.super.showAt(x, y);

			}
		};
	}

	@Override
	public void showAt(int x, int y) {
		this.x = x;
		this.y = y;

		showTimer.schedule(showDelay);
	}

	public String getBodyHtml() {
		return bodyHtml;
	}

	public void setBodyHtml(String bodyHtml) {
		this.bodyHtml = bodyHtml;
	}

	public int getShowDelay() {
		return showDelay;
	}

	public void setShowDelay(int showDelay) {
		this.showDelay = showDelay;
	}

	@Override
	protected void updateContent() {
		super.updateContent();
		appearance.updateContent(getElement(), null, bodyHtml);
	}

}
