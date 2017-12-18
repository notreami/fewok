package com.fewok.dsl.bpm.annotation;

import org.springframework.stereotype.Component;

/**
 * 流程注入
 * @author notreami on 17/11/23.
 */
@Component
public @interface Processor {
    String value() default "";
}