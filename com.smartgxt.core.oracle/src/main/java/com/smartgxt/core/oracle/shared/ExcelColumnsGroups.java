package com.smartgxt.core.oracle.shared;

import java.io.Serializable;

/**
 * @author Anton Alexeyev
 * 
 */
@SuppressWarnings("serial")
public class ExcelColumnsGroups implements Serializable {

	private int column;
	private int columnsSpan;
	private String text;
	private String toCell;
	private String fromCell;

	public ExcelColumnsGroups() {
	}

	public ExcelColumnsGroups(int column, int columnsSpan, String text) {
		setColumn(column);
		setColumnsSpan(columnsSpan);
		setText(text);
		setToCell("");
		setFromCell("");
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getColumn() {
		return column;
	}

	public void setColumnsSpan(int columnsSpan) {
		this.columnsSpan = columnsSpan;
	}

	public int getColumnsSpan() {
		return columnsSpan;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setFromCell(String fromCell) {
		this.fromCell = fromCell;
	}

	public String getFromCell() {
		return fromCell;
	}

	public void setToCell(String toCell) {
		this.toCell = toCell;
	}

	public String getToCell() {
		return toCell;
	}
}
