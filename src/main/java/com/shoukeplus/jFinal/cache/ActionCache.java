package com.shoukeplus.jFinal.cache;

import com.shoukeplus.jFinal.common.AppConstants;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface ActionCache {
	String contentType() default "text/html;charset="+ AppConstants.DEFAULT_ENCODING;
}
