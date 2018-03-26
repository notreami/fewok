package com.fewok.dsl.core.annotation;

import org.springframework.stereotype.Component;

/**
 * @author notreami on 18/3/26.
 */
@Component
public @interface Processor {
    String value() default "";
}
