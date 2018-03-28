package com.fewok.biz.biz;

import com.fewok.common.common.BaseInput;
import com.fewok.common.common.ClientInfo;
import com.fewok.common.common.CommonInput;
import com.fewok.common.common.CommonOutput;
import com.fewok.dsl.core.execute.BaseProcessor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author notreami on 18/3/21.
 */
@Slf4j
public abstract class BaseLaunchProcessor<Input extends BaseInput, Output> extends BaseProcessor<CommonInput<Input>, CommonOutput<Output>> {

    @Override
    protected void preProcess(CommonInput<Input> commonInput) {
        if (commonInput != null && commonInput.getClientInfo() == null) {
            ClientInfo clientInfo = new ClientInfo();
            commonInput.setClientInfo(clientInfo);
        }
    }

    @Override
    protected ValidResult checkInput(CommonInput<Input> commonInput) {
        boolean check = commonInput.isValid();
        if (!check) {
            return new ValidResult(false, "入参错误");
        }
        return ValidResult.SUCCESS;
    }


    @Override
    protected CommonOutput<Output> createValidFail(ValidResult validResult) {
        return CommonOutput.PROCESS_INFO;
    }

    @Override
    protected CommonOutput<Output> handleException(CommonInput<Input> commonInput, CommonOutput<Output> commonOutput, Exception e) {
        log.error("BaseLaunchProcessor process error", e);
        return CommonOutput.SYS_ERROR;
    }
}
