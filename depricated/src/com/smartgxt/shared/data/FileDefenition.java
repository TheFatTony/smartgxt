package com.smartgxt.shared.data;

import java.util.Map;

import com.extjs.gxt.ui.client.data.BaseModelData;

/**
 * @author Anton Alexeyev
 * 
 */
@SuppressWarnings("serial")
public class FileDefenition extends BaseModelData {

	/**
	 * 
	 */

	public FileDefenition() {
		super();
	}

	public FileDefenition(Map<String, Object> properties) {
		super(properties);
	}

	public FileDefenition(String fileId) {
		super();
		setFileId(fileId);
	}

	public void setFileId(String id) {
		set("fileId", id);
	}

	public String getFileId() {
		return get("fileId");
	}
}
