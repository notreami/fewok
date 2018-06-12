package com.fewok.dsl.core.bpm.exception;

import com.fewok.dsl.core.bpm.type.ProcessExceptionType;

/**
 * 流程异常
 *
 * @author notreami on 18/5/31.
 */
public class ProcessException extends RuntimeException {

    public ProcessException(ProcessExceptionType processExceptionType) {
        super(processExceptionType.getMessage());
    }
}
