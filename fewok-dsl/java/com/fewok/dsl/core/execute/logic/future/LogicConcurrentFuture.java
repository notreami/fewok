package com.fewok.dsl.core.execute.logic.future;

import com.fewok.dsl.core.execute.Processor;
import com.fewok.dsl.core.execute.logic.LogicResult;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author notreami on 18/3/26.
 */
public class LogicConcurrentFuture<R extends LogicResult> implements Future<R> {

    private R result;

    public LogicConcurrentFuture(R result) {
        this.result = result;
    }

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

    public static class LogicRunnable<IN, R extends LogicResult> implements Runnable {
        private Processor<IN, R> processor;
        private IN input;

        public LogicRunnable(Processor<IN, R> processor, IN input) {
            this.processor = processor;
            this.input = input;
        }

        @Override
        public void run() {
            processor.process(input);
        }
    }
}
