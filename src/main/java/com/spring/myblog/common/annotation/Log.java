package com.spring.myblog.common.annotation;

import java.lang.annotation.*;

/**
 * @author STARS
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
    boolean value() default true;
}
