package com.smartgxt.client.link.base;

public interface IsBaseLink<D extends IsBaseLinkInfo> {

	boolean execute();

	void lock();

	void unLock();

	void endExecute();

	int getDelay();

	void setDelay(int delay);

	boolean isLocked();

	D getLinkData();

	void setLinkData(D linkData);
}
