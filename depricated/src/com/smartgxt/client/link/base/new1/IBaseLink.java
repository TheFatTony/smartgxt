package com.smartgxt.client.link.base.new1;

import com.extjs.gxt.ui.client.util.DelayedTask;

public interface IBaseLink<P, C> {

	public void setChild(C child);

	public void setParent(P parent);

	public boolean close();

	public void execute();

	public boolean isLocked();

	public boolean validate();

	public void onExecute();

	public DelayedTask getExecuteTask();

	public void setExecuteTask(DelayedTask executeTask);

	public int getDelay();

	public void setDelay(int delay);

}
