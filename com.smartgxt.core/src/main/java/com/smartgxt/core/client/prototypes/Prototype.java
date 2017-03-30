package com.smartgxt.core.client.prototypes;

/**
 * @author Anton Alexeyev
 * 
 */
public interface Prototype {
	public <T> T newInstance(Class<T> classLiteral, Object... args);

	public <T> T newInstance(Class<T> classLiteral, final AsyncCommand command,
			Object... args);
	
	public <T> T newInstance(String classLiteral, Object... args);

	public <T> T newInstance(String classLiteral, final AsyncCommand command,
			Object... args);
}
