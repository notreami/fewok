package com.fewok.dsl.core.bpm.processor;

import com.fewok.common.common.*;
import com.fewok.common.util.JsonBinder;
import com.fewok.dsl.core.bpm.container.ExecuteResult;
import com.fewok.dsl.core.bpm.util.IdGenerator;
import lombok.extern.slf4j.Slf4j;

/**
 * 道流程
 *
 * @author notreami on 18/5/31.
 */
@Slf4j
public abstract class BaseFlowProcessor<Input extends BaseInput, Output> extends BaseProcessor<CommonInput<Input>, CommonOutput<Output>> {

    private static final IdGenerator idGenerator = new IdGenerator();//用于 IdWorker 初始化

    @Override
    protected void preProcess(CommonInput<Input> commonInput) {
        if (commonInput != null) {
            if (commonInput.getClientInfo() == null) {
                ClientInfo clientInfo = new ClientInfo();
                commonInput.setClientInfo(clientInfo);
            }
            commonInput.getData();
            commonInput.setDataClassName(null);
            commonInput.setDataJsonValue(null);
        }

        log.info("preProcess:{}", JsonBinder.toJSONString(commonInput));
    }

    @Override
    protected ExecuteResult checkInput(CommonInput<Input> commonInput) {
        boolean inChk = commonInput != null && commonInput.getClientInfo() != null && commonInput.getData() != null;
        if (!inChk) {
            return ExecuteResult.PARAM_CHECK_ERROR;
        }
        return ExecuteResult.OK;
    }

    @Override
    protected CommonOutput<Output> handleCheckFail(ExecuteResult executeResult) {
        return CommonOutput.PARAM_ERROR;
    }

    @Override
    protected CommonOutput<Output> handleException(CommonInput<Input> commonInput, CommonOutput<Output> commonResponse, Exception e) {
        String msg = String.format("Process[%s]处理异常,commonInput=%s", getProcessorName(), JsonBinder.toJSONString(commonInput));
        log.error(msg, e);
        return CommonOutput.UNKNOWN_ERROR;
    }
}
