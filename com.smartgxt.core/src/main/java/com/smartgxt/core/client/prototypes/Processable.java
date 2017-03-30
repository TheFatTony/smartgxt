package com.smartgxt.core.client.prototypes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * @author Igor Kuchmenko
 * 
 */
// @Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
public @interface Processable {

}
