package com.fewok.dsl.core.bpm.execute.future;

import com.fewok.dsl.core.bpm.container.ExecuteResult;
import lombok.AllArgsConstructor;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 同步执行
 *
 * @author notreami on 18/5/31.
 */
@AllArgsConstructor
public class SyncExecuteFuture<R extends ExecuteResult> implements Future<R> {

    private R executeResult;

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return true;
    }

    @Override
    public R get() throws InterruptedException, ExecutionException {
        return executeResult;
    }

    @Override
    public R get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return executeResult;
    }
}
