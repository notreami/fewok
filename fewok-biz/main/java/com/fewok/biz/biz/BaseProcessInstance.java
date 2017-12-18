package com.fewok.biz.biz;

import com.fewok.common.common.BaseInput;
import com.fewok.common.common.CommonInput;
import com.fewok.common.common.CommonOutput;
import com.fewok.biz.biz.bean.*;
import com.fewok.dsl.bpm.common.ProcessContext;
import com.fewok.dsl.bpm.common.ProcessDefinition;
import com.fewok.dsl.bpm.common.ProcessEvent;
import com.fewok.dsl.bpm.common.ProcessTypeIdentify;
import com.fewok.dsl.bpm.execute.LogicExecutor;

/**
 * 流程实例
 * @author notreami on 17/11/22.
 */
public abstract class BaseProcessInstance<IN extends BaseInput, OUT> implements ProcessDefinition, ProcessTypeIdentify, ProcessEvent {
    protected static LogicExecutor executor = new LogicExecutor<>();

    @Override
    public CommonOutput process(CommonInput commonInput) {
        startEvent(commonInput);
        ProcessContext<IN, OUT> processContext = null;
        try {
            CommonOutput commonOutput = checkInput(commonInput);
            if (!commonOutput.isSuccess()) {
                return commonOutput;
            }
            processContext = mergeContext(commonInput);
            if (!processContext.getCommonOutput().isSuccess()) {
                return commonOutput;
            }
            intermediateEvent(processContext);
            commonOutput = doProcess(processContext);
            processContext.setCommonOutput(commonOutput);
            return commonOutput;
        } catch (Exception e) {
            exceptionEvent(processContext != null ? processContext : ProcessContext.create(commonInput, null), e);
            return CommonOutput.FAILURE;
        } finally {
            endEvent(processContext);
        }
    }

    /**
     * 合并
     *
     * @param commonInput
     * @return
     */
    private ProcessContext<IN, OUT> mergeContext(CommonInput<IN> commonInput) {
        return null;
    }

    /**
     * 获取所需数据
     *
     * @param commonInput
     * @return
     */
    protected RichDataDefaultModel getRichDataModel(CommonInput<IN> commonInput) {
        return new RichDataDefaultModel();
    }

    protected abstract CommonOutput<OUT> doProcess(ProcessContext<IN, OUT> processContext) throws Exception;
}
