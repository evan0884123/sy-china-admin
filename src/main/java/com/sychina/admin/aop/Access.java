package com.sychina.admin.aop;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description: 系统访问日志
 *
 * @author Administrator
 */
@Target({ElementType.METHOD, ElementType.PACKAGE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Access {

    boolean recordLog() default false;
}
