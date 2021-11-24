package com.spring.security.annotation;

import java.lang.annotation.*;

/**
 * @author xxx
 * @create 2021-10-16
 */

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PreAuth {
    boolean value() default true;
}
