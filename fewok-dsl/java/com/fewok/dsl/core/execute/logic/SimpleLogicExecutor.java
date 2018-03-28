package com.fewok.dsl.core.execute.logic;

import com.fewok.common.util.JsonBinder;
import com.fewok.dsl.core.execute.LogicExecutor;
import com.fewok.dsl.core.execute.ProcessContext;
import com.fewok.dsl.core.execute.Processor;
import com.fewok.dsl.core.execute.logic.future.LogicAsyncFuture;
import com.fewok.dsl.core.execute.logic.future.LogicConcurrentFuture;
import com.fewok.dsl.core.execute.logic.future.LogicSyncFuture;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author notreami on 18/3/26.
 */
@Slf4j
public class SimpleLogicExecutor<P extends ProcessContext, R extends LogicResult> implements LogicExecutor<P, R> {

    private ExecutorService executorService;

    public SimpleLogicExecutor(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public Future<R> execute(Processor<P, R> processor, P processContext) {
        switch (processor.getInvokeType()) {
            case SYNC:
                return new LogicAsyncFuture(executorService.submit(new LogicAsyncFuture.LogicCallable<P, R>(processor, processContext)));
            case ASYNC:
                return new LogicSyncFuture(processor.process(processContext));
            case CONCURRENT:
                executorService.submit(new LogicConcurrentFuture.LogicRunnable<P, R>(processor, processContext));
                return new LogicConcurrentFuture(LogicResult.SUCCESS);
            default:
                log.error("逻辑执行异常processor={},input={}", JsonBinder.toJSONString(processor), JsonBinder.toJSONString(processContext));
                return null;
        }
    }
}
