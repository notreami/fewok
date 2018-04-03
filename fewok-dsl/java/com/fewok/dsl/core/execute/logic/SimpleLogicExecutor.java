package com.fewok.dsl.core.execute.logic;

import com.fewok.common.util.JsonBinder;
import com.fewok.dsl.core.execute.LogicExecutor;
import com.fewok.dsl.core.execute.ProcessContext;
import com.fewok.dsl.core.execute.Processor;
import com.fewok.dsl.core.execute.logic.future.LogicAsyncFuture;
import com.fewok.dsl.core.execute.logic.future.LogicConcurrentFuture;
import com.fewok.dsl.core.execute.logic.future.LogicSyncFuture;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * 内置逻辑的执行器
 * @author notreami on 18/3/26.
 */
@Slf4j
@AllArgsConstructor
public class SimpleLogicExecutor<P extends ProcessContext, R extends LogicResult> implements LogicExecutor<P, R> {
    private ThreadPoolTaskExecutor executorService;

    @Override
    public Future<R> execute(Processor<P, R> processor, P processContext) {
        switch (processor.getInvokeType()) {
            case SYNC:
                return new LogicSyncFuture<>(processor.process(processContext));
            case ASYNC:
                return new LogicAsyncFuture<>(executorService.submit(new LogicAsyncFuture.LogicCallable<>(processor, processContext)));
            case CONCURRENT:
                executorService.submit(new LogicConcurrentFuture.LogicRunnable<>(processor, processContext));
                return new LogicConcurrentFuture(LogicResult.SUCCESS);
            default:
                log.error("逻辑执行异常processor={},input={}", JsonBinder.toJSONString(processor), JsonBinder.toJSONString(processContext));
                return null;
        }
    }
}
