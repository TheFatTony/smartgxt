package com.smartgxt.server.db.jdbc;

import java.io.Serializable;

/**
 * @author Anton Alexeyev
 * 
 */
@SuppressWarnings("serial")
public class StatementParameter implements Serializable {

	// private static final String dir = "direction";
	// private static final String val = "value";
	// private static final String type = "sqltype";
	public static int notDefined = -999;

	private Direction direction;
	private Object value;
	private int sqlType;

	public StatementParameter() {
		setSqlType(notDefined);
		setDirection(Direction.IN);
	}

	public StatementParameter(Direction direction, int sqlType, Object value) {
		setSqlType(sqlType);
		setDirection(direction);
		setValue(value);
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public int getSqlType() {
		return sqlType;
	}

	public void setSqlType(int sqlType) {
		this.sqlType = sqlType;
	}

}
