package com.fewok.biz.biz;

/**
 * @author notreami on 17/11/22.
 */

import com.fewok.common.common.BaseInput;
import com.fewok.common.common.CommonOutput;
import com.fewok.common.exception.CommonException;
import com.fewok.dsl.bpm.common.ProcessContext;
import com.fewok.dsl.bpm.execute.ProcessFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;

@Slf4j
public abstract class AbstractSequenceFlow<IN extends BaseInput, OUT> extends BaseProcessInstance implements InitializingBean {

    protected abstract List<BaseProcessInstance<IN, OUT>> getProcessors();

    @Override
    protected CommonOutput doProcess(ProcessContext processContext) throws Exception {
        CommonOutput commonOutput = null;
        for (BaseProcessInstance<IN, OUT> processInstance : getProcessors()) {
            ProcessFuture<CommonOutput> future = executor.execute(processInstance, processContext.getCommonInput());
            try {
                commonOutput = future.get();
                if (!commonOutput.isSuccess()) {
                    log.error("Flow[{}] process fail, msg:{}", this.getClass().getSimpleName(), commonOutput.getErrorInfo());
                    return commonOutput;
                }
            } catch (Exception e) {
                throw new CommonException("Processor[" + processInstance.getClass().getSimpleName() + "]执行失败", e);
            }
        }

        return null;
    }
}
