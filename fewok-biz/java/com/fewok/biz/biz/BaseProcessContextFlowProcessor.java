package com.fewok.biz.biz;

import com.fewok.biz.biz.exception.ProcessExecuteException;
import com.fewok.dsl.core.execute.LogicExecutor;
import com.fewok.dsl.core.execute.ProcessContext;
import com.fewok.dsl.core.execute.logic.LogicResult;
import com.fewok.dsl.core.execute.logic.SimpleLogicExecutor;
import com.google.common.base.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author notreami on 18/3/21.
 */
@Slf4j
public abstract class BaseProcessContextFlowProcessor<P extends ProcessContext> extends BaseProcessContextProcessor<P> implements InitializingBean {
    @Autowired
    @Qualifier("orderOptExecutor")
    private ThreadPoolTaskExecutor taskExecutor;
    private LogicExecutor<P, LogicResult> executor;

    protected abstract List<BaseProcessContextProcessor<P>> getProcessors();


    @Override
    public void afterPropertiesSet() throws Exception {
//        executor = new SimpleLogicExecutor<P, LogicResult>(taskExecutor);
    }


    @Override
    protected LogicResult doProcess(P processContext) throws Exception {
        LogicResult finalResult = null;

        List<BaseProcessContextProcessor<P>> processContextProcessorList = getProcessors();
        List<Future<LogicResult>> futureList = new ArrayList<>(processContextProcessorList.size());
        for (BaseProcessContextProcessor<P> processor : processContextProcessorList) {
            Future<LogicResult> future = executor.execute(processor, processContext);
            futureList.add(future);

        }

        for (int i = 0; i < futureList.size(); i++) {
            try {
                finalResult = futureList.get(i).get();
                if (!finalResult.isSuccess()) {
                    log.error("Flow[{}] process fail, msg:{}", this.getClass().getSimpleName(), finalResult.getErrorInfo().getReasonPhrase());
                    return finalResult;
                }
            } catch (Exception e) {
                throw new ProcessExecuteException("Processor[" + processContextProcessorList.get(i).getClass().getSimpleName() + "]执行失败", e);
            }
        }

        finalResult = Optional.fromNullable(finalResult).or(LogicResult.FAIL);
        // 结果应该是请求的业务ID, 而非业务主体ID
//        finalResult.setData(input.getObjectId());

        return finalResult;
    }
}
