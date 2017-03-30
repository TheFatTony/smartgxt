package com.smartgxt.client.link.base;

public interface IsBaseLinkInfo {

	<X> X getChild();

	<X> X setChild(X child);

	<X> X getParent();

	<X> X setParent(X parent);

	// public <X> X getLink();
	//
	// public <X> X setLink(X link);

}
