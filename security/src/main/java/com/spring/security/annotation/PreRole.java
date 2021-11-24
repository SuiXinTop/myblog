package com.spring.security.annotation;

import com.spring.common.constant.RoleConstant;

import java.lang.annotation.*;

/**
 * @author STARS
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PreRole {

    boolean value() default true;

    String[] role() default {RoleConstant.ADMIN, RoleConstant.SUPER_ADMIN};
}
