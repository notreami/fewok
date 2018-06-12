package com.fewok.dsl.core.bpm;

import com.fewok.dsl.core.bpm.type.InvokeType;

/**
 * 流程定义
 *
 * @param <IN>  输入
 * @param <OUT> 输出
 * @author notreami on 18/5/31.
 */
public interface Processor<IN, OUT> {

    /**
     * 流程
     *
     * @param input 输入
     * @return 输出
     */
    OUT process(IN input);

    /**
     * 流程名称
     *
     * @return 流程名称
     */
    default String getProcessorName() {
        return this.getClass().getSimpleName();
    }

    /**
     * 执行方式（同步、异步、并行）（默认 同步）
     *
     * @return 执行方式
     */
    default InvokeType getInvokeType() {
        return InvokeType.SYNC;
    }

    default boolean isWaitBeforeProcessor() {
        return false;
    }

    /**
     * 重试次数（默认 0 次）
     *
     * @return 重试次数
     */
    default int getRetryCount() {
        return 0;
    }

    /**
     * 每个流程执行的超时时间（默认 1 s）
     *
     * @return 超时时间
     */
    default int getTimeoutMillis() {
        return 1000;
    }
}
