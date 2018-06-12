package com.fewok.dsl.core.bpm.annotation;

import org.springframework.stereotype.Component;

/**
 * 流程单元
 *
 * @author notreami on 18/5/31.
 */
@Component
public @interface Processor {
    String value() default "";
}
