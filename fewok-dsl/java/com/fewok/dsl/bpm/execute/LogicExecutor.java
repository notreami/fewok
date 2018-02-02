package com.fewok.dsl.bpm.execute;

import com.fewok.common.common.BaseInput;
import com.fewok.common.common.CommonInput;
import com.fewok.common.common.CommonOutput;
import com.fewok.dsl.bpm.common.ProcessDefinition;

import java.util.concurrent.*;

/**
 * @author notreami on 17/11/23.
 */
public class LogicExecutor<IN extends BaseInput, OUT> implements IExecutor {
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(10);

    @Override
    public ProcessFuture<CommonOutput> execute(ProcessDefinition processDefinition, CommonInput commonInput) {
        if (processDefinition.isAsync()) {
            return new LogicFutureWrapper(getExecutor().submit(new LogicCallable<IN, OUT>(processDefinition, commonInput)));
        }
        return new LogicSyncFuture(processDefinition.process(commonInput));
    }

    protected ExecutorService getExecutor() {
        return EXECUTOR_SERVICE;
    }

    class LogicCallable<IN extends BaseInput, OUT> implements Callable<CommonOutput<OUT>> {
        private ProcessDefinition<IN, OUT> processInstance;
        private CommonInput<IN> commonInput;

        public LogicCallable(ProcessDefinition<IN, OUT> processInstance, CommonInput<IN> commonInput) {
            this.processInstance = processInstance;
            this.commonInput = commonInput;
        }

        @Override
        public CommonOutput<OUT> call() throws Exception {
            return processInstance.process(commonInput);
        }
    }

    class LogicFutureWrapper<V> implements ProcessFuture {
        private Future<V> future;

        public LogicFutureWrapper(Future<V> future) {
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
        public V get() throws InterruptedException, ExecutionException {
            return future.get();
        }

        @Override
        public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return future.get(timeout, unit);
        }
    }


    class LogicSyncFuture<V> implements ProcessFuture {
        private V result;

        public LogicSyncFuture(V result) {
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
        public V get() throws InterruptedException, ExecutionException {
            return result;
        }

        @Override
        public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return result;
        }
    }


}
