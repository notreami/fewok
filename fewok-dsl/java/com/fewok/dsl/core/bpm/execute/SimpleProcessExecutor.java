package com.fewok.dsl.core.bpm.execute;

import com.fewok.dsl.core.bpm.ProcessExecutor;
import com.fewok.dsl.core.bpm.Processor;
import com.fewok.dsl.core.bpm.container.ExecuteResult;
import com.fewok.dsl.core.bpm.container.ProcessContext;
import com.fewok.dsl.core.bpm.execute.future.AsyncExecuteFuture;
import com.fewok.dsl.core.bpm.execute.future.ParallelExecuteFuture;
import com.fewok.dsl.core.bpm.execute.future.SyncExecuteFuture;
import com.fewok.dsl.core.bpm.type.InvokeType;
import com.fewok.dsl.core.bpm.util.JsonProcess;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Future;

/**
 * 简单的流程执行器
 *
 * @author notreami on 18/5/31.
 */
@Slf4j
@AllArgsConstructor
public class SimpleProcessExecutor<P extends ProcessContext, R extends ExecuteResult> implements ProcessExecutor<P, R> {

    private ThreadPoolTaskExecutor taskExecutor;

    @Override
    public Future<R> execute(Processor<P, R> processor, P processContext) {
        InvokeType invokeType = processor.getProcessRule().getInvokeType();
        if (invokeType == null) {
            log.error("执行异常invokeType=null,processor={},processContext={}", processor.getProcessorName(), JsonProcess.toJSONString(processContext));
            return null;
        }
        switch (processor.getProcessRule().getInvokeType()) {
            case SYNC:
                return new SyncExecuteFuture<>(processor.process(processContext));
            case ASYNC:
                return new AsyncExecuteFuture<>(taskExecutor.submit(new AsyncExecuteFuture.ExecuteCallable<>(processor, processContext)));
            case PARALLEL:
                taskExecutor.submit(new ParallelExecuteFuture.ExecuteRunnable<>(processor, processContext));
                return new ParallelExecuteFuture(ExecuteResult.OK);
            default:
                log.error("执行异常invokeType={},processor={},processContext={}", invokeType, processor.getProcessorName(), JsonProcess.toJSONString(processContext));
                return null;
        }
    }
}
