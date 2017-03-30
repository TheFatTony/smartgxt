/**
 * 
 */
package com.smartgxt.server.base.executers;

import java.util.Date;

import com.smartgxt.server.base.sessions.GwtSession;

/**
 * @author Anton Alexeyev
 * 
 */
public abstract class BaseRequestExecuter<T extends GwtSession, Q, S>
		implements Runnable {
	protected S resp;
	protected Q req;
	protected T session;
	private boolean isSystem = false;
	private boolean canceled = false;
	private Throwable ecxeption;

	private Date startedAt;
	private Date endedAt;
	private long elapsedMilliseconds;
	private long elapsedSeconds;

	public BaseRequestExecuter() {
	}

	public T getSession() {
		return session;
	}

	@SuppressWarnings("unchecked")
	public void setSessionObject(Object session) {
		setSession((T) session);
	}

	public void setSession(T session) {
		this.session = session;
	}

	public void run() {
		startedAt = new Date();
		try {
			execute();
		} catch (Throwable e) {
			setEcxeption(e);
		}
		endedAt = new Date();
		elapsedMilliseconds = (endedAt.getTime() - startedAt.getTime());
		elapsedSeconds = elapsedMilliseconds / 1000;
	}

	public abstract void execute() throws Throwable;

	public void cancel() {
		this.canceled = true;
	}

	public boolean isCanceled() {
		return canceled;
	}

	public void setResponse(S resp) {
		this.resp = resp;
	}

	@SuppressWarnings("unchecked")
	public void setResponseObject(Object resp) {
		setResponse((S) resp);
	}

	public S getResponse() {
		return resp;
	}

	public void setRequest(Q req) {
		this.req = req;
	}

	@SuppressWarnings("unchecked")
	public void setRequestObject(Object req) {
		setRequest((Q) req);
	}

	public Q getRequest() {
		return req;
	}

	protected boolean isSystem() {
		return isSystem;
	}

	protected void setSystem(boolean isSystem) {
		this.isSystem = isSystem;
	}

	public Throwable getEcxeption() {
		return ecxeption;
	}

	public void setEcxeption(Throwable ecxeption) {
		this.ecxeption = ecxeption;
	}

	public Date getStartedAt() {
		return startedAt;
	}

	public Date getEndedAt() {
		return endedAt;
	}

	public long getElapsedMilliseconds() {
		return elapsedMilliseconds;
	}

	public long getElapsedSeconds() {
		return elapsedSeconds;
	}

}
