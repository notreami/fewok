package com.fewok.dsl.core.bpm.processor;

import com.fewok.common.common.BaseInput;
import com.fewok.common.common.StatusInfo;
import com.fewok.common.util.JsonBinder;
import com.fewok.dsl.core.bpm.container.ExecuteResult;
import com.fewok.dsl.core.bpm.container.ProcessContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 活动流程
 *
 * @author notreami on 18/5/31.
 */
@Slf4j
public abstract class BaseActivityProcessor<Input extends BaseInput, Holder> extends BaseProcessor<ProcessContext<Input, Holder>, ExecuteResult> {

    @Override
    protected ExecuteResult handleCheckFail(ExecuteResult executeResult) {
        return executeResult;
    }

    @Override
    protected ExecuteResult handleException(ProcessContext<Input, Holder> processContext, ExecuteResult executeResult, Exception e) {
        log.error("Process[{}]处理异常,processId={},processContext={}", getProcessorName(), processContext.getProcessId(), JsonBinder.toJSONString(processContext), e);
        if (executeResult == null) {
            executeResult = new ExecuteResult();
        }
        executeResult.setSuccess(false);
        executeResult.setStatusInfo(StatusInfo.UNKNOWN_ERROR);
        executeResult.setException(e);
        return executeResult;
    }
}
