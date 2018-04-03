package com.fewok.dsl.core.execute;

import java.util.concurrent.Future;

/**
 * 逻辑的执行器
 *
 * @param <IN> 入参
 * @param <R>  执行结果
 * @author notreami on 18/3/19.
 */
public interface LogicExecutor<IN, R> {
    /**
     * @param processor 流程
     * @param input     入参
     * @return 执行结果
     */
    Future<R> execute(Processor<IN, R> processor, IN input);
}
