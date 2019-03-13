package com.suntion.core.aspect;

import java.lang.annotation.*;

/**
 * 定义操作日志注解类
 * @author suns suntion@yeah.net
 * @since 2017年2月21日上午10:02:09
 */
@Target({ElementType.METHOD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogOperation {
    //描述
    String value() default "";
}