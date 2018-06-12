package com.fewok.dsl.core.bpm;

import java.util.concurrent.Future;

/**
 * 流程执行
 *
 * @param <IN> 输入
 * @param <R>  执行结果
 * @author notreami on 18/5/31.
 */
public interface ProcessExecutor<IN, R> {

    /**
     * 流程执行
     *
     * @param processor 流程
     * @param input     输入
     * @return 执行结果
     */
    Future<R> execute(Processor<IN, R> processor, IN input);
}
