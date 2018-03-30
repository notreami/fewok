package com.fewok.biz.biz;

import com.fewok.common.common.BaseInput;
import com.fewok.common.common.ClientInfo;
import com.fewok.common.common.CommonInput;
import com.fewok.common.common.CommonOutput;
import com.fewok.dsl.core.execute.BaseProcessor;
import com.fewok.dsl.core.execute.Processor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * @author notreami on 18/3/21.
 */
@Slf4j
public abstract class BaseLaunchProcessor<Input extends BaseInput, Output> extends BaseProcessor<CommonInput<Input>, CommonOutput<Output>> {

    @Override
    protected void preProcess(CommonInput<Input> input) {
        if (input != null && input.getClientInfo() == null) {
            ClientInfo clientInfo = new ClientInfo();
            input.setClientInfo(clientInfo);
        }
    }

    @Override
    protected ValidResult checkInput(CommonInput<Input> commonRequest) {
        boolean check = commonRequest.isValid();
        if (!check) {
            return new ValidResult(false, "入参错误");
        }
        boolean reqChk = commonRequest.getData().isValid();
        return new ValidResult(reqChk, reqChk ? "确认预定参数验证成功" : "确认预定参数验证失败");
    }


    @Override
    protected CommonOutput<Output> createValidFail(ValidResult validResult) {
        return CommonOutput.PROCESS_INFO;
    }

    @Override
    protected void afterProcess(Processor<CommonInput<Input>, CommonOutput<Output>> processor, CommonInput<Input> input, CommonOutput<Output> output) {

    }
    @Override
    protected CommonOutput<Output> handleException(CommonInput<Input> commonInput, CommonOutput<Output> commonOutput, Exception e) {
        log.error("BaseLaunchProcessor process error", e);
        return CommonOutput.SYS_ERROR;
    }
}
