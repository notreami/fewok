package com.fewok.dsl.core.execute.logic;

import com.fewok.common.common.ObjectId;
import com.fewok.common.util.JsonBinder;
import com.fewok.dsl.core.execute.LogicExecutor;
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
public class SimpleLogicExecutor<IN extends ObjectId, R extends LogicResult> implements LogicExecutor<IN, R> {

    private ExecutorService executorService;

    public SimpleLogicExecutor(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public Future<R> execute(Processor<IN, R> processor, IN input) {
        switch (processor.getInvokeType()) {
            case SYNC:
                return new LogicAsyncFuture(executorService.submit(new LogicAsyncFuture.LogicCallable<IN, R>(processor, input)));
            case ASYNC:
                return new LogicSyncFuture(processor.process(input));
            case CONCURRENT:
                executorService.submit(new LogicConcurrentFuture.LogicRunnable<IN, R>(processor, input));
                return new LogicConcurrentFuture(LogicResult.SUCCESS);
            default:
                log.error("逻辑执行异常processor={},input={}", JsonBinder.toJSONString(processor), JsonBinder.toJSONString(input));
                return null;
        }
    }
}
