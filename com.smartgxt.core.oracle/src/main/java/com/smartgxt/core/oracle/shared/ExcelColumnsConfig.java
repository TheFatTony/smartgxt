package com.smartgxt.core.oracle.shared;

import java.io.Serializable;

/**
 * @author Anton Alexeyev
 * 
 */
@SuppressWarnings("serial")
public class ExcelColumnsConfig implements Serializable {

	private String id;
	private String header;
	private int index;
	private int width;
	private String format;
	private boolean visible;

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
		this.header = header;
	}

	public String getHeader() {
		return header;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getWidth() {
		return width;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getFormat() {
		return format;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isVisible() {
		return visible;
	}

}
