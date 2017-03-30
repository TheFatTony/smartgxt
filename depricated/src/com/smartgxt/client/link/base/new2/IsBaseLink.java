package com.smartgxt.client.link.base.new2;

public interface IsBaseLink {

	void execute();

	void lock();

	void unLock();

	void endExecute();

	int getDelay();

	void setDelay(int delay);

	boolean isLocked();

	// D getLinkData();
	//
	// void setLinkData(D linkData);
}
