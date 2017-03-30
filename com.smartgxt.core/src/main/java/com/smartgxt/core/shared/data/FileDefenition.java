package com.smartgxt.core.shared.data;

import java.io.Serializable;

/**
 * @author Anton Alexeyev
 * 
 */
@SuppressWarnings("serial")
public class FileDefenition implements Serializable {

	/**
	 * 
	 */
	private String fileId;

	public FileDefenition() {
		super();
	}

	public FileDefenition(String fileId) {
		super();
		setFileId(fileId);
	}

	public void setFileId(String id) {
		fileId = id;
	}

	public String getFileId() {
		return fileId;
	}
}
