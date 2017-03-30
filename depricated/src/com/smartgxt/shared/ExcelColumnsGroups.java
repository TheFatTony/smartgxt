package com.smartgxt.shared;

import com.extjs.gxt.ui.client.data.BaseModelData;

/**
 * @author Anton Alexeyev
 * 
 */
@SuppressWarnings("serial")
public class ExcelColumnsGroups extends BaseModelData {

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
		set("column", column);
	}

	public int getColumn() {
		return this.<Integer> get("column");
	}

	public void setColumnsSpan(int columnsSpan) {
		set("columnsSpan", columnsSpan);
	}

	public int getColumnsSpan() {
		return this.<Integer> get("columnsSpan");
	}

	public void setText(String text) {
		set("text", text);
	}

	public String getText() {
		return get("text");
	}

	public void setFromCell(String fromCell) {
		set("fromCell", fromCell);
	}

	public String getFromCell() {
		return get("fromCell");
	}

	public void setToCell(String toCell) {
		set("toCell", toCell);
	}

	public String getToCell() {
		return get("toCell");
	}

}
