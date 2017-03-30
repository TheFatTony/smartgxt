package com.smartgxt.shared;

/**
 * @author Anton Alexeyev
 * 
 */
public enum ConditionTypes {
	EQUALS("="), NOTEQUALS("!="), GREATER(">"), GREATEREQUALS("=>"), LESS("<"), LESSEQUALS(
			"<="), LIKE("LIKE"), NOTLIKE("NOT LIKE"), IN("IN"), NOTIN("NOT IN");

	private final String key;

	private ConditionTypes(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
}
