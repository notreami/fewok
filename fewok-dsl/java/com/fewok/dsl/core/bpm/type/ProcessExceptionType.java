package com.fewok.dsl.core.bpm.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 流程异常类型
 *
 * @author notreami on 18/5/31.
 */
@Getter
@AllArgsConstructor
public enum ProcessExceptionType {

    /**
     * 流程编排异常
     */
    PROCESS_SEQUENCE_EXCEPTION("流程编排异常"),

    /**
     * Promise获取异常
     */
    PROMISE_EXCEPTION("Promise获取异常"),

    /**
     * ProcessExecutor获取异常
     */
    PROCESS_EXECUTOR_EXCEPTION("ProcessExecutor获取异常");

    private String message;

}
