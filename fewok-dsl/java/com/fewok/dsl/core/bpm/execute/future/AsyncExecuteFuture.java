package com.fewok.dsl.core.bpm.execute.future;

import com.fewok.dsl.core.bpm.Processor;
import com.fewok.dsl.core.bpm.container.ExecuteResult;
import com.fewok.dsl.core.bpm.container.ProcessContext;
import lombok.AllArgsConstructor;

import java.util.concurrent.*;

/**
 * 异步执行
 *
 * @author notreami on 18/5/31.
 */
@AllArgsConstructor
public class AsyncExecuteFuture<R extends ExecuteResult> implements Future<R> {

    private Future<R> future;

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return future.cancel(mayInterruptIfRunning);
    }

    @Override
    public boolean isCancelled() {
        return future.isCancelled();
    }

    @Override
    public boolean isDone() {
        return future.isDone();
    }

    @Override
    public R get() throws InterruptedException, ExecutionException {
        return future.get();
    }

    @Override
    public R get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return future.get(timeout, unit);
    }

    @AllArgsConstructor
    public static class ExecuteCallable<P extends ProcessContext, R extends ExecuteResult> implements Callable<R> {

        private Processor<P, R> processor;
        private P processContext;

        @Override
        public R call() throws Exception {
            return processor.process(processContext);
        }
    }
}
