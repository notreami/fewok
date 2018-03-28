package com.fewok.dsl.core.execute.logic.future;

import com.fewok.dsl.core.execute.ProcessContext;
import com.fewok.dsl.core.execute.Processor;
import com.fewok.dsl.core.execute.logic.LogicResult;

import java.util.concurrent.*;

/**
 * @author notreami on 18/3/26.
 */
public class LogicAsyncFuture<R extends LogicResult> implements Future<R> {

    private Future<R> future;

    public LogicAsyncFuture(Future<R> future) {
        this.future = future;
    }

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


    public static class LogicCallable<P extends ProcessContext, R> implements Callable<R> {
        private Processor<P, R> processor;
        private P processContext;

        public LogicCallable(Processor<P, R> processor, P processContext) {
            this.processor = processor;
            this.processContext = processContext;
        }

        @Override
        public R call() throws Exception {
            return processor.process(processContext);
        }
    }

}
