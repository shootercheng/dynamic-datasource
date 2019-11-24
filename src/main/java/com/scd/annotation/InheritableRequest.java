package com.scd.annotation;

import java.lang.annotation.*;

/**
 * 传递父线程 requestheader到子线程
 * @author chengdu
 * @date 2019/11/24
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InheritableRequest {
}
