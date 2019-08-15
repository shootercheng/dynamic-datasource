package com.scd.annotation;

import java.lang.annotation.*;

/**
 * METHOD > TYPE
 * @author chengdu
 * @date 2019/8/13.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSourceRouting {

    String dsname() default "";

    String dsparam() default "";
}
