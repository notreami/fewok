package com.fewok.dsl.core.execute;

import java.util.concurrent.Future;

/**
 * @param <IN> 入参
 * @param <R>  执行结果
 * @author notreami on 18/3/19.
 */
public interface LogicExecutor<IN, R> {
    /**
     * @param processor 流程
     * @param input     入参
     * @return
     */
    Future<R> execute(Processor<IN, R> processor, IN input);
}
