package com.fewok.dsl.core.execute;

/**
 * 流程控制
 *
 * @param <IN>  入参
 * @param <OUT> 出参
 * @author notreami on 18/3/21.
 */
public interface Processor<IN, OUT> {
    /**
     * 流程
     * @param input 入参
     * @return 出参
     */
    OUT process(IN input);

    /**
     * 执行模式（同步、异步、并行）
     *
     * @return 执行模式
     */
    default InvokeType getInvokeType() {
        return InvokeType.SYNC;
    }
}
