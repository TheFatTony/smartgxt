package com.smartgxt.shared;

import com.extjs.gxt.ui.client.data.BaseModelData;

/**
 * @author Anton Alexeyev
 * 
 */
@SuppressWarnings("serial")
public class ExcelColumnsConfig extends BaseModelData {

	public ExcelColumnsConfig() {

	}

	public ExcelColumnsConfig(String id, String header, int index, int width,
			String format, boolean visible) {
		setId(id);
		setHeader(header);
		setIndex(index);
		setWidth(width);
		setFormat(format);
		setVisible(visible);
	}

	public void setHeader(String header) {
		set("header", header);
	}

	public String getHeader() {
		return get("header");
	}

	public void setIndex(int index) {
		set("index", index);
	}

	public int getIndex() {
		return this.<Integer> get("index");
	}

	public void setWidth(int width) {
		set("width", width);
	}

	public int getWidth() {
		return this.<Integer> get("width");
	}

	public void setFormat(String format) {
		this.set("format", format);
	}

	public String getFormat() {
		return get("format");
	}

	public void setId(String id) {
		set("id", id);
	}

	public String getId() {
		return get("id");
	}

	public void setVisible(boolean visible) {
		set("visible", visible);
	}

	public boolean isVisible() {
		return this.<Boolean> get("visible");
	}

}
