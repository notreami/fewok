package com.fewok.dsl.core.execute;

/**
 * @param <IN>
 * @param <OUT>
 * @author notreami on 18/3/21.
 */
public interface Processor<IN, OUT> {
    /**
     * @param input
     * @return
     */
    OUT process(IN input);

    /**
     * 同步、异步、并行
     *
     * @return
     */
    default InvokeType getInvokeType() {
        return InvokeType.SYNC;
    }
}
