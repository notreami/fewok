package com.fewok.dsl.core.execute.logic.future;

import com.fewok.dsl.core.execute.logic.LogicResult;
import lombok.AllArgsConstructor;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author notreami on 18/3/26.
 */
@AllArgsConstructor
public class LogicSyncFuture<R extends LogicResult> implements Future<R> {
    private R result;

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
        return result;
    }

    @Override
    public R get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return result;
    }
}
