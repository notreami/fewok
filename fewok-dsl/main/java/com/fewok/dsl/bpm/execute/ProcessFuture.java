package com.fewok.dsl.bpm.execute;

import java.util.concurrent.Future;

/**
 * @author notreami on 17/11/23.
 */
public interface ProcessFuture<V> extends Future<V> {
    /**
     * 操作流水
     *
     * @return
     */
    default String getTraceId() {
        return "" + System.currentTimeMillis();
    }
}
