package com.sky.annotation;

/**
 * 自定义注解，AOP，用于标识公共字段的自动标识
 */

import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface AutoFill
{
    //Update Insert method
    OperationType value();
}
